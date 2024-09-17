package com.agitex.climax.parser;

import com.agitex.climax.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class FileProcessor {

    private final Map<String, FileParser> parsers;

    @Autowired
    public FileProcessor(List<FileParser> fileParsers) {
        // Associe l'extension du fichier à l'implémentation du parser
        this.parsers = Map.of(
                "csv", getFileParser(fileParsers, CsvFileParser.class),
                "xml", getFileParser(fileParsers, XmlFileParser.class),
                "json", getFileParser(fileParsers, JsonFileParser.class),
                "txt", getFileParser(fileParsers, TextFileParser.class),
                "xlsx", getFileParser(fileParsers, ExcelFileParser.class)
        );
    }

    private FileParser getFileParser(List<FileParser> fileParsers, Class<? extends FileParser> parserClass) {
        return fileParsers.stream()
                .filter(parserClass::isInstance)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Parser not found for: " + parserClass.getSimpleName()));
    }

    public List<Client> processFile(InputStream inputStream, String fileName) throws IOException {
        String extension = getFileExtension(fileName);

        // Liste des extensions de fichiers supportées
        Set<String> supportedExtensions = new HashSet<>(Arrays.asList("csv", "xml", "json", "txt", "xlsx"));

        if (!supportedExtensions.contains(extension)) {
            throw new UnsupportedOperationException("format de fichier non supporté: " + extension);
        }

        FileParser parser = parsers.get(extension);

        if (parser == null) {
            throw new UnsupportedOperationException("format de fichier non supporté: " + extension);
        }

        return parser.parse(inputStream);
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        return (lastIndexOfDot == -1) ? "" : fileName.substring(lastIndexOfDot + 1).toLowerCase();
    }
}
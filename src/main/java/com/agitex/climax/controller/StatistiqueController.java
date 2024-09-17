package com.agitex.climax.controller;

import com.agitex.climax.model.Client;
import com.agitex.climax.parser.FileProcessor;
import com.agitex.climax.service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatistiqueController {

    @Autowired
    private FileProcessor fileProcessor;

    @Autowired
    private StatistiqueService statistiqueService;

    @PostMapping("/upload")
    public Map<String, Double> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // Obtenez le nom du fichier pour déterminer son extension
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalArgumentException("Le nom du fichier est nul");
        }

        // Passez l'InputStream et le nom du fichier à la méthode processFile
        List<Client> clients = fileProcessor.processFile(file.getInputStream(), fileName);

        return statistiqueService.calculeMoyenneSalaireParProfession(clients);
    }


}

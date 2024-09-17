package com.agitex.climax.parser;//package com.agitex.climax.parser;
//
//import com.agitex.climax.model.Client;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ExcelFileParser implements FileParser {
//
//    @Override
//    public List<Client> parse(File file) throws IOException {
//        List<Client> clients = new ArrayList<>();
//
//        try (FileInputStream fis = new FileInputStream(file);
//             Workbook workbook = new XSSFWorkbook(fis)) {
//
//            Sheet sheet = workbook.getSheetAt(0); // Suppose que les données sont dans la première feuille
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) {
//                    // Sauter la première ligne (header)
//                    continue;
//                }
//
//                Client client = new Client();
//                client.setNom(row.getCell(0).getStringCellValue());
//                client.setPrenom(row.getCell(1).getStringCellValue());
//                client.setAge(Integer.parseInt(row.getCell(2).getStringCellValue()));
//                client.setProfession(row.getCell(3).getStringCellValue());
//                client.setSalaire(row.getCell(4).getNumericCellValue());
//
//                clients.add(client);
//            }
//        }
//
//        return clients;
//    }
//}

import com.agitex.climax.model.Client;
import com.agitex.climax.parser.FileParser;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelFileParser implements FileParser {
    @Override
    public List<Client> parse(InputStream inputStream) throws IOException {
        List<Client> clients = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // On suppose que les données sont dans la première feuille
            for (Row row : sheet) {
                // Ignore les en-têtes
                if (row.getRowNum() == 0) {
                    continue;
                }
                Client client = parseExcelRowToClient(row);
                clients.add(client);
            }
        } catch (Exception e) {
            throw new IOException("Erreur lors du traitement du fichier XLSX", e);
        }
        return clients;
    }

    private Client parseExcelRowToClient(Row row) {
        Client client = new Client();
        client.setNom(row.getCell(0).getStringCellValue());
        client.setPrenom(row.getCell(1).getStringCellValue());

        Cell ageCell = row.getCell(0);
        if (ageCell != null) {
            if (ageCell.getCellType() == CellType.NUMERIC) {
                client.setAge((int) ageCell.getNumericCellValue());
            } else if (ageCell.getCellType() == CellType.STRING) {
                client.setAge(Integer.parseInt(ageCell.getStringCellValue()));
            }
        }

//        client.setAge(Integer.parseInt(row.getCell(2).getStringCellValue()));
        client.setProfession(row.getCell(3).getStringCellValue());
        client.setSalaire(Double.parseDouble(row.getCell(4).getStringCellValue()));
        // Ajoutez d'autres champs selon la structure de votre XLSX
        return client;
    }
}
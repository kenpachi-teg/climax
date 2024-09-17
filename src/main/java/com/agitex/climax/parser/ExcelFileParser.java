package com.agitex.climax.parser;

import com.agitex.climax.model.Client;
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

        Cell ageCell = row.getCell(2);
        if (ageCell != null) {
            if (ageCell.getCellType() == CellType.NUMERIC) {
                client.setAge((int) ageCell.getNumericCellValue());
            } else if (ageCell.getCellType() == CellType.STRING) {
                client.setAge(Integer.parseInt(ageCell.getStringCellValue()));
            }
        }

        client.setProfession(row.getCell(3).getStringCellValue());
        client.setSalaire(row.getCell(4).getNumericCellValue());
        // Ajoutez d'autres champs selon la structure de votre XLSX
        return client;
    }
}
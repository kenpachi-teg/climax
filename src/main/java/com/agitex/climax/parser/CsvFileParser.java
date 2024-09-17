package com.agitex.climax.parser;

import com.agitex.climax.model.Client;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
//public class CsvFileParser implements FileParser {
//
//    @Override
//    public List<Client> parse(File file) throws IOException {
//        List<Client> clients = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                Client client = new Client();
//                client.setNom(values[0]);
//                client.setPrenom(values[1]);
//                client.setAge(Integer.parseInt(values[2]));
//                client.setProfession(values[3]);
//                client.setSalaire(Double.parseDouble(values[4]));
//                clients.add(client);
//            }
//        }
//        return clients;
//    }
//}

public class CsvFileParser implements FileParser {
    @Override
    public List<Client> parse(InputStream inputStream) throws IOException {
        List<Client> clients = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] line;
            boolean isFirstLine = true;
            while ((line = reader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                Client client = parseCsvLineToClient(line);
                clients.add(client);
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

    private Client parseCsvLineToClient(String[] line) {
        // Conversion d'une ligne CSV en un objet Client
        Client client = new Client();
        client.setNom(line[0]);
        client.setPrenom(line[1]);

        if (isNumeric(line[2])) {
            client.setAge(Integer.parseInt(line[2]));
        } else {
            // Gérer le cas où la valeur d'âge n'est pas un nombre valide
            throw new NumberFormatException("Valeur d'âge non valide : " + line[2]);
        }

//        client.setAge(Integer.parseInt(line[2]));
        client.setProfession(line[3]);
        client.setSalaire(Double.parseDouble(line[4]));
        return client;
    }

    private boolean isNumeric(String str) {
        // Méthode utilitaire pour vérifier si une chaîne est un nombre valide
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
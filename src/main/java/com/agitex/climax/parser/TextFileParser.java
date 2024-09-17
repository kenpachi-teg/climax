package com.agitex.climax.parser;//package com.agitex.climax.parser;
//
//import com.agitex.climax.model.Client;
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class TextFileParser implements FileParser {
//
//    @Override
//    public List<Client> parse(File file) throws IOException {
//        List<Client> clients = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split("\\s+"); // Supposons que les données soient séparées par des espaces
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

import com.agitex.climax.model.Client;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class TextFileParser implements FileParser {
    @Override
    public List<Client> parse(InputStream inputStream) throws IOException {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Client client = parseTextLineToClient(line);
                clients.add(client);
            }
        }
        return clients;
    }

    private Client parseTextLineToClient(String line) {
        // Exemple simple de parsing de ligne
        String[] parts = line.split(",");
        Client client = new Client();
        client.setNom(parts[0]);
        client.setPrenom(parts[1]);
        client.setAge(Integer.parseInt(parts[2]));
        client.setProfession(parts[3]);
        client.setSalaire(Double.parseDouble(parts[4]));
        // Ajoutez d'autres champs selon la structure de votre TXT
        return client;
    }
}
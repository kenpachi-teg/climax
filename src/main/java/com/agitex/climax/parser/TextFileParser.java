package com.agitex.climax.parser;

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
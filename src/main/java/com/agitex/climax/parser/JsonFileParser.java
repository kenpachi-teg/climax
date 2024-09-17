package com.agitex.climax.parser;

import com.agitex.climax.model.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

//@Service
//public class JsonFileParser implements FileParser {
//
//    @Override
//    public List<Client> parse(File file) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Client[] clients = objectMapper.readValue(file, Client[].class);
//        return Arrays.asList(clients);
//    }
//}

@Service
public class JsonFileParser implements FileParser {
    @Override
    public List<Client> parse(InputStream inputStream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, Client.class));
    }
}
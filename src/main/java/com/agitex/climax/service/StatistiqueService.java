package com.agitex.climax.service;

import com.agitex.climax.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatistiqueService {

    public Map<String, Double> calculeMoyenneSalaireParProfession(List<Client> clients) {
        return clients.stream()
                .collect(Collectors.groupingBy(
                        Client::getProfession,
                        Collectors.averagingDouble(Client::getSalaire)
                ));
    }

}

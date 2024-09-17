package com.agitex.climax.parser;

import com.agitex.climax.model.Client;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class XmlFileParser implements FileParser {
    @Override
    public List<Client> parse(InputStream inputStream) throws IOException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ClientListWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ClientListWrapper wrapper = (ClientListWrapper) unmarshaller.unmarshal(inputStream);
            return wrapper.getClients();
        } catch (JAXBException e) {
            throw new IOException("Erreur lors du traitement du fichier XML", e);
        }
    }

    @XmlRootElement(name = "clients")
    public static class ClientListWrapper {
        @XmlElement(name = "client")
        private List<Client> clients;

        public List<Client> getClients() {
            return clients;
        }

        public void setClients(List<Client> clients) {
            this.clients = clients;
        }
    }
}
package com.agitex.climax.parser;//package com.agitex.climax.parser;
//
//import com.agitex.climax.model.Client;
//import org.springframework.stereotype.Service;
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class XmlFileParser implements FileParser {
//
//    @Override
//    public List<Client> parse(File file) throws IOException {
//        List<Client> clients = new ArrayList<>();
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(new InputSource(new FileReader(file)));
//
//            NodeList nodeList = document.getElementsByTagName("client");
//
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                Client client = new Client();
//                client.setNom(node.getAttributes().getNamedItem("nom").getNodeValue());
//                client.setPrenom(node.getAttributes().getNamedItem("prenom").getNodeValue());
//                client.setAge(Integer.parseInt(node.getAttributes().getNamedItem("age").getNodeValue()));
//                client.setProfession(node.getAttributes().getNamedItem("profession").getNodeValue());
//                client.setSalaire(Double.parseDouble(node.getAttributes().getNamedItem("salaire").getNodeValue()));
//                clients.add(client);
//            }
//        } catch (Exception e) {
//            throw new IOException("Error parsing XML file", e);
//        }
//        return clients;
//    }
//}

import com.agitex.climax.model.Client;
import com.agitex.climax.parser.FileParser;
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
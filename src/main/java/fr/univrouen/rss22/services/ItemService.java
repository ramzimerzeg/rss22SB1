package fr.univrouen.rss22.services;

import fr.univrouen.rss22.models.Feed;
import org.springframework.stereotype.Service;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

@Service
public class ItemService {

    public boolean validate_rss22(Feed rss22) {
        try
        {
            // creér un SchemaFactory
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // chargé le format xsd de vérification  avec une  instance  Schema
            Source xsdFile = new StreamSource(new File("src/main/resources/rss22.xsd"));
            Schema schema = factory.newSchema(xsdFile);

            // créer un Validator
            Validator validator = schema.newValidator();
            System.out.println("sssssssssssssssssssssssssssss");

            // convertir objet rss22 en fichier xml
            File xmlFile = getXMLFrom_rss22(rss22);
            if(xmlFile == null)
                return false;

            // valider le rss22
            System.out.println("sssssssssssssssssssssssssssss");
            validator.validate(new StreamSource(new File("src/main/resources/rss22.xml")));

            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    private File getXMLFrom_rss22(Feed rss22) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Feed.class);

            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            System.out.println(rss22);
            // fichier xml
            File xmlFile = new File("rss22.xml");
            marshaller.marshal(rss22, xmlFile);

            return xmlFile;
        }
        catch(Exception ex) {
            return null;
        }
    }
}

package fr.univrouen.rss22.services;

import fr.univrouen.rss22.models.Feed;
import fr.univrouen.rss22.models.Item;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

@Service
public class ItemService {

    public boolean validate_rss22(String rss22) throws SAXException,IOException {
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);
            SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Resource resource = new DefaultResourceLoader().getResource("classpath:rss22.xsd");

            factory.setSchema(schemaFactory.newSchema(
                    new Source[] {new StreamSource(resource.getInputStream())}));

            javax.xml.validation.Validator validator = factory.getSchema().newValidator();

            //String rss22_String = getXMLFrom_rss22(rss22);

            validator.validate(new StreamSource(new StringReader(rss22)));

            return true;
        }  catch (SAXException exp) {
                throw  exp;
        }  catch (IOException exp) {
                throw  exp;
        }
        /*try
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
            //File xmlFile = getXMLFrom_rss22(rss22);
            //if(xmlFile == null)
             //   return false;

            // valider le rss22
            System.out.println("sssssssssssssssssssssssssssss");
            validator.validate(new StreamSource(new File("src/main/resources/rss22bad.xml")));

            return true;
        }
        catch(Exception ex)
        {
            return false;
        }*/
    }

    public String getXMLFrom_rss22(Feed rss22) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Feed.class);

            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // fichier xml
            //File xmlFile = new File("rss22.xml");
            StringWriter rss22_String = new StringWriter();
            marshaller.marshal(rss22, rss22_String);

            String xmlString = rss22_String.toString();
            System.out.println("xmlString **************************");
            System.out.println(xmlString);

            return xmlString;
        }
        catch(Exception ex) {
            return null;
        }
    }
}

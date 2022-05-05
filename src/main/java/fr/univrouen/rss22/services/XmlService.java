package fr.univrouen.rss22.services;

import fr.univrouen.rss22.models.Feed;
import fr.univrouen.rss22.models.Item;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

@Service
public class XmlService {

    /**
     * Valide le flux rss22 recu.
     * @param rss22
     * @return true si valide, false sinon
     * @throws SAXException
     * @throws IOException
     */
    public boolean validateRss22(String rss22) throws SAXException,IOException {
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

            validator.validate(new StreamSource(new StringReader(rss22)));
        }  catch (SAXException exp) {
                throw  exp;
        }  catch (IOException exp) {
                throw  exp;
        }

        return true;
    }

    /**
     * @param xmlString
     * @return objet Item
     */
    public Item getItemObjectFromXMLString(String xmlString) {
        Item item = null;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Item.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            item = (Item) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return item;
    }

    /**
     * @param xmlString
     * @return objet Feed
     */
    public Feed getFeedObjectFromXMLString(String xmlString) {
        Feed feed = null;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Feed.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            feed = (Feed) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return feed;
    }

    /**
     * @param rss22
     * @return string format xml du l'objet Feed
     */
    public String getXMLFromFeedObject(Feed rss22) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Feed.class);

            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter rss22_String = new StringWriter();
            marshaller.marshal(rss22, rss22_String);

            String xmlString = rss22_String.toString();

            return xmlString;
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * @param rss22
     * @return string format xml du l'objet Item
     */
    public String getXMLFromItemObject(Item rss22) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Item.class);

            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter rss22_String = new StringWriter();
            marshaller.marshal(rss22, rss22_String);

            String xmlString = rss22_String.toString();

            return xmlString;
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * @param items
     * @return string format xml des données avec guis, titre, date de publication
     */
    public String getDataResume(List<Item> items) {
        StringBuilder xml = new StringBuilder("<feed>");

        if (items.isEmpty()) {
            xml.append("</feed>");
            return xml.toString();
        }

        for (int i=0; i < items.size(); i++) {
            xml.append("<item>");
            xml.append("<guid>" + items.get(i).getGuid() + "</guid>");
            xml.append("<title>" + items.get(i).getTitle() + "</title>");
            xml.append("<published>" + items.get(i).getPublished() + "</published>");
            xml.append("</item>");
        }

        xml.append("</feed>");

        return xml.toString();
    }

    /**
     * @param flux
     * @return flux rss complet avec l'item recu
     */
    public String englobeFluxInFeed(String flux) {
        StringBuilder xml = new StringBuilder("");
        xml.append("<rss:feed lang=\"ar-AR\" xmlns:rss=\"http://univrouen.fr/rss22\">\n" +
                "  <title>string</title>\n" +
                "  <pubDate>2008-09-29T03:49:45</pubDate>\n" +
                "  <copyright>string</copyright>\n" +
                "  <!--1 or more repetitions:-->\n" +
                "  <link rel=\"self\" type=\"string\" href=\"string\"/>");
        xml.append(flux);
        xml.append("</rss:feed>");

        return xml.toString();
    }
}

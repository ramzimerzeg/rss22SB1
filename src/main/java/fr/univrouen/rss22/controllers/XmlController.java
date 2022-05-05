package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.models.Item;
import fr.univrouen.rss22.repositories.ItemRepository;
import fr.univrouen.rss22.services.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class XmlController {

    @Autowired
    private XmlService xmlService;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * @return liste des items format xml.
     */
    @RequestMapping(value = "/rss22/resume/xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public String getItemsAsXml() {
        List<Item> items = itemRepository.findAll();

        return xmlService.getDataResume(items);
    }

    /**
     * @param guid
     * @return un item format xml.
     */
    @GetMapping(value = "/rss22/resume/xml/{guid}",produces = MediaType.APPLICATION_XML_VALUE)
    public String getItemAsXml(@PathVariable("guid") String guid) {
        Optional<Item> optionalItem = itemRepository.findById(guid);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();

            return xmlService.getXMLFromItemObject(item);
        }

        return "<result><guid>" + guid + "</guid><status>ERROR</status></result>";
    }

    /**
     * @param flux
     * @param file
     * @return resultat de l'operation d'ajout format xml.
     * @throws IOException
     */
    @PostMapping(value = "/insert", consumes = "application/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String insertItem(@RequestBody String flux, @RequestParam(value = "file",required = false) MultipartFile file ) throws IOException {
        Item item;
        String rss22;

        // Vérification de type d'ajout
        // Type 1 : fichier xml
        // Type 2 : flux xml
        if (file == null) {
            rss22 = xmlService.englobeFluxInFeed(flux);
        } else {
            rss22 = new String(file.getBytes(), StandardCharsets.UTF_8);
        }

        boolean valid = false;

        try {
            // Validation du contenu xml
            valid = xmlService.validateRss22(rss22);
        } catch(SAXException e) {
            return "<result><status>" + e.getMessage() + "</status></result>";
        } catch (IOException e) {
            return "<result><status>" + e.getMessage() + "</status></result>";
        }

        if (valid) {
            try {
                if (file == null) {
                    item = xmlService.getItemObjectFromXMLString(flux);
                } else {
                    item = xmlService.getFeedObjectFromXMLString(rss22).getItems().get(0);
                }

                item.setGuid(null);

                // Ajout a la base de donnée
                itemRepository.save(item);
            } catch (Exception e) {
                return "<result><status>" + e.getMessage() + "</status></result>";
            }
        } else {
            return "<result><status>ERROR</status></result>";
        }

        return "<result><guid>" + item.getGuid() + "</guid><status>INSERTED</status></result>";
    }

    /**
     * @param guid
     * @return résultat de l'opération de suppression
     */
    @DeleteMapping(value = "/rss22/delete/{guid}", produces = MediaType.APPLICATION_XML_VALUE)
    public String deleteItemById(@PathVariable("guid") String guid) {
        itemRepository.deleteById(guid);

        return "<result><guid>" + guid + "</guid><status>DELETED</status></result>";
    }
}

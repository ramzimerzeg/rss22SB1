package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.models.Item;
import fr.univrouen.rss22.models.XmlEngine;
import fr.univrouen.rss22.repositories.ItemRepository;
import fr.univrouen.rss22.services.ItemService;
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
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value = "/rss22/resume/xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public String getItemsAsXml() {
        List<Item> items = itemRepository.findAll();

        return new XmlEngine(items).loadResumeOfDataAsXML();
    }

    @GetMapping(value = "/rss22/resume/xml/{guid}")
    public String getItemAsXml(@PathVariable("guid") String guid) {
        Optional<Item> optionalItem = itemRepository.findById(guid);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();

            return new XmlEngine().getItemAsXml(item);
        }

        return "<result><guid>" + guid + "</guid><status>ERROR</status></result>";
    }

    @PostMapping(value = "/insert", produces = "application/xml")
    public String insertItem(@RequestBody String flux, @RequestParam(value = "file",required = false) MultipartFile file ) throws IOException {

        Item item;
        String rss22;

        if (file == null) {
            rss22 = new XmlEngine().getRss22(flux);
        } else {
            rss22 = new String(file.getBytes(), StandardCharsets.UTF_8);
        }

        boolean valid = false;

        try {
            valid = itemService.validate_rss22(rss22);
        } catch(SAXException e) {
            return "<result><status>" + e.getMessage() + "</status></result>";
        } catch (IOException e) {
            return "<result><status>" + e.getMessage() + "</status></result>";
        }

        if (valid) {
            try {
                if (file == null) {
                    item = itemService.getItemObjectFromXMLString(flux);
                } else {
                    item = itemService.getFeedObjectFromXMLString(rss22).getItems().get(0);
                }
                item.setGuid(null);
                itemRepository.save(item);
            } catch (Exception e) {
                System.out.println("IO Exception Ajout");
                return "<result><status>" + e.getMessage() + "</status></result>";
            }
        } else {
            return "<result><status>ERROR</status></result>";
        }

        return "<result><guid>" + item.getGuid() + "</guid><status>INSERTED</status></result>";
    }

    @DeleteMapping(value = "/rss22/delete/{guid}", produces = MediaType.APPLICATION_XML_VALUE)
    public String deleteItemById(@PathVariable("guid") String guid) {
        itemRepository.deleteById(guid);

        return "<result><guid>" + guid + "</guid><status>DELETED</status></result>";
    }

}

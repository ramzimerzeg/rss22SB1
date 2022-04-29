package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.models.Feed;
import fr.univrouen.rss22.models.Item;
import fr.univrouen.rss22.models.XmlEngine;
import fr.univrouen.rss22.repositories.ItemRepository;
import fr.univrouen.rss22.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class XmlController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value = "/rss22/resume/xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public String getItemAsXml() {
        List<Item> items = itemRepository.findAll();

        return new XmlEngine(items).loadResumeOfDataAsXML();
    }

    @PostMapping(value = "/insert", produces = "application/xml")
    public String insertItem(@ModelAttribute Item newItem) {

        Feed rss22 = new Feed("Projet XML","06-05-2022","Ramzi Merzeg, Islam Mokrane","localhost:8080");
        rss22.addItem(newItem);

        if(!itemService.validate_rss22(rss22))
            throw new RuntimeException("l'article ne respecte pas le format du xsd");

        try {
            itemRepository.save(newItem);
        } catch (Exception e) {
            return "<result><status>ERROR</status></result>";
        }

        return "<result><guid>" + newItem.getGuid() + "</guid><status>INSERTED</status></result>";
    }

    @GetMapping(value = "/rss22/resume/xml/{guid}", produces = MediaType.APPLICATION_XML_VALUE)
    public String getItemAsXml(@PathVariable("guid") String guid) {
        Optional<Item> optionalItem = itemRepository.findById(guid);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();

            XmlEngine engine = new XmlEngine();
            engine.addItem(item);

            return engine.loadDataAsXML();
        }

        return "<result><guid>" + guid + "</guid><status>ERROR</status></result>";
    }

    @DeleteMapping(value = "/rss22/delete/{guid}", produces = MediaType.APPLICATION_XML_VALUE)
    public String deleteItemById(@PathVariable("guid") String guid) {
        itemRepository.deleteById(guid);

        return "<result><guid>" + guid + "</guid><status>DELETED</status></result>";
    }

}

package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.models.Item;
import fr.univrouen.rss22.models.XmlEngine;
import fr.univrouen.rss22.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class XmlController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping(value = "/rss22/resume/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String getAllItemsAsXml() {
        List<Item> items = itemRepository.findAll();

        XmlEngine engine = new XmlEngine(items);

        return engine.loadDataAsXML();
    }

}

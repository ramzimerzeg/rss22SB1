package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.models.Feed;
import fr.univrouen.rss22.models.Item;
import fr.univrouen.rss22.models.XmlEngine;
import fr.univrouen.rss22.repositories.ItemRepository;
import fr.univrouen.rss22.services.ItemService;
import org.apache.coyote.Response;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    public String insertItem(@ModelAttribute Item newItem, @RequestParam(value = "file",required = false) MultipartFile file ) throws IOException {

        Feed rss22 = new Feed();
        String rss22_;
        if (file ==null){
            newItem.setGuid("123");
            if (newItem.getUpdated()==""){
                newItem.setUpdated(null);
            }
            if (newItem.getImage().getHref()==""){
                newItem.setImage(null);
            }
            if (newItem.getContributor().getName() ==""){
                newItem.setContributor(null);
            }
            rss22.addItem(newItem);
            rss22_ = itemService.getXMLFrom_rss22(rss22);

        }else{
            rss22_ = new String(file.getBytes(), StandardCharsets.UTF_8);
        }

        boolean valid = false;

        try {
            valid = itemService.validate_rss22(rss22_);
        } catch(SAXException e) {
            return "<result><status>"+e.getMessage()+"ERROR</status></result>";
        } catch (IOException e) {
            return "<result><status>"+e.getMessage()+"ERROR</status></result>";
        }

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

        JSONObject obj = new JSONObject();
        JSONObject errorObj = new JSONObject();
        errorObj.append("guid", guid);
        errorObj.append("status", "ERROR");
        obj.append("error", errorObj);
        return XML.toString(obj);

        //return "<result><guid>" + guid + "</guid><status>ERROR</status></result>";
    }

    @DeleteMapping(value = "/rss22/delete/{guid}", produces = MediaType.APPLICATION_XML_VALUE)
    public String deleteItemById(@PathVariable("guid") String guid) {
        itemRepository.deleteById(guid);

        return "<result><guid>" + guid + "</guid><status>DELETED</status></result>";
    }

}

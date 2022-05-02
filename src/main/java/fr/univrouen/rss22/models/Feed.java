package fr.univrouen.rss22.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jdk.jfr.DataAmount;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "feed", namespace = "http://univrouen.fr/rss22")
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed {

    @XmlElement
    private String title;

    @XmlElement
    private String pubDate;

    @XmlElement
    private String copyright;

    @XmlElement
    private Link link;

    @XmlElement(name = "item")
    @JsonProperty("item")
    private List<Item> items;

    @JacksonXmlProperty(isAttribute = true)
    @XmlAttribute(name = "lang", required = true)
    private String lang;


    public Feed() {
        this.title = "Projet XML rss22";
        this.copyright = "Merzeg - Mokrane";
        this.pubDate = "2022-04-30T09:50:20";
        this.link = new Link("self","", "");
        this.items = new LinkedList();
        this.lang = "en-007";
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}

    /*
    @XmlAttribute(name = "titre")
    private String title = "Projet XML";
    private String pubDate = "06-05-2022";
    private String copyright = "Ramzi Merzeg, Islam Mokrane";
    private String link = "localhost:8080";

    private List<Item> items;

    public Feed(String title, String pubDate, String copyright, String link) {
        this.title = title;
        this.pubDate = pubDate;
        this.copyright = copyright;
        this.link = link;

        this.items = new LinkedList();
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

}*/

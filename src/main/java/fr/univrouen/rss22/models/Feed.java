package fr.univrouen.rss22.models;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Feed implements Serializable {

    private String title = "Projet XML";
    private String pubDate = "06-05-2022";
    private String copyright = "Ramzi Merzeg, Islam Mokrane";
    private String url = "localhost:8080";

    private List<Item> items;

    public Feed(String title, String pubDate, String copyright, String url) {
        this.title = title;
        this.pubDate = pubDate;
        this.copyright = copyright;
        this.url = url;

        this.items = new LinkedList();
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

}

package fr.univrouen.rss22.models;

import java.util.List;

public class XmlEngine {

    private List<Item> items;

    public XmlEngine() {}

    public XmlEngine(List<Item> items) {
        this.items = items;
    }

    public void setData(List<Item> items) {
        this.items = items;
    }

    public List<Item> getData() {
        return this.items;
    }

    public String loadDataAsXML() {
        StringBuilder xml = new StringBuilder("<articles>");

        if (this.items.isEmpty()) {
            xml.append("</articles>");
            return xml.toString();
        }

        for (int i=0; i < this.items.size(); i++) {
            xml.append("<item>");
            xml.append("<guid>" + this.items.get(i).getGuid() + "</guid>");
            xml.append("<title>" + this.items.get(i).getTitle() + "</title>");
            xml.append("<published>" + this.items.get(i).getPublished() + "</published>");
            xml.append("</item>");
        }

        xml.append("</articles>");

        return xml.toString();
    }

}

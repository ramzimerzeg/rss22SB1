package fr.univrouen.rss22.models;

import java.util.LinkedList;
import java.util.List;

public class XmlEngine {

    private List<Item> items = new LinkedList<>();

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

    public String loadResumeOfDataAsXML() {
        StringBuilder xml = new StringBuilder("<feed>");

        if (this.items.isEmpty()) {
            xml.append("</feed>");
            return xml.toString();
        }

        for (int i=0; i < this.items.size(); i++) {
            xml.append("<item>");
            xml.append("<guid>" + this.items.get(i).getGuid() + "</guid>");
            xml.append("<title>" + this.items.get(i).getTitle() + "</title>");
            xml.append("<published>" + this.items.get(i).getPublished() + "</published>");
            xml.append("</item>");
        }

        xml.append("</feed>");

        return xml.toString();
    }

    public String loadDataAsXML() {
        StringBuilder xml = new StringBuilder("<feed>");

        if (this.items.isEmpty()) {
            xml.append("</feed>");
            return xml.toString();
        }

        for (int i=0; i < this.items.size(); i++) {
            xml.append("<item>");
            xml.append("<guid>" + this.items.get(i).getGuid() + "</guid>");
            xml.append("<title>" + this.items.get(i).getTitle() + "</title>");

            String categories[] = this.items.get(i).getCategory().getTerm().split(" ");
            for (String category : categories) {
                xml.append("<category term=\"" + category + "\"/>");
            }

            xml.append("<published>" + this.items.get(i).getPublished() + "</published>");

            if (!this.items.get(i).getImage().getHref().isEmpty()) {
                xml.append("<image alt=\"" + this.items.get(i).getImage().getAlt() + "\"/>");
            }

            xml.append("<content type=\"" + this.items.get(i).getContent() + "\">" + this.items.get(i).getContent() + "</content>");

            String authors[] = this.items.get(i).getAuthor().getName().split(",");
            String emails[] = this.items.get(i).getAuthor().getEmail().split(",");
            String uris[] = this.items.get(i).getAuthor().getUri().split(",");

            for (int j=0; j < authors.length; j++) {
                xml.append("<author>");
                xml.append("<name>" + authors[j] + "</name>");
                //xml.append("<email>" + emails[j] + "</email>");
                //xml.append("<uri>" + uris[j] + "</uri>");
                xml.append("</author>");
            }

            xml.append("</item>");
        }

        xml.append("</feed>");

        return xml.toString();
    }

    public void addItem(Item item) {
        items.add(item);
    }
}

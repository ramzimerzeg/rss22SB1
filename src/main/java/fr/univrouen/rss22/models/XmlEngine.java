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

    public String getItemAsXml(Item item) {
        StringBuilder xml = new StringBuilder("");

        xml.append("<item>");
        xml.append("<guid>" + item.getGuid() + "</guid>");
        xml.append("<title>" + item.getTitle() + "</title>");
        xml.append("<category term=\"" + item.getCategory().getTerm() + "\"/>");

        xml.append("<published>" + item.getPublished() + "</published>");

        if (item.getImage() != null) {
            xml.append("<image type=\"" + item.getImage().getType() + "\" href=\"" + item.getImage().getHref() + "\" alt=\"" + item.getImage().getAlt() + "\"/>");
        }

        xml.append("<content type=\"" + item.getContent().getType() + "\">" + item.getContent().getValue() + "</content>");

        xml.append("<author>");
        xml.append("<name>" + item.getAuthor().getName() + "</name>");
        xml.append("<email>" + item.getAuthor().getEmail() + "</email>");
        xml.append("</author>");

        xml.append("</item>");

        return xml.toString();
    }

    public String getRss22(String flux) {
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

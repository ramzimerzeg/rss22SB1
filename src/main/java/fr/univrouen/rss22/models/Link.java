package fr.univrouen.rss22.models;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "link")
@XmlAccessorType(XmlAccessType.FIELD)
public class Link {

    @JacksonXmlProperty(isAttribute = true)
    @XmlAttribute(name = "rel", required = true)
    private String rel;
    @JacksonXmlProperty(isAttribute = true)
    @XmlAttribute(name = "type", required = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    @XmlAttribute(name = "href", required = true)
    private String href;


    public Link(String rel, String type, String href) {
        this.rel = rel;
        this.type= type;
        this.href= href;
    }

    public Link() {

    }


    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }


}

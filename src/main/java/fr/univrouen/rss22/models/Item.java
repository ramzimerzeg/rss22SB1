package fr.univrouen.rss22.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@Document(collection = "rss22")
@CompoundIndex(unique = true, def = "{'title': 1, 'published': 1}")
public class Item implements Serializable {
	@Id
	@XmlElement
	private String guid;
	@XmlElement
	private String title;
	@XmlElement
	private Category category;
	@XmlElement
	private String published;
	@XmlElement
	private String updated;
	@XmlElement
	private Image image;
	@XmlElement
	private Content content;
	@XmlElement
	private Author author;
	@XmlElement
	private Author contributor;

	public Item(String guid, String title,Category category, String published, String updated, Image image, Content content, Author author, Author contributor) {
		this.guid = guid;
		this.title = title;
		this.category = category;
		this.published = published;
		this.updated = updated;
		this.image = image;
		this.content = content;
		this.author = author;
		this.contributor = contributor;
	}

	public Item() {
		
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getPublished() {
		return published;
	}


	public void setPublished(String published) { this.published = published; }

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Author getContributor() {
		return contributor;
	}

	public void setContributor(Author contributor) {
		this.contributor = contributor;
	}


	@Override
	public String toString() {
		return ("Article : " + title + "\n(" + guid + ") Le = " + published );
	}
}
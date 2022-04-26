package fr.univrouen.rss22.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "articles")
public class Item implements Serializable {

	@Id
	private String guid;

	private String title;
	private String category;
	private String published;
	private String image;
	private String contentType;
	private String content;
	private String authors;
	private String email;
	private String uri;

	public Item(String guid, String title, String published) {
		this.guid = guid;
		this.title = title;
		this.published = published;
	}

	public Item() {
		
	}

	public Item(String guid, String title, String category, String published, String image, String contentType, String content, String authors, String email, String uri) {
		this.guid = guid;
		this.title = title;
		this.category = category;
		this.published = published;
		this.image = image;
		this.contentType = contentType;
		this.content = content;
		this.authors = authors;
		this.email = email;
		this.uri = uri;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getGuid() {
		return guid;
	}

	public String getTitle() {
		return title;
	}

	public String getPublished() {
		return published;
	}

	@Override
	public String toString() {
		return ("Article : " + title + "\n(" + guid + ") Le = " + published );
	}
}
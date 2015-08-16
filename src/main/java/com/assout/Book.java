package com.assout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@Data
@DatabaseTable(tableName = "book")
public class Book {
	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField
	private String title;
	@DatabaseField
	private String content;
	@DatabaseField
	private Date createdAt;
	@DatabaseField
	private String summary;
	@DatabaseField
	private Boolean deleted;

	@Deprecated
	public Book() { // ORMLite needs a no-arg constructor 
	}

	public Book(String title, String summary, String content) {
		this.title = title;
		this.summary = summary;
		this.content = content;
		this.createdAt = new Date();
		this.deleted = false;
	}

	public void delete() {
		this.deleted = true;
	}

	public Boolean readable() {
		return !this.deleted;
	}

	public String getCreatedAt() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(this.createdAt);
	}

	public String getEditLink() {
		return "<a href='/book/update/" + this.id + "'>Edit</a>";
	}

	public String getDeleteLink() {
		return "<a href='/book/delete/" + this.id + "'>Delete</a>";
	}

	public String getSummaryLink() {
		return "<a href='/book/read/" + this.id + "'>" + this.summary + "</a>";
	}
}

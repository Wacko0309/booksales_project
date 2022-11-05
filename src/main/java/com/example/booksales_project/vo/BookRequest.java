package com.example.booksales_project.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookRequest {

	@JsonProperty(value = "ISBN")
	private String isbn; 
	//â¡è„@JsonPropertyò“îñ∆ëSëÂõçÊ§éÊïsìûìIèÓãµ
	private String name;
	private String author;
	private Integer price; 
	private Integer stock;
	private String type;
	private Integer number;
	private String mode;
	
	public BookRequest() {
	
	}
	public BookRequest(String isbn, String name, String author, Integer price, Integer stock, String type, String mode) {
		
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.price = price;
		this.stock = stock;
		this.type = type;
		this.mode = mode;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}
	
	

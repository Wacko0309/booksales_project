package com.example.booksales_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bookstore")
public class Book {
	
	@Id
	@Column(name = "ISBN")
	private String ISBN;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "price")
	private int price;
	
	@Column(name = "sales")
	private int sales;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "stock")
	private int stock;

	public Book() {
		
	}

	public Book(String ISBN, String name, String author, int price, String type, int stock) {
		super();
		this.ISBN = ISBN;
		this.name = name;
		this.author = author;
		this.price = price;
		this.type = type;
		this.stock = stock;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
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

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	

}


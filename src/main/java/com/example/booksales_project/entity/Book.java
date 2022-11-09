package com.example.booksales_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) //–hŽ~null‰ñœä
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
	private Integer price;
	
	@Column(name = "sales")
	private Integer sales;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "stock")
	private Integer stock;

	public Book() {
		
	}
	
	public Book(String ISBN, String name, String author, Integer price, String type) {
		super();
		this.ISBN = ISBN;
		this.name = name;
		this.author = author;
		this.price = price;
		this.type = type;
	}

	public Book(String ISBN, String name, String author, Integer price, Integer sales, String type, Integer stock) {
		super();
		this.ISBN = ISBN;
		this.name = name;
		this.author = author;
		this.price = price;
		this.sales = sales;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	

}


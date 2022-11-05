package com.example.booksales_project.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) //–hŽ~null‰ñœä

public class BookSerchResponse {
	private List<BookSerchResponse> bookList;
	
	private String isbn;
	
	private String name;

	private String author;

	private Integer price;
	
	private Integer sales;
	
	private Integer stock;
	
	private String message;
	
	
	public BookSerchResponse() {
		
	}

	public BookSerchResponse(String isbn, String name, String author, Integer price, Integer sales, Integer stock) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.price = price;
		this.sales = sales;
		this.stock = stock;
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

	public List<BookSerchResponse> getBookList() {
		return bookList;
	}

	public void setBookList(List<BookSerchResponse> bookList) {
		this.bookList = bookList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
}

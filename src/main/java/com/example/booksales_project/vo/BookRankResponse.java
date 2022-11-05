package com.example.booksales_project.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL) //–hŽ~null‰ñœä

public class BookRankResponse {
	
	private List<BookRankResponse> bookList;
	
	private String isbn;
	
	private String name;

	private String author;

	private Integer price;
	
	private String message;
	
	
	public BookRankResponse() {
		
	}

	public BookRankResponse(String isbn, String name, String author, Integer price) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.price = price;
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

	public List<BookRankResponse> getBookList() {
		return bookList;
	}

	public void setBookList(List<BookRankResponse> bookList) {
		this.bookList = bookList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}

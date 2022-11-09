package com.example.booksales_project.vo;

import com.example.booksales_project.entity.Book;

public class BookDetail extends Book{
	
	private Integer number;
	
	

	public BookDetail(Book book, Integer number) {
		super(book.getISBN(), book.getName(), book.getAuthor(), book.getPrice(), book.getType());
		this.number = number;
		
	}
	
	public BookDetail(Book book) {
		super(book.getISBN(), book.getName(), book.getAuthor(), book.getPrice(), book.getType());
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}

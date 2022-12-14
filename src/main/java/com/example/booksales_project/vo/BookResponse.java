package com.example.booksales_project.vo;

import java.util.List;
import java.util.Map;

import com.example.booksales_project.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) //?h?~null????
public class BookResponse {
	
	private List<Book> bookList;
	
	private Book book;
	
	private Map<String,Integer> map;
	
	private String message;
	
	private List<String> messageList;
	
	private List<BookDetail> bookDetails;
	
	private List<BookDetail> bookRank;
	
	private Integer total;

	public BookResponse() {

	}

	public BookResponse(Book book, String message) {
		super();
		this.book = book;
		this.message = message;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}

	public List<BookDetail> getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(List<BookDetail> bookDetails) {
		this.bookDetails = bookDetails;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<BookDetail> getBookRank() {
		return bookRank;
	}

	public void setBookRank(List<BookDetail> bookRank) {
		this.bookRank = bookRank;
	}

	
}

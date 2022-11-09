package com.example.booksales_project.service.ifs;

import java.util.Map;

import com.example.booksales_project.vo.BookResponse;


public interface BookSale {
	
	public BookResponse addBook(String ISBN, String name, String author, int price, String type, int stock);
	
	public BookResponse deleteById(String ISBN);
	
	public BookResponse findByType(String type);
	
	public BookResponse bookSearch(String mode, String ISBN, String name, String author);
	
	public BookResponse priceUpdate(String ISBN, int price);
	
	public BookResponse stockUpdate(String ISBN, int stock);
	
	public BookResponse costomerSaleService(Map<String, Integer> buyMap);
	
	public BookResponse salesRank();

}

package com.example.booksales_project.service.ifs;

import java.util.List;

import com.example.booksales_project.vo.BookRankResponse;
import com.example.booksales_project.vo.BookRequest;
import com.example.booksales_project.vo.BookResponse;


public interface BookSale {
	
	public BookResponse addBook(String ISBN, String name, String author, int price, String type, int stock);
	
	public BookResponse deleteById(String ISBN);
	
	public BookResponse findByType(String type);
	
	public BookResponse bookSerch(String mode, String ISBN, String name, String author);
	
	public BookResponse priceUpdate(List<BookRequest> priceList);
	
	public BookResponse stockUpdate(List<BookRequest> stockList);
	
	public BookResponse costomerSaleService(List<BookRequest> costomerBuyList);
	
	public List<BookRankResponse> salesRank();

}

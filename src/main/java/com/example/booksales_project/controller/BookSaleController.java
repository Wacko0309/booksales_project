package com.example.booksales_project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.booksales_project.service.ifs.BookSale;
import com.example.booksales_project.vo.BookRequest;
import com.example.booksales_project.vo.BookResponse;


@RestController
public class BookSaleController {

	@Autowired
	private BookSale bookSale;
	
	@PostMapping(value = "/api/addBook")
	public BookResponse addBook(@RequestBody BookRequest request) {
		return bookSale.addBook(request.getIsbn(),request.getName(),request.getAuthor(),
				request.getPrice(),request.getType(),request.getStock());
	}
	@PostMapping(value = "/api/deleteById")
	public BookResponse deleteById(@RequestBody BookRequest request){
		return bookSale.deleteById(request.getIsbn());
	}
	@PostMapping(value = "/api/bookSerchfindByType")
	public BookResponse findByType(@RequestBody BookRequest request) {
		return bookSale.findByType(request.getType());
	}
	@PostMapping(value = "/api/bookSerch")
	public BookResponse bookSerch(@RequestBody BookRequest request) {
		return bookSale.bookSearch(request.getMode(), request.getIsbn(), request.getName(), request.getAuthor());
	}
	@PostMapping(value = "/api/priceUpdate")
	public BookResponse priceUpdate(@RequestBody BookRequest request){
		return bookSale.priceUpdate(request.getIsbn(),request.getPrice());
	}
	@PostMapping(value = "/api/stockUpdate")
	public BookResponse stockUpdate(@RequestBody BookRequest request){
		return bookSale.stockUpdate(request.getIsbn(),request.getStock());
	}
	@PostMapping(value = "/api/costomerSaleService")
	public BookResponse costomerSaleService(@RequestBody BookRequest request) {
		return bookSale.costomerSaleService(request.getBuyMap());
	}
	@PostMapping(value = "/api/salesRank")
	public BookResponse salesRank() {
		return bookSale.salesRank();
	}


}

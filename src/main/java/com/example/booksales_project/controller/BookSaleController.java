package com.example.booksales_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.booksales_project.service.ifs.BookSale;
import com.example.booksales_project.vo.BookRankResponse;
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
		return bookSale.bookSerch(request.getMode(), request.getIsbn(), request.getName(), request.getAuthor());
	}
	@PostMapping(value = "/api/priceUpdate")
	public BookResponse priceUpdate(@RequestBody List<BookRequest> priceList){
		return bookSale.priceUpdate(priceList);
	}
	@PostMapping(value = "/api/stockUpdate")
	public BookResponse stockUpdate(@RequestBody List<BookRequest> stockList){
		return bookSale.stockUpdate(stockList);
	}
	@PostMapping(value = "/api/costomerSaleService")
	public BookResponse costomerSaleService(@RequestBody List<BookRequest> buyList) {
		return bookSale.costomerSaleService(buyList);
	}
	@PostMapping(value = "/api/salesRank")
	public List<BookRankResponse> salesRank() {
		return bookSale.salesRank();
	}


}

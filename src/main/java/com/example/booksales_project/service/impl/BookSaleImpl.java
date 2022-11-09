package com.example.booksales_project.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.booksales_project.constants.BookRtnCode;
import com.example.booksales_project.entity.Book;
import com.example.booksales_project.repository.BookDao;
import com.example.booksales_project.service.ifs.BookSale;
import com.example.booksales_project.vo.BookDetail;
import com.example.booksales_project.vo.BookResponse;

@Service
public class BookSaleImpl implements BookSale {

	@Autowired
	private BookDao bookDao;

	@Override
	public BookResponse addBook(String ISBN, String name, String author, int price, String type, int stock) {
		BookResponse res = new BookResponse();
		/* 以下IF判斷輸入不能為空 */
		if (!StringUtils.hasText(ISBN)) {
			res.setMessage(BookRtnCode.ISBN_REQUIRED.getMessage());
			return res; // 返回錯誤請求，不然會到50行
		} else if (!StringUtils.hasText(author)) {
			res.setMessage(BookRtnCode.AUTHOR_REQUIRED.getMessage());
			return res;
		} else if (!StringUtils.hasText(name)) {
			res.setMessage(BookRtnCode.NAME_REQUIRED.getMessage());
			return res;
		} else if (price < 0) {
			res.setMessage(BookRtnCode.PRICE_REQUIRED.getMessage());
			return res;
		} else if (!StringUtils.hasText(type)) {
			res.setMessage(BookRtnCode.TYPE_REQUIRED.getMessage());
			return res;
		} else if (stock < 0) {
			res.setMessage(BookRtnCode.STOCK_REQUIRED.getMessage());
			return res;
		}
		/* ISBN不可重複，判斷ISBN即可 */
		if (bookDao.existsById(ISBN)) {
			res.setMessage("品項已存在");
			return res;
		}
		Book book = new Book();
		book.setISBN(ISBN);
		book.setAuthor(author);
		book.setName(name);
		book.setType(type);
		book.setPrice(price);
		book.setStock(stock);
		res.setBook(book);
		bookDao.save(book);
		res.setMessage(BookRtnCode.SUCCESSFUL.getMessage());
		return res;
	}

	@Override
	public BookResponse deleteById(String ISBN) {
		BookResponse res = new BookResponse();
		if (!StringUtils.hasText(ISBN)) {
			res.setMessage(BookRtnCode.ISBN_REQUIRED.getMessage());
			return res;
		}
		if (bookDao.existsById(ISBN)) {
			bookDao.deleteById(ISBN);
			res.setMessage(BookRtnCode.SUCCESSFUL.getMessage());
			return res;
		}
		res.setMessage(BookRtnCode.ISBN_FAILURE.getMessage());
		return res;
	}

	@Override
	public BookResponse findByType(String type) {
		BookResponse res = new BookResponse();
		if (!StringUtils.hasText(type)) {
			res.setMessage("請填入資訊");
			return res;
		}
		Set<String> typeSet = new HashSet<>();
		String[] typeArray = type.split(",");
		for (String item : typeArray) {
			String str = item.trim();
			typeSet.add(str);
		}
		List<Book> bookList = new ArrayList<>();
		for (String newItem : typeSet) {
			List<Book> bookOp = bookDao.findAllByTypeContaining(newItem);
			if (bookOp.isEmpty()) {
				res.setMessage("查無此分類書籍");
				return res;
			}
			Set<Book> bookSet = new HashSet<>(bookOp);
			bookList.addAll(bookSet); // 直接回傳
			res.setBookList(bookList);
		}
		return res;
	}

	@Override
	public BookResponse bookSearch(String mode, String ISBN, String name, String author) {
		BookResponse res = new BookResponse();
		/* 下權限顯示不同結果 */
		if (StringUtils.hasText(ISBN)) {
			Optional<Book> bookOp = bookDao.findById(ISBN);
			if (bookOp.isPresent()) {
				Book book = bookOp.get();
				if (!mode.equalsIgnoreCase("AgentMode")) {
					book.setSales(null);
					book.setStock(null);
					res.setBook(book);
				} else {
					res.setBook(book);
				}
				return res;
			}
		} else if (StringUtils.hasText(name)) {
			List<Book> bookOp = bookDao.findAllByName(name);
			if (!mode.equalsIgnoreCase("AgentMode")) {
				return setRes(bookOp);
			}
			res.setBookList(bookOp);
			return res;
		} else if (StringUtils.hasText(author)) {
			List<Book> bookOp = bookDao.findAllByAuthor(author);
			if (!mode.equalsIgnoreCase("AgentMode")) {
				return setRes(bookOp);
			}
			res.setBookList(bookOp);
			return res;
		}
		res.setMessage("not found");
		return res;
	}

	private BookResponse setRes(List<Book> bookOp) {
		BookResponse res = new BookResponse();
		List<Book> bookList = new ArrayList<>();
		for (Book item : bookOp) {
			Book book = new Book();
			book.setISBN(item.getISBN());
			book.setAuthor(item.getAuthor());
			book.setName(item.getName());
			book.setSales(null);
			book.setStock(null);
			book.setPrice(item.getPrice());
			bookList.add(book);
		}
		res.setBookList(bookList);
		return res;
	}

	@Override
	public BookResponse priceUpdate(String ISBN, int price) {
		List<String> messageList = new ArrayList<>(); // 更新明細
		BookResponse res = new BookResponse();
		if (!StringUtils.hasText(ISBN)) {
			res.setMessage(BookRtnCode.ISBN_REQUIRED.getMessage());
			return res;
		}
		if (price <= 0) { // 若價格小於0則報錯
			res.setMessage(BookRtnCode.PRICE_REQUIRED.getMessage());
			return res;
		}
		// 判斷ISBN是否為空,是則跳過
		Optional<Book> bookOp = bookDao.findById(ISBN);
		if (bookOp.isPresent()) { // 若資料庫中無符合ISBN,跳出
			messageList.add(BookRtnCode.SUCCESSFUL.getMessage() + " ,更新價格: " + price);
			Book book = bookOp.get();
			/* 更新價格(price) */
			book.setPrice(price);
			res.setBook(book);
			bookDao.save(book);
		} else {
			messageList.add("無此書");
		}
		res.setMessageList(messageList);
		return res;
	}

	@Override
	public BookResponse stockUpdate(String ISBN, int stock) {

		List<String> messageList = new ArrayList<>(); // 更新明細
		BookResponse res = new BookResponse();
		if (!StringUtils.hasText(ISBN)) {
			res.setMessage(BookRtnCode.ISBN_REQUIRED.getMessage());
			return res;
		} else if (stock <= 0) { // 若進貨量小於0則報錯
			res.setMessage("Stock ERROR");
			return res;
		}
		Optional<Book> bookOp = bookDao.findById(ISBN);
		if (bookOp.isPresent()) { // 若資料庫中無符合ISBN,跳出
			messageList.add(BookRtnCode.SUCCESSFUL.getMessage() + " ,庫存數: " + (bookOp.get().getStock() + stock));
			Book book = new Book();
			book = bookOp.get();
			/* 進貨(Stock) */
			book.setStock(bookOp.get().getStock() + stock);
			res.setBook(book);
			bookDao.save(book);
		} else {
			messageList.add("無此書");
		}
		res.setMessageList(messageList);
		return res;
	}

	@Override
	public BookResponse costomerSaleService(Map<String, Integer> buyMap) {
		List<String> isbnList = new ArrayList<>();
		BookResponse res = new BookResponse();
		List<BookDetail> bookDetails = new ArrayList<>();
		int total = 0; // 購書總價
		for (Entry<String, Integer> checkMap : buyMap.entrySet()) {
			if (!StringUtils.hasText(checkMap.getKey())) {
				continue; // 判斷ISBN是否為空,是則跳過
			} 
			isbnList.add(checkMap.getKey());
		}
		List<Book> bookList = bookDao.findAllByISBNIn(isbnList);
		for (Book item : bookList) {
			String key = "";
			int value = 0;
			for (Entry<String, Integer> checkMap : buyMap.entrySet()) {
				key = checkMap.getKey();
				value = checkMap.getValue();
				if (key.equalsIgnoreCase(item.getISBN())) {
					if (value < 0 || value > item.getStock()) {
						value = 0;
					}
					break;
				}
			}
			BookDetail bookDetail = new BookDetail(item, value);
			bookDetails.add(bookDetail);
			total += value * item.getPrice();
						/* 更新銷售額(Sales)及庫存(Stock) */
			item.setSales(item.getSales() + value);
			item.setStock(item.getStock() - value);
			bookDao.save(item);
		}
		res.setTotal(total);
		res.setBookDetails(bookDetails);
		return res;
	}
		

	@Override
	public BookResponse salesRank() {
		BookResponse res = new BookResponse();
		List<BookDetail> bookRank = new ArrayList<>();
		List<Book> rankList = bookDao.findTop5ByOrderBySalesDesc();
		for (Book item : rankList) {
			BookDetail bookDetail = new BookDetail(item);
			bookRank.add(bookDetail);
		}
		res.setBookRank(bookRank);
		return res;
	}

}

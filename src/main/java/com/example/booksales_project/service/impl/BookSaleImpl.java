package com.example.booksales_project.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.booksales_project.constants.BookRtnCode;
import com.example.booksales_project.entity.Book;
import com.example.booksales_project.repository.BookDao;
import com.example.booksales_project.service.ifs.BookSale;
import com.example.booksales_project.vo.BookRankResponse;
import com.example.booksales_project.vo.BookRequest;
import com.example.booksales_project.vo.BookResponse;

@Service
public class BookSaleImpl implements BookSale {

	@Autowired
	private BookDao bookDao;

	@Override
	public BookResponse addBook(String ISBN, String name, String author, int price, String type, int stock) {
		Book book = new Book();
		BookResponse res = new BookResponse();
		/* ÈºIF»ÐAüs\×ó */
		if (!StringUtils.hasText(ISBN)) {
			res.setMessage(BookRtnCode.ISBN_REQUIRED.getMessage());
			return res; // Ôñöë¿CsRð50s
		} else if (!StringUtils.hasText(author)) {
			res.setMessage(BookRtnCode.AUTHOR_REQUIRED.getMessage());
			return res;
		} else if (!StringUtils.hasText(name)) {
			res.setMessage(BookRtnCode.NAME_REQUIRED.getMessage());
			return res;
		} else if (price <= 0) {
			res.setMessage(BookRtnCode.PRICE_REQUIRED.getMessage());
			return res;
		} else if (!StringUtils.hasText(type)) {
			res.setMessage(BookRtnCode.TYPE_REQUIRED.getMessage());
			return res;
		} else if (stock <= 0) {
			res.setMessage(BookRtnCode.STOCK_REQUIRED.getMessage());
			return res;
		}
		/* ISBNsÂd¡C»ÐISBN¦Â */
		if (bookDao.existsById(ISBN)) {
			res.setMessage("iß¶Ý");
		} else {
			book.setISBN(ISBN);
			book.setAuthor(author);
			book.setName(name);
			book.setType(type);
			book.setPrice(price);
			book.setStock(stock);
			res.setBook(book);
			bookDao.save(res.getBook());
			res.setMessage(BookRtnCode.SUCCESSFUL.getMessage());
		}
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
		} else {
			res.setMessage(BookRtnCode.ISBN_FAILURE.getMessage());
		}
		return res;
	}

	@Override
	public BookResponse findByType(String type) {
		BookResponse res = new BookResponse();
		if (!StringUtils.hasText(type)) {
			res.setMessage("¿Uüu");
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
			String str = newItem.trim();
			List<Book> bookOp = bookDao.findAllByTypeContaining(str);
			bookList.addAll(bookOp);
			res.setBookList(bookList);
		}
		res.setMessage("¿Aü³mè®");
		return res;
	}

	@Override
	public BookResponse bookSerch(String mode, String ISBN, String name, String author) {
		BookResponse res = new BookResponse();
		List<String> messageList = new ArrayList<>();
		/*ºÜÀèû¦s¯Ê*/
		if (!mode.equalsIgnoreCase("AgentMode")) {
			/*á³AüAgentMode¥sèû¦ç÷ÊyÉ¶*/
			if (StringUtils.hasText(ISBN)) {
				List<Book> bookOp = bookDao.findAllByISBN(ISBN);
				for (Book item : bookOp) {
					messageList.add("¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,Ji: "
						+ item.getPrice() + " ,ìÒ: " + item.getAuthor());
				}
				res.setMessageList(messageList);
				return res;
			} else if (StringUtils.hasText(name)) {
				List<Book> bookOp = bookDao.findAllByName(name);
				for (Book item : bookOp) {
					messageList.add("¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,Ji: "
						+ item.getPrice() + " ,ìÒ: " + item.getAuthor());
				}
				res.setMessageList(messageList);
				return res;
			} else if (StringUtils.hasText(author)) {
				List<Book> bookOp = bookDao.findAllByAuthor(author);
				for (Book item : bookOp) {
					messageList.add("¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,Ji: "
						+ item.getPrice() + " ,ìÒ: " + item.getAuthor());
				}
				res.setMessageList(messageList);
				return res;
			}
			res.setMessage("not found");
			return res;
			/*ºÊ×AgentModeèû¦Ê*/
		} if (StringUtils.hasText(ISBN)) {
			List<Book> bookOp = bookDao.findAllByISBN(ISBN);
			for (Book item : bookOp) {
				messageList.add("¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,Ji: "
					+ item.getPrice() + " ,ìÒ: " + item.getAuthor() + " ,ç÷Ê: " +  item.getSales() + " ,É¶: " + item.getStock());
			}
			res.setMessageList(messageList);
			return res;
		} else if (StringUtils.hasText(name)) {
			List<Book> bookOp = bookDao.findAllByName(name);
			for (Book item : bookOp) {
				messageList.add("¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,Ji: "
					+ item.getPrice() + " ,ìÒ: " + item.getAuthor() + " ,ç÷Ê: " +  item.getSales() + " ,É¶: " + item.getStock());
			}
			res.setMessageList(messageList);
			return res;
		} else if (StringUtils.hasText(author)) {
			List<Book> bookOp = bookDao.findAllByAuthor(author);
			for (Book item : bookOp) {
				messageList.add("¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,Ji: "
					+ item.getPrice() + " ,ìÒ: " + item.getAuthor() + " ,ç÷Ê: " +  item.getSales() + " ,É¶: " + item.getStock());
			}
			res.setMessageList(messageList);
			return res;
		}
		res.setMessage("not found");
		return res;
	}

	@Override
	public BookResponse priceUpdate(List<BookRequest> priceList) {
		Book book = new Book();
		List<String> messageList = new ArrayList<>(); // XV¾×
		Map<String, Integer> map = new HashMap<>(); // XV¼(key)AJi(value)
		BookResponse res = new BookResponse();
		for (BookRequest orderItem : priceList) {
			if (!StringUtils.hasText(orderItem.getName())) {
				continue; // »Ð¼¥Û×ó,¥¥µß
			}
			Optional<Book> bookOp = bookDao.findByName(orderItem.getName());
			if (bookOp.isPresent()) { // á¿É³¼,µo
				if (orderItem.getPrice() < 0) { // áJi¬0¥ñö
					messageList.add("Price ERROR");
				}
				map.put(orderItem.getName(), orderItem.getPrice());
				messageList.add("¼: " + orderItem.getName() + " ,ISBN: " + bookOp.get().getISBN() + " ,XVJi: "
						+ orderItem.getPrice() + " ,ìÒ: " + bookOp.get().getAuthor() + " ,É¶éP: "
						+ bookOp.get().getStock());
				book.setISBN(bookOp.get().getISBN());
				book.setAuthor(bookOp.get().getAuthor());
				book.setName(bookOp.get().getName());
				/* XVJi(price) */
				book.setPrice(orderItem.getPrice());
				book.setType(bookOp.get().getType());
				book.setSales(bookOp.get().getSales());
				book.setStock(bookOp.get().getStock());
				bookDao.save(book);
				continue;
			} else {
				messageList.add("³");
			}
		}
		res.setMessageList(messageList);
		res.setMap(map);
		return res;
	}

	@Override
	public BookResponse stockUpdate(List<BookRequest> stockList) {
		Book book = new Book();
		List<String> messageList = new ArrayList<>(); // XV¾×
		Map<String, Integer> map = new HashMap<>(); // XV¼(key)AiÝÊ(value)
		BookResponse res = new BookResponse();
		for (BookRequest orderItem : stockList) {
			if (!StringUtils.hasText(orderItem.getName())) {
				continue; // »Ð¼¥Û×ó,¥¥µß
			}
			Optional<Book> bookOp = bookDao.findByName(orderItem.getName());
			if (bookOp.isPresent()) { // á¿É³¼,µo
				if (orderItem.getStock() < 0) { // áiÝÊ¬0¥ñö
					messageList.add("Stock ERROR");
				}
				map.put(orderItem.getName(), orderItem.getStock());
				messageList.add("¼: " + orderItem.getName() + " ,ISBN: " + bookOp.get().getISBN() + " ,Ji: "
						+ bookOp.get().getPrice() + " ,ìÒ: " + bookOp.get().getAuthor() + " ,É¶É: "
						+ (bookOp.get().getStock() + orderItem.getStock()));
				book.setISBN(bookOp.get().getISBN());
				book.setAuthor(bookOp.get().getAuthor());
				book.setName(bookOp.get().getName());
				book.setPrice(orderItem.getPrice());
				book.setType(bookOp.get().getType());
				book.setSales(bookOp.get().getSales());
				/* iÝ(Stock) */
				book.setStock(bookOp.get().getStock() + orderItem.getStock());
				bookDao.save(book);
				continue;
			} else {
				messageList.add("³");
			}
		}
		res.setMessageList(messageList);
		res.setMap(map);
		return res;
	}

	@Override
	public BookResponse costomerSaleService(List<BookRequest> buyList) {
		Book book = new Book();
		List<String> messageList = new ArrayList<>(); // w¾×
		Map<String, Integer> map = new HashMap<>(); // w¼(key)AÉÊ(value)
		int total = 0; // wã`J
		BookResponse res = new BookResponse();
		for (BookRequest orderItem : buyList) {
			if (!StringUtils.hasText(orderItem.getName())) {
				continue; // »Ð¼¥Û×ó,¥¥µß
			}
			Optional<Book> bookOp = bookDao.findByName(orderItem.getName());
			if (bookOp.isPresent()) { // á¿É³¼,µo
				if (orderItem.getNumber() < 0) { // áwÉÊ¬0¥Ý×0
					orderItem.setNumber(0);
				}
				map.put(orderItem.getName(), orderItem.getNumber());
				total += orderItem.getNumber() * bookOp.get().getPrice();
				messageList.add("i: " + orderItem.getName() + " ,ISBN: " + bookOp.get().getISBN() + " ,ìÒ: "
						+ bookOp.get().getAuthor() + " ,ÉÊ: " + orderItem.getNumber() + " ,ã`àz: " + total + " ,É¶éP: "
						+ (bookOp.get().getStock() - orderItem.getNumber()));

				book.setISBN(bookOp.get().getISBN());
				book.setAuthor(bookOp.get().getAuthor());
				book.setName(bookOp.get().getName());
				book.setPrice(bookOp.get().getPrice());
				book.setType(bookOp.get().getType());
				/* XVç÷Sz(Sales)yÉ¶(Stock) */
				book.setSales(bookOp.get().getSales() + orderItem.getNumber());
				book.setStock(bookOp.get().getStock() - orderItem.getNumber());
				bookDao.save(book);
				continue;
			} else {
				orderItem.setNumber(0); // w¼s¿¥wÉÊÝ×0
				messageList.add("³iJi×0");
			}
		}

		res.setMessageList(messageList);
		res.setMap(map);
		return res;
	}

	@Override
	public List<BookRankResponse> salesRank() {
		List<BookRankResponse> list = new ArrayList<>();
		List<Book> rankList = bookDao.findTop5ByOrderBySalesDesc();
		for (Book item : rankList) {
			BookRankResponse res = new BookRankResponse();
			res.setAuthor(item.getAuthor());
			res.setIsbn(item.getISBN());
			res.setName(item.getName());
			res.setPrice(item.getPrice());
			list.add(res);
		}
		return list;
	}

}

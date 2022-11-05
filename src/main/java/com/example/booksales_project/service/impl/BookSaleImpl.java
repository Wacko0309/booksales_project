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
		/* ˆÈ‰ºIF”»Ğ—A“ü•s”\ˆ×‹ó */
		if (!StringUtils.hasText(ISBN)) {
			res.setMessage(BookRtnCode.ISBN_REQUIRED.getMessage());
			return res; // •Ô‰ñöŒë¿‹C•s‘R˜ğ“50s
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
		/* ISBN•s‰Âd•¡C”»ĞISBN‘¦‰Â */
		if (bookDao.existsById(ISBN)) {
			res.setMessage("•i€›ß‘¶İ");
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
			res.setMessage("¿“U“ü‘u");
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
		res.setMessage("¿—A“ü³ŠmèŒ®š");
		return res;
	}

	@Override
	public BookResponse bookSerch(String mode, String ISBN, String name, String author) {
		BookResponse res = new BookResponse();
		List<String> messageList = new ArrayList<>();
		/*‰ºÜŒÀèû¦•s“¯Œ‹‰Ê*/
		if (!mode.equalsIgnoreCase("AgentMode")) {
			/*á–³—A“üAgentMode‘¥•sèû¦ç÷—Ê‹yŒÉ‘¶*/
			if (StringUtils.hasText(ISBN)) {
				List<Book> bookOp = bookDao.findAllByISBN(ISBN);
				for (Book item : bookOp) {
					messageList.add("‘–¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,™JŠi: "
						+ item.getPrice() + " ,ìÒ: " + item.getAuthor());
				}
				res.setMessageList(messageList);
				return res;
			} else if (StringUtils.hasText(name)) {
				List<Book> bookOp = bookDao.findAllByName(name);
				for (Book item : bookOp) {
					messageList.add("‘–¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,™JŠi: "
						+ item.getPrice() + " ,ìÒ: " + item.getAuthor());
				}
				res.setMessageList(messageList);
				return res;
			} else if (StringUtils.hasText(author)) {
				List<Book> bookOp = bookDao.findAllByAuthor(author);
				for (Book item : bookOp) {
					messageList.add("‘–¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,™JŠi: "
						+ item.getPrice() + " ,ìÒ: " + item.getAuthor());
				}
				res.setMessageList(messageList);
				return res;
			}
			res.setMessage("not found");
			return res;
			/*‰º–Êˆ×AgentModeèû¦Œ‹‰Ê*/
		} if (StringUtils.hasText(ISBN)) {
			List<Book> bookOp = bookDao.findAllByISBN(ISBN);
			for (Book item : bookOp) {
				messageList.add("‘–¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,™JŠi: "
					+ item.getPrice() + " ,ìÒ: " + item.getAuthor() + " ,ç÷—Ê: " +  item.getSales() + " ,ŒÉ‘¶: " + item.getStock());
			}
			res.setMessageList(messageList);
			return res;
		} else if (StringUtils.hasText(name)) {
			List<Book> bookOp = bookDao.findAllByName(name);
			for (Book item : bookOp) {
				messageList.add("‘–¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,™JŠi: "
					+ item.getPrice() + " ,ìÒ: " + item.getAuthor() + " ,ç÷—Ê: " +  item.getSales() + " ,ŒÉ‘¶: " + item.getStock());
			}
			res.setMessageList(messageList);
			return res;
		} else if (StringUtils.hasText(author)) {
			List<Book> bookOp = bookDao.findAllByAuthor(author);
			for (Book item : bookOp) {
				messageList.add("‘–¼: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,™JŠi: "
					+ item.getPrice() + " ,ìÒ: " + item.getAuthor() + " ,ç÷—Ê: " +  item.getSales() + " ,ŒÉ‘¶: " + item.getStock());
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
		List<String> messageList = new ArrayList<>(); // XV–¾×
		Map<String, Integer> map = new HashMap<>(); // XV‘–¼(key)A™JŠi(value)
		BookResponse res = new BookResponse();
		for (BookRequest orderItem : priceList) {
			if (!StringUtils.hasText(orderItem.getName())) {
				continue; // ”»Ğ‘–¼¥”Ûˆ×‹ó,¥‘¥’µ‰ß
			}
			Optional<Book> bookOp = bookDao.findByName(orderItem.getName());
			if (bookOp.isPresent()) { // á‘—¿ŒÉ’†–³•„‡‘–¼,’µo
				if (orderItem.getPrice() < 0) { // á™JŠi¬‰—0‘¥•ñö
					messageList.add("Price ERROR");
				}
				map.put(orderItem.getName(), orderItem.getPrice());
				messageList.add("‘–¼: " + orderItem.getName() + " ,ISBN: " + bookOp.get().getISBN() + " ,XV™JŠi: "
						+ orderItem.getPrice() + " ,ìÒ: " + bookOp.get().getAuthor() + " ,ŒÉ‘¶™”éP: "
						+ bookOp.get().getStock());
				book.setISBN(bookOp.get().getISBN());
				book.setAuthor(bookOp.get().getAuthor());
				book.setName(bookOp.get().getName());
				/* XV™JŠi(price) */
				book.setPrice(orderItem.getPrice());
				book.setType(bookOp.get().getType());
				book.setSales(bookOp.get().getSales());
				book.setStock(bookOp.get().getStock());
				bookDao.save(book);
				continue;
			} else {
				messageList.add("–³Ÿ‘");
			}
		}
		res.setMessageList(messageList);
		res.setMap(map);
		return res;
	}

	@Override
	public BookResponse stockUpdate(List<BookRequest> stockList) {
		Book book = new Book();
		List<String> messageList = new ArrayList<>(); // XV–¾×
		Map<String, Integer> map = new HashMap<>(); // XV‘–¼(key)Ai‰İ—Ê(value)
		BookResponse res = new BookResponse();
		for (BookRequest orderItem : stockList) {
			if (!StringUtils.hasText(orderItem.getName())) {
				continue; // ”»Ğ‘–¼¥”Ûˆ×‹ó,¥‘¥’µ‰ß
			}
			Optional<Book> bookOp = bookDao.findByName(orderItem.getName());
			if (bookOp.isPresent()) { // á‘—¿ŒÉ’†–³•„‡‘–¼,’µo
				if (orderItem.getStock() < 0) { // ái‰İ—Ê¬‰—0‘¥•ñö
					messageList.add("Stock ERROR");
				}
				map.put(orderItem.getName(), orderItem.getStock());
				messageList.add("‘–¼: " + orderItem.getName() + " ,ISBN: " + bookOp.get().getISBN() + " ,™JŠi: "
						+ bookOp.get().getPrice() + " ,ìÒ: " + bookOp.get().getAuthor() + " ,ŒÉ‘¶É: "
						+ (bookOp.get().getStock() + orderItem.getStock()));
				book.setISBN(bookOp.get().getISBN());
				book.setAuthor(bookOp.get().getAuthor());
				book.setName(bookOp.get().getName());
				book.setPrice(orderItem.getPrice());
				book.setType(bookOp.get().getType());
				book.setSales(bookOp.get().getSales());
				/* i‰İ(Stock) */
				book.setStock(bookOp.get().getStock() + orderItem.getStock());
				bookDao.save(book);
				continue;
			} else {
				messageList.add("–³Ÿ‘");
			}
		}
		res.setMessageList(messageList);
		res.setMap(map);
		return res;
	}

	@Override
	public BookResponse costomerSaleService(List<BookRequest> buyList) {
		Book book = new Book();
		List<String> messageList = new ArrayList<>(); // w”ƒ–¾×
		Map<String, Integer> map = new HashMap<>(); // w”ƒ‘–¼(key)AÉ—Ê(value)
		int total = 0; // w‘ã`™J
		BookResponse res = new BookResponse();
		for (BookRequest orderItem : buyList) {
			if (!StringUtils.hasText(orderItem.getName())) {
				continue; // ”»Ğ‘–¼¥”Ûˆ×‹ó,¥‘¥’µ‰ß
			}
			Optional<Book> bookOp = bookDao.findByName(orderItem.getName());
			if (bookOp.isPresent()) { // á‘—¿ŒÉ’†–³•„‡‘–¼,’µo
				if (orderItem.getNumber() < 0) { // áw”ƒÉ—Ê¬‰—0‘¥İˆ×0
					orderItem.setNumber(0);
				}
				map.put(orderItem.getName(), orderItem.getNumber());
				total += orderItem.getNumber() * bookOp.get().getPrice();
				messageList.add("•i€: " + orderItem.getName() + " ,ISBN: " + bookOp.get().getISBN() + " ,ìÒ: "
						+ bookOp.get().getAuthor() + " ,É—Ê: " + orderItem.getNumber() + " ,ã`‹àŠz: " + total + " ,ŒÉ‘¶™”éP: "
						+ (bookOp.get().getStock() - orderItem.getNumber()));

				book.setISBN(bookOp.get().getISBN());
				book.setAuthor(bookOp.get().getAuthor());
				book.setName(bookOp.get().getName());
				book.setPrice(bookOp.get().getPrice());
				book.setType(bookOp.get().getType());
				/* XVç÷šSŠz(Sales)‹yŒÉ‘¶(Stock) */
				book.setSales(bookOp.get().getSales() + orderItem.getNumber());
				book.setStock(bookOp.get().getStock() - orderItem.getNumber());
				bookDao.save(book);
				continue;
			} else {
				orderItem.setNumber(0); // w”ƒ‘–¼•s•„‘—¿‘¥w”ƒÉ—Êİˆ×0
				messageList.add("–³Ÿ•i€™JŠiˆ×0");
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

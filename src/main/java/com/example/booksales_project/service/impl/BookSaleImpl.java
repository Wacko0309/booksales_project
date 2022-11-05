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
		/* �ȉ�IF���ЗA���s�\�׋� */
		if (!StringUtils.hasText(ISBN)) {
			res.setMessage(BookRtnCode.ISBN_REQUIRED.getMessage());
			return res; // �ԉ���됿���C�s�R��50�s
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
		/* ISBN�s�d���C����ISBN���� */
		if (bookDao.existsById(ISBN)) {
			res.setMessage("�i���ߑ���");
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
			res.setMessage("���U�����u");
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
		res.setMessage("���A�����m萌���");
		return res;
	}

	@Override
	public BookResponse bookSerch(String mode, String ISBN, String name, String author) {
		BookResponse res = new BookResponse();
		List<String> messageList = new ArrayList<>();
		/*���܌������s������*/
		if (!mode.equalsIgnoreCase("AgentMode")) {
			/*�ᖳ�A��AgentMode���s�������ʋy�ɑ�*/
			if (StringUtils.hasText(ISBN)) {
				List<Book> bookOp = bookDao.findAllByISBN(ISBN);
				for (Book item : bookOp) {
					messageList.add("����: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,�J�i: "
						+ item.getPrice() + " ,���: " + item.getAuthor());
				}
				res.setMessageList(messageList);
				return res;
			} else if (StringUtils.hasText(name)) {
				List<Book> bookOp = bookDao.findAllByName(name);
				for (Book item : bookOp) {
					messageList.add("����: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,�J�i: "
						+ item.getPrice() + " ,���: " + item.getAuthor());
				}
				res.setMessageList(messageList);
				return res;
			} else if (StringUtils.hasText(author)) {
				List<Book> bookOp = bookDao.findAllByAuthor(author);
				for (Book item : bookOp) {
					messageList.add("����: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,�J�i: "
						+ item.getPrice() + " ,���: " + item.getAuthor());
				}
				res.setMessageList(messageList);
				return res;
			}
			res.setMessage("not found");
			return res;
			/*���ʈ�AgentMode��������*/
		} if (StringUtils.hasText(ISBN)) {
			List<Book> bookOp = bookDao.findAllByISBN(ISBN);
			for (Book item : bookOp) {
				messageList.add("����: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,�J�i: "
					+ item.getPrice() + " ,���: " + item.getAuthor() + " ,����: " +  item.getSales() + " ,�ɑ�: " + item.getStock());
			}
			res.setMessageList(messageList);
			return res;
		} else if (StringUtils.hasText(name)) {
			List<Book> bookOp = bookDao.findAllByName(name);
			for (Book item : bookOp) {
				messageList.add("����: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,�J�i: "
					+ item.getPrice() + " ,���: " + item.getAuthor() + " ,����: " +  item.getSales() + " ,�ɑ�: " + item.getStock());
			}
			res.setMessageList(messageList);
			return res;
		} else if (StringUtils.hasText(author)) {
			List<Book> bookOp = bookDao.findAllByAuthor(author);
			for (Book item : bookOp) {
				messageList.add("����: " + item.getName() + " ,ISBN: " + item.getISBN() + " ,�J�i: "
					+ item.getPrice() + " ,���: " + item.getAuthor() + " ,����: " +  item.getSales() + " ,�ɑ�: " + item.getStock());
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
		List<String> messageList = new ArrayList<>(); // �X�V����
		Map<String, Integer> map = new HashMap<>(); // �X�V����(key)�A�J�i(value)
		BookResponse res = new BookResponse();
		for (BookRequest orderItem : priceList) {
			if (!StringUtils.hasText(orderItem.getName())) {
				continue; // ���Џ������ۈ׋�,��������
			}
			Optional<Book> bookOp = bookDao.findByName(orderItem.getName());
			if (bookOp.isPresent()) { // �᎑���ɒ�����������,���o
				if (orderItem.getPrice() < 0) { // ��J�i����0�����
					messageList.add("Price ERROR");
				}
				map.put(orderItem.getName(), orderItem.getPrice());
				messageList.add("����: " + orderItem.getName() + " ,ISBN: " + bookOp.get().getISBN() + " ,�X�V�J�i: "
						+ orderItem.getPrice() + " ,���: " + bookOp.get().getAuthor() + " ,�ɑ����P: "
						+ bookOp.get().getStock());
				book.setISBN(bookOp.get().getISBN());
				book.setAuthor(bookOp.get().getAuthor());
				book.setName(bookOp.get().getName());
				/* �X�V�J�i(price) */
				book.setPrice(orderItem.getPrice());
				book.setType(bookOp.get().getType());
				book.setSales(bookOp.get().getSales());
				book.setStock(bookOp.get().getStock());
				bookDao.save(book);
				continue;
			} else {
				messageList.add("������");
			}
		}
		res.setMessageList(messageList);
		res.setMap(map);
		return res;
	}

	@Override
	public BookResponse stockUpdate(List<BookRequest> stockList) {
		Book book = new Book();
		List<String> messageList = new ArrayList<>(); // �X�V����
		Map<String, Integer> map = new HashMap<>(); // �X�V����(key)�A�i�ݗ�(value)
		BookResponse res = new BookResponse();
		for (BookRequest orderItem : stockList) {
			if (!StringUtils.hasText(orderItem.getName())) {
				continue; // ���Џ������ۈ׋�,��������
			}
			Optional<Book> bookOp = bookDao.findByName(orderItem.getName());
			if (bookOp.isPresent()) { // �᎑���ɒ�����������,���o
				if (orderItem.getStock() < 0) { // ��i�ݗʏ���0�����
					messageList.add("Stock ERROR");
				}
				map.put(orderItem.getName(), orderItem.getStock());
				messageList.add("����: " + orderItem.getName() + " ,ISBN: " + bookOp.get().getISBN() + " ,�J�i: "
						+ bookOp.get().getPrice() + " ,���: " + bookOp.get().getAuthor() + " ,�ɑ���: "
						+ (bookOp.get().getStock() + orderItem.getStock()));
				book.setISBN(bookOp.get().getISBN());
				book.setAuthor(bookOp.get().getAuthor());
				book.setName(bookOp.get().getName());
				book.setPrice(orderItem.getPrice());
				book.setType(bookOp.get().getType());
				book.setSales(bookOp.get().getSales());
				/* �i��(Stock) */
				book.setStock(bookOp.get().getStock() + orderItem.getStock());
				bookDao.save(book);
				continue;
			} else {
				messageList.add("������");
			}
		}
		res.setMessageList(messageList);
		res.setMap(map);
		return res;
	}

	@Override
	public BookResponse costomerSaleService(List<BookRequest> buyList) {
		Book book = new Book();
		List<String> messageList = new ArrayList<>(); // �w������
		Map<String, Integer> map = new HashMap<>(); // �w������(key)�A�ɗ�(value)
		int total = 0; // �w���`�J
		BookResponse res = new BookResponse();
		for (BookRequest orderItem : buyList) {
			if (!StringUtils.hasText(orderItem.getName())) {
				continue; // ���Џ������ۈ׋�,��������
			}
			Optional<Book> bookOp = bookDao.findByName(orderItem.getName());
			if (bookOp.isPresent()) { // �᎑���ɒ�����������,���o
				if (orderItem.getNumber() < 0) { // ��w���ɗʏ���0���݈�0
					orderItem.setNumber(0);
				}
				map.put(orderItem.getName(), orderItem.getNumber());
				total += orderItem.getNumber() * bookOp.get().getPrice();
				messageList.add("�i��: " + orderItem.getName() + " ,ISBN: " + bookOp.get().getISBN() + " ,���: "
						+ bookOp.get().getAuthor() + " ,�ɗ�: " + orderItem.getNumber() + " ,�`���z: " + total + " ,�ɑ����P: "
						+ (bookOp.get().getStock() - orderItem.getNumber()));

				book.setISBN(bookOp.get().getISBN());
				book.setAuthor(bookOp.get().getAuthor());
				book.setName(bookOp.get().getName());
				book.setPrice(bookOp.get().getPrice());
				book.setType(bookOp.get().getType());
				/* �X�V���S�z(Sales)�y�ɑ�(Stock) */
				book.setSales(bookOp.get().getSales() + orderItem.getNumber());
				book.setStock(bookOp.get().getStock() - orderItem.getNumber());
				bookDao.save(book);
				continue;
			} else {
				orderItem.setNumber(0); // �w�������s���������w���ɗʐ݈�0
				messageList.add("�����i���J�i��0");
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

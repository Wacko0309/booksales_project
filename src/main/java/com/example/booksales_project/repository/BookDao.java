package com.example.booksales_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.booksales_project.entity.Book;


@Repository
public interface BookDao extends JpaRepository<Book, String>{
	public void deleteByISBN(String ISBN);
	public List<Book> findAllByTypeContaining(String type);
	public List<Book> findAllByName(String name);
	public List<Book> findAllByAuthor(String author);
	public Optional<Book> findByName(String name);
	public List<Book> findTop5ByOrderBySalesDesc();
	public List<Book> findAllByISBNIn(List<String> isbnList);

}

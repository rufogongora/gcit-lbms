package com.gcit.lms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

@RestController
public class BookController {

	@Autowired
	BookDAO bookDAO;	

	@Autowired
	PublisherDAO publisherDAO;
	
	@Autowired
	GenreDAO genreDAO;
	
	@Autowired
	AuthorDAO authorDAO;
	
	@Transactional
	@RequestMapping(value="/book/add", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Book addBook(@RequestBody Book book){
		try {
			if (book.getAuthors() != null)
				{
					for (Author a : book.getAuthors())
					{
						a.setBooks(null);
					}
				}
			if (book.getGenres() != null)
			{
				for (Genre g : book.getGenres())
				{
					g.setBooks(null);
				}
			}
			if (book.getPublisher() != null)
			{
				book.getPublisher().setBooks(null);
			}
			bookDAO.addBook(book);
			publisherDAO.addBook(book);
			genreDAO.addBook(book);
			authorDAO.addBook(book);

			return book;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	

	
	@RequestMapping(value="/book/getOne", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json", produces="application/json")
	public Book getOneBook(@RequestBody Book book){
		try {
			return bookDAO.readOne(book.getBookId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}


	
	@Transactional
	@RequestMapping(value="/book/update", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Book updateBook(@RequestBody Book book){
		try {
			bookDAO.updateBook(book);
			genreDAO.removeBook(book);
			genreDAO.addBook(book);
			authorDAO.removeBook(book);
			authorDAO.addBook(book);
			publisherDAO.removeBook(book);
			publisherDAO.addBook(book);				
			return book;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	@Transactional
	@RequestMapping(value="/book/delete", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Book deleteBook(@RequestBody Book book){
		try {
			authorDAO.removeBook(book);
			genreDAO.removeBook(book);
			publisherDAO.removeBook(book);
			bookDAO.removeBook(book);
			
			return book;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/book/get/{pageNo}/{pageSize}/{field}", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Book> getAuthors(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String field){
		try {
			return bookDAO.search("", pageNo, pageSize, field);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/book/get/{pageNo}/{pageSize}/{field}/{query}", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Book> getAuthorsSearch(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String field, @PathVariable String query ){
		try {
			return bookDAO.search(query, pageNo, pageSize, field);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	@RequestMapping(value="/book/getCount/{field}/{query}", method={RequestMethod.GET} , produces="application/json")
	public long getCountQuery(@PathVariable String field, @PathVariable String query) throws JsonProcessingException, IOException{
		try {
			return	bookDAO.getCount(query, field);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@RequestMapping(value="/book/getCount/{field}", method={RequestMethod.GET} , produces="application/json")
	public long getCount(@PathVariable String field) throws JsonProcessingException, IOException{
		try {
			return	bookDAO.getCount("", field);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
}

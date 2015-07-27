package com.gcit.lms.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Publisher;
import com.mongodb.BasicDBObject;

public class BookDAO extends BaseDAO<Book> implements Serializable, ResultSetExtractor<List<Book>> {

	private static final long serialVersionUID = 1619700647002508164L;
	
	@Autowired
	PublisherDAO pDAO;
	
	private static final String BOOK_COLLECTION = "Books";
	
	public void addBook(Book bk) throws SQLException {
		bk.setBookId(getNextSequenceId(BOOK_COLLECTION));		
		mongoOps.insert(bk, BOOK_COLLECTION);
		Update update = new Update();
		
	}

	public void updateBook(Book book) throws SQLException {
		mongoOps.save(book, BOOK_COLLECTION);
	}

	public void removeBook(Book book) throws SQLException {
		mongoOps.remove(book, BOOK_COLLECTION);
	}

	public List<Book> readAll() throws SQLException {
		return mongoOps.findAll(Book.class, BOOK_COLLECTION);
	}

	public Book readOne(long bookId) throws SQLException {
		Query query = new Query(Criteria.where("_id").is(bookId));
		return mongoOps.findOne(query, Book.class, BOOK_COLLECTION);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		
		while (rs.next()) {
			Book b = new Book();
			//b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setPublisher(pDAO.readOne(rs.getInt("pubId")));
			books.add(b);
		}
		return books;
	}
	
	public List<Book> search(String searchQuery, int pageNo, int pageSize, String field) {
		if (pageSize <= 0)
			return mongoOps.findAll(Book.class, BOOK_COLLECTION);
		
		//search by author name
		if (field.equals("authorName") )
		{
			Query query = new Query(Criteria.where("authors.authorName").regex(Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
			return mongoOps.find(query, Book.class, BOOK_COLLECTION);
		}
		
		if (field.equals("publisher"))
		{
			Query query = new Query(Criteria.where("publisher.name").regex(Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
			return mongoOps.find(query, Book.class, BOOK_COLLECTION);
		}
		if (field.equals("genre"))
		{
			Query query = new Query(Criteria.where("genres.name").regex(Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
			return mongoOps.find(query, Book.class, BOOK_COLLECTION);
		}
		
		Query query = new Query(Criteria.where(field).regex(Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		if (pageNo <= 0)
			query.limit(pageSize);
		else
			query.skip(pageSize*(pageNo-1)).limit(pageSize);
		
		
		
		return this.mongoOps.find(query, Book.class, BOOK_COLLECTION);
	}
	public long getCount(String query, String field) throws SQLException{
		
		if (field.equals("authorName") )
		{
			Query q = new Query(Criteria.where("authors.authorName").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
			return mongoOps.count(q, Book.class, BOOK_COLLECTION);
		}
		if (field.equals("publisher") )
		{
			Query q = new Query(Criteria.where("publisher.name").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
			return mongoOps.count(q, Book.class, BOOK_COLLECTION);
		}
		if (field.equals("genre") )
		{
			Query q = new Query(Criteria.where("genres.name").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
			return mongoOps.count(q, Book.class, BOOK_COLLECTION);
		}
		
		Query q = new Query(Criteria.where(field).regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		return mongoOps.count(q, BOOK_COLLECTION);
	}

	public void removeAuthor(long authorId) {
		Update update = new Update();
		update.pull("authors", new BasicDBObject("_id", authorId));
		mongoOps.updateMulti(new Query(), update, BOOK_COLLECTION);
	}

	public void removeGenre(long genreId) {
		Update update = new Update();
		update.pull("genres", new BasicDBObject("_id", genreId));
		mongoOps.updateMulti(new Query(), update, BOOK_COLLECTION);
	}

	public void updateAuthor(Author author) {
		removeAuthor(author.getAuthorId());
		
		Update update = new Update();
		update.push("authors", author);
		mongoOps.updateMulti(new Query(), update, BOOK_COLLECTION);
	}

	
	public void updateGenre(Genre genre) {
		removeGenre(genre.getGenreId());
		
		Update update = new Update();
		update.push("genres", genre);
		mongoOps.updateMulti(new Query(), update, BOOK_COLLECTION);
	}

	public void removePublisher(Publisher publisher)
	{
		Update update = new Update().set("publisher", null);
		Query query = new Query(Criteria.where("publisher._id").is(publisher.getId()));
		mongoOps.updateMulti(query, update, BOOK_COLLECTION);
	}
	
	public void updatePublisher(Publisher publisher)
	{
		Update update = new Update().set("publisher", publisher);
		Query query = new Query(Criteria.where("publisher._id").is(publisher.getId()));
		mongoOps.updateMulti(query, update, BOOK_COLLECTION);
	}

}

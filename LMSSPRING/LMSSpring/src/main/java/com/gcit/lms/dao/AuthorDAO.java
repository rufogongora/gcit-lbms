package com.gcit.lms.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.mongodb.BasicDBObject;

public class AuthorDAO extends BaseDAO<Author> implements Serializable, ResultSetExtractor<List<Author>> {
	
	private static final long serialVersionUID = 1619700647002508164L;
	
	private static final String AUTH_COLLECTION = "Authors";

	public void addAuthor(Author author) throws SQLException {
		author.setAuthorId(getNextSequenceId(AUTH_COLLECTION));
		mongoOps.insert(author, AUTH_COLLECTION);
		
	}

	public void updateAuthor(Author author) throws SQLException {
		this.mongoOps.save(author, AUTH_COLLECTION);
	}

	public void removeAuthor(Author author) throws SQLException {
		
		
		mongoOps.remove(author, AUTH_COLLECTION);
		
	}

	

	public long getCount(String query) throws SQLException{
		Query q = new Query(Criteria.where("authorName").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		return mongoOps.count(q, AUTH_COLLECTION);
	}

	public Author readOne(long authorId) throws SQLException {
		Query query = new Query(Criteria.where("_id").is(authorId));
		return this.mongoOps.findOne(query, Author.class, AUTH_COLLECTION);

	}

	public List<Author> search(String authorName, int pageNo, int pageSize) throws SQLException
	{
		if (pageSize <= 0)
			return mongoOps.findAll(Author.class, AUTH_COLLECTION);
		
		Query query = new Query(Criteria.where("authorName").regex(Pattern.compile(authorName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		if (pageNo <= 0)
			query.limit(pageSize);
		else
			query.skip(pageSize*(pageNo-1)).limit(pageSize);
		return this.mongoOps.find(query, Author.class, AUTH_COLLECTION);
	}
	
	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<Author>();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}

	public void addBook(Book book) {
		for (Author a : book.getAuthors()){
		Update update = new Update().push("books", book);
		Query q = new Query(Criteria.where("_id").is(a.getAuthorId()));
		mongoOps.updateMulti(q, update, AUTH_COLLECTION);
		}
	}

	public void removeBook(Book book) {
		Update update = new Update();
		update.pull("books", new BasicDBObject("_id", book.getBookId()));
		mongoOps.updateMulti(new Query(), update, AUTH_COLLECTION);
	}
}



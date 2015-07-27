package com.gcit.lms.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.mongodb.BasicDBObject;

public class GenreDAO extends BaseDAO<Genre> implements Serializable,  ResultSetExtractor<List<Genre>> {
		
	private static final long serialVersionUID = 1619700647002508164L;
	
	private static final String GENRE_COLLECTION = "Genres";
	
	public void addGenre(Genre g) throws SQLException {
		g.setGenreId(getNextSequenceId(GENRE_COLLECTION));
		mongoOps.insert(g, GENRE_COLLECTION);
	}

	public void updateGenre(Genre g) throws SQLException {
		mongoOps.save(g, GENRE_COLLECTION);
	}

	public void removeGenre(Genre g) throws SQLException {
		mongoOps.remove(g, GENRE_COLLECTION);
	}

	public long getCount(String query) throws SQLException{
		Query q = new Query(Criteria.where("name").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		return mongoOps.count(q, GENRE_COLLECTION);
	}
	
	public List<Genre> readAll() throws SQLException {
		return mongoOps.findAll(Genre.class, GENRE_COLLECTION);
	}

	public Genre readOne(int genreId) throws SQLException {
		Query query = new Query(Criteria.where("_id").is(genreId));
		return mongoOps.findOne(query, Genre.class, GENRE_COLLECTION);
	}

	public Genre readOne(long genreId) throws SQLException {
		Query query = new Query(Criteria.where("_id").is(genreId));
		return this.mongoOps.findOne(query, Genre.class, GENRE_COLLECTION);

	}
	
	public List<Genre> search(String genreName, int pageNo, int pageSize) throws SQLException
	{
		if (pageSize <= 0)
			return mongoOps.findAll(Genre.class, GENRE_COLLECTION);
		
		Query query = new Query(Criteria.where("name").regex(Pattern.compile(genreName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		if (pageNo <= 0)
			query.limit(pageSize);
		else
			query.skip(pageSize*(pageNo-1)).limit(pageSize);
		return this.mongoOps.find(query, Genre.class, GENRE_COLLECTION);
	}
	
	@Override
	public List<Genre> extractData(ResultSet rs) {
		List<Genre> books = new ArrayList<Genre>();
		
/*		while (rs.next()) {
			Genre b = new Genre();
			//b.setGenreId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setPublisher(pDAO.readOne(rs.getInt("pubId")));
			books.add(b);
		}*/
		return books;
	}

	public void addBook(Book book) {
		for (Genre g : book.getGenres()){
		Update update = new Update().push("books", book);
		Query q = new Query(Criteria.where("_id").is(g.getGenreId()));
		mongoOps.updateMulti(q, update, GENRE_COLLECTION);
		}
		
	}

	public void removeBook(Book book) {
		// TODO Auto-generated method stub
		Update update = new Update();
		update.pull("books", new BasicDBObject("_id", book.getBookId()));
		mongoOps.updateMulti(new Query(), update, GENRE_COLLECTION);
	}
}

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
import com.gcit.lms.domain.Publisher;
import com.mongodb.BasicDBObject;

public class PublisherDAO extends BaseDAO<Publisher> implements Serializable, ResultSetExtractor<List<Publisher>> {


	private static final long serialVersionUID = 1619700647002508164L;
	
	private static final String PUB_COLLECTION = "Publishers";

	public void addPublisher(Publisher publisher) throws SQLException {
		publisher.setId(getNextSequenceId(PUB_COLLECTION));
		mongoOps.insert(publisher, PUB_COLLECTION);

	}

	public void updatePublisher(Publisher publisher) throws SQLException {
		mongoOps.save(publisher, PUB_COLLECTION);
	}

	public void removePublisher(Publisher publisher) throws SQLException {
		mongoOps.remove(publisher, PUB_COLLECTION);
	}

	public List<Publisher> readAll() throws SQLException {
		return mongoOps.findAll(Publisher.class, PUB_COLLECTION);
	}

	public Publisher readOne(int publisherId) throws SQLException {
		Query query = new Query(Criteria.where("_id").is(publisherId));
		return mongoOps.findOne(query, Publisher.class, PUB_COLLECTION);
	}

	

	public List<Publisher> search(String publisherName, int pageNo, int pageSize, String field) throws SQLException
	{
		if (pageSize <= 0 )
			return mongoOps.findAll(Publisher.class, PUB_COLLECTION);
		
		Query query = new Query(Criteria.where(field).regex(Pattern.compile(publisherName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		if (pageNo <= 0)
			query.limit(pageSize);
		else
			query.skip(pageSize*(pageNo-1)).limit(pageSize);
		return this.mongoOps.find(query, Publisher.class, PUB_COLLECTION);
	}
	
	

	public long getCount(String query, String field) throws SQLException{
		Query q = new Query(Criteria.where(field).regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		return mongoOps.count(q, PUB_COLLECTION);
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		while (rs.next()) {
			Publisher a = new Publisher();
			a.setId(rs.getInt("publisherId"));
			a.setName(rs.getString("publisherName"));

			publishers.add(a);
		}
		return publishers;
	}

	public void addBook(Book book) {
		Update update = new Update().push("books", book);
		Query q = new Query(Criteria.where("_id").is(book.getPublisher().getId()));
		mongoOps.updateMulti(q, update, PUB_COLLECTION);
	}

	public void removeBook(Book book) {
		Update update = new Update();
		update.pull("books", new BasicDBObject("_id", book.getBookId()));
		mongoOps.updateMulti(new Query(), update, PUB_COLLECTION);
	}
}

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

import com.gcit.lms.domain.BookLoan;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.mongodb.BasicDBObject;

public class BorrowerDAO extends BaseDAO<Borrower> implements Serializable, ResultSetExtractor<List<Borrower>> {
	
	private static final long serialVersionUID = 1619700647002508164L;
	
	private static final String AUTH_COLLECTION = "Borrowers";

	public void addBorrower(Borrower borrower) throws SQLException {
		mongoOps.insert(borrower, AUTH_COLLECTION);
		
	}

	public void updateBorrower(Borrower borrower) throws SQLException {
		this.mongoOps.save(borrower, AUTH_COLLECTION);
	}

	public void removeBorrower(Borrower borrower) throws SQLException {
	mongoOps.remove(borrower, AUTH_COLLECTION);
		
	}

	public long getCount(String query) throws SQLException{
		Query q = new Query(Criteria.where("borrowerName").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		return mongoOps.count(q, AUTH_COLLECTION);
	}

	public Borrower readOne(long cardNo) throws SQLException {
		Query query = new Query(Criteria.where("_id").is(cardNo));
		return this.mongoOps.findOne(query, Borrower.class, AUTH_COLLECTION);

	}

	public List<Borrower> search(String borrowerName, int pageNo, int pageSize) throws SQLException
	{
		if (pageSize <= 0)
			return mongoOps.findAll(Borrower.class, AUTH_COLLECTION);
		
		Query query = new Query(Criteria.where("borrowerName").regex(Pattern.compile(borrowerName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		if (pageNo <= 0)
			query.limit(pageSize);
		else
			query.skip(pageSize*(pageNo-1)).limit(pageSize);
		return this.mongoOps.find(query, Borrower.class, AUTH_COLLECTION);
	}
	
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<Borrower>();
		while (rs.next()) {
/*			Borrower a = new Borrower();
			a.setBorrowerId(rs.getInt("borrowerId"));
			a.setBorrowerName(rs.getString("borrowerName"));
			borrowers.add(a);*/
		}
		return borrowers;
	}

	public void checkOut(BookLoan bookLoan) {
		Query q = new Query(Criteria.where("_id").is(bookLoan.getBorrower().getCardNo()));
		Update u = new Update().push("bookLoans", bookLoan);
		mongoOps.updateFirst(q, u, AUTH_COLLECTION);
		
		
	}

}



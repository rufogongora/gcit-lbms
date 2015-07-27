package com.gcit.lms.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.ResultSetExtractor;







import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch> implements Serializable, ResultSetExtractor<List<LibraryBranch>>  {


	private static final long serialVersionUID = 1619700647002508164L;
	
	private static final String LIBRARY_COLLECTION = "LibraryBranches";
	
	public void addLibraryBranch(LibraryBranch lb) throws SQLException {
		lb.setBranchId(getNextSequenceId(LIBRARY_COLLECTION));
		mongoOps.insert(lb, LIBRARY_COLLECTION);
	}

	public void updateLibraryBranch(LibraryBranch lb) throws SQLException {
		for (BookCopies bc : lb.getBookCopies())
		{
			if (bc.getNoOfCopies() <= 0)
			{
				lb.setBookCopies(null);
				break;
			}
		}
		mongoOps.save(lb, LIBRARY_COLLECTION);
	}

	public void removeLibraryBranch(LibraryBranch lb) throws SQLException {
		mongoOps.remove(lb, LIBRARY_COLLECTION);
	}

	public List<LibraryBranch> readAll() throws SQLException {
		return mongoOps.findAll(LibraryBranch.class, LIBRARY_COLLECTION);
	}

	public LibraryBranch readOne(int branchId) throws SQLException {
		Query query = new Query(Criteria.where("_id").is(branchId));
		return mongoOps.findOne(query, LibraryBranch.class, LIBRARY_COLLECTION);
	}
	
	
	public List<LibraryBranch> search(String libraryBranchName, int pageNo, int pageSize, String field) throws SQLException
	{
		if (pageSize <= 0 )
			return mongoOps.findAll(LibraryBranch.class, LIBRARY_COLLECTION);
		
		Query query = new Query(Criteria.where(field).regex(Pattern.compile(libraryBranchName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		if (pageNo <= 0)
			query.limit(pageSize);
		else
			query.skip(pageSize*(pageNo-1)).limit(pageSize);
		return this.mongoOps.find(query, LibraryBranch.class, LIBRARY_COLLECTION);
	}
	
	public long getCount(String query, String field) throws SQLException{
		Query q = new Query(Criteria.where(field).regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		return mongoOps.count(q, LIBRARY_COLLECTION);
	}
	
	
	@Override
	public List<LibraryBranch> extractData(ResultSet arg0) throws SQLException,
			DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}

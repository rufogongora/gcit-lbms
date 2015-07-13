package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.LibraryBranch;

public class BookCopiesDAO extends BaseDAO {
	public BookCopiesDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(BookCopies bc) throws Exception {
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values(?,?,?)",
				new Object[] { bc.getBook().getBookId(), bc.getBranch().getBranchId(), bc.getNoOfCopies()});

	}

	public void update(BookCopies bc) throws Exception {
		save("update tbl_book_copies set noOfCopies = ? where branchId = ? AND bookId = ?",
				new Object[] { bc.getNoOfCopies(), bc.getBranch().getBranchId(), bc.getBook().getBookId() });

	}

	public void delete(BookCopies bc) throws Exception {
		save("delete from tbl_book_copies where branchId = ? AND bookId = ?",
				new Object[] { bc.getBranch().getBranchId(), bc.getBook().getBookId() });
	}

	public List<BookCopies> readAll() throws Exception{
		return (List<BookCopies>) read("select * from tbl_book_copies", null);
		
	}

	public BookCopies readOne(int branchId, int bookId) throws Exception {
		List<BookCopies> lbs = (List<BookCopies>) read("select * from tbl_book_copies WHERE branchId = ? AND bookId = ?", new Object[] {branchId, bookId});
		if(lbs!=null && lbs.size()>0){
			return lbs.get(0);
		}
		return null;
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws Exception {
		List<BookCopies> bcs =  new ArrayList<BookCopies>();

		while(rs.next()){
			BookCopies bc = new BookCopies();
			bc.setNoOfCopies(rs.getInt("noOfCopies"));
			BookDAO bDao = new BookDAO(getConnection());
			LibraryBranchDAO lbDao = new LibraryBranchDAO(getConnection());
			bc.setBook(bDao.readOne(rs.getInt("bookId")));
			bc.setBranch(lbDao.readOne(rs.getInt("branchId")));
			bcs.add(bc);
		}
		return bcs;
	}
	
	@Override
	public List<BookCopies> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<BookCopies> bcs =  new ArrayList<BookCopies>();		
		while(rs.next()){
			BookCopies bc = new BookCopies();
			bc.setNoOfCopies(rs.getInt("noOfCopies"));
			BookDAO bDao = new BookDAO(getConnection());
			LibraryBranchDAO lbDao = new LibraryBranchDAO(getConnection());
			List<Book> b = (List<Book>)bDao.readFirstLevel("select * from tbl_book where bookId = ? ", new Object[] {rs.getInt("bookId")});
			bc.setBook(b.get(0));
			@SuppressWarnings("unchecked")
			List<LibraryBranch> lb = (List<LibraryBranch>) lbDao.readFirstLevel("select * from tbl_library_branch WHERE branchId = ?", new Object[] {rs.getInt("branchId")});
			bc.setBranch(lb.get(0));
			bcs.add(bc);
		}
		return bcs;
	}
}

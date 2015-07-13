package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO{
	public LibraryBranchDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(LibraryBranch lb) throws Exception {
		int branchId = saveWithID("insert into tbl_library_branch (branchName, branchAddress) values(?,?)",
				new Object[] { lb.getBranchName(), lb.getBranchAddress()});
		lb.setBranchId(branchId);
	}

	public void update(LibraryBranch lb) throws Exception {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { lb.getBranchName(), lb.getBranchAddress(), lb.getBranchId() });

	}

	public void delete(LibraryBranch lb) throws Exception {
		save("delete from tbl_library_branch where branchId = ?",
				new Object[] { lb.getBranchId() });
	}

	public List<LibraryBranch> readAll() throws Exception{
		return (List<LibraryBranch>) read("select * from tbl_library_branch", null);
		
	}

	public LibraryBranch readOne(int lbId) throws Exception {
		List<LibraryBranch> lbs = (List<LibraryBranch>) read("select * from tbl_library_branch WHERE branchId = (?)", new Object[] {lbId});
		if(lbs!=null && lbs.size()>0){
			return lbs.get(0);
		}
		return null;
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws Exception {
		List<LibraryBranch> lbs =  new ArrayList<LibraryBranch>();

		while(rs.next()){
			LibraryBranch a = new LibraryBranch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("branchName"));
			a.setBranchAddress(rs.getString("branchAddress"));
			BookCopiesDAO bDao = new BookCopiesDAO(getConnection());
			@SuppressWarnings("unchecked")
			List<BookCopies> books = (List<BookCopies>) bDao.readFirstLevel("select * from tbl_book_copies where branchId = ? ", new Object[] {rs.getInt("branchId")});
			a.setBookCopies(books);
			lbs.add(a);
		}
		return lbs;
	}
	
	@Override
	public List<LibraryBranch> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<LibraryBranch> lbs =  new ArrayList<LibraryBranch>();		
		while(rs.next()){
			LibraryBranch a = new LibraryBranch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("branchName"));
			a.setBranchAddress(rs.getString("branchAddress"));
			lbs.add(a);
		}
		return lbs;
	}
}

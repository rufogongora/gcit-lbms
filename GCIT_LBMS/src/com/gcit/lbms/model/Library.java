package com.gcit.lbms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

public class Library {
	int branchId;
	String branchName;
	String branchAddress;
	ArrayList<Book> listOfBooks; 

	public Library(int id, String name, String address)
	{
		branchId = id;
		branchName = name;
		branchAddress = address;
	}


	public String getName()
	{
		return branchName;
	}	

	public int getBranchId()
	{
		return branchId;
	}


	public void sync(dbConnection conn)
	{
		try
		{
			String query = "UPDATE tbl_library_branch SET branchName = ?, branchId = ?, branchAddress = ? WHERE branchId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setString(1, branchName);
			pstmt.setInt(2, this.branchId);
			pstmt.setString(3, branchAddress);
			pstmt.setInt(4, branchId);
			conn.executeUpdate(pstmt);
		}
		catch (SQLException e){

		}
	}

	public void updateName(String name, dbConnection conn)
	{
		this.branchName = name;
		sync(conn);
	}

	public void updateAddress(String address, dbConnection conn)
	{
		this.branchAddress = address;
		sync(conn);
	}

	public static ArrayList<Library> getLibraries(dbConnection conn)
	{
		ArrayList<Library> list = new ArrayList<Library>();
		try {

			String Query = "SELECT * FROM tbl_library_branch";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(Query);
			ResultSet rs = conn.executeQuery(pstmt);

			while(rs.next())
			{
				Library l = new Library(rs.getInt("branchId"), rs.getString("branchName"), rs.getString("branchAddress"));
				list.add(l);
			}

		} 
		catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}


	public ArrayList<Book> getListOfBooksForThisLibrary(dbConnection conn){
		ArrayList<Book> bookList = new ArrayList<Book>();

		try{
			String query = "SELECT b.* FROM tbl_book_copies as c "
					+ "JOIN tbl_book as b ON (c.bookId = b.bookId) "
					+ "JOIN tbl_library_branch as l ON (l.branchId = c.branchId) WHERE l.branchId = ? AND c.noOfCopies > 0";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, this.branchId);
			ResultSet rs = conn.executeQuery(pstmt);
			while (rs.next())
			{
				Book b = new Book(rs.getInt("bookId"), rs.getString("title"), rs.getInt("pubId"));
				bookList.add(b);
			}
		}
		catch(SQLException e)
		{

		}
		this.listOfBooks = bookList;
		return this.listOfBooks;
	}
	
	public boolean checkForLoan(Book b, User u, dbConnection conn)
	{
		try
		{
			String query = "SELECT * FROM tbl_libray_loans WHERE cardNo = ? AND bookId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, u.getCardNo());
			pstmt.setInt(2, b.getBookId());
			ResultSet rs = conn.executeQuery(pstmt);
			if (rs.next())
				return true;
		}
		catch (SQLException e)
		{
			
		}
		return false;
	}

	public int getNumberOfCopiesForBook(int id, dbConnection conn)
	{
		try{

			String query = "SELECT noOfCopies FROM tbl_book_copies WHERE bookId = ? AND branchId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, this.branchId);
			ResultSet rs = conn.executeQuery(pstmt);

			while(rs.next())
			{
				return rs.getInt("noOfCopies");
			}
			return 0;

		}catch(Exception e)
		{

		}
		return 0;
	}
	
	public ArrayList<Book> getListOfBooks(){
		return this.listOfBooks;
	}
	
	public void checkOutBook(int cardNo, Book b, dbConnection conn)
	{
		try{
		String query = "INSERT INTO tbl_book_loans VALUES (?, ?, ?, CURDATE(), date_add(CURDATE(), INTERVAL 7 DAY), NULL)";
		PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
		pstmt.setInt(1, b.getBookId());
		pstmt.setInt(2, this.branchId);
		pstmt.setInt(3, cardNo);
		conn.executeUpdate(pstmt);
		
		int n = getNumberOfCopiesForBook(b.getBookId(), conn);
		updateNumberOfCopies(b, n -1, conn);
		}
		catch(SQLException e)
		{
			
		}
		
	}
	public void updateNumberOfCopies(Book b, int n, dbConnection conn)
	{
		try{
			if (getNumberOfCopiesForBook(b.getBookId(), conn) > 0 )
			{
				//UPDATE
				String query = "UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?";
				PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
				pstmt.setInt(1, n);
				pstmt.setInt(2, b.getBookId());
				pstmt.setInt(3, this.branchId);
				conn.executeUpdate(pstmt);

			}
			else
			{
				//FIRST delete every row that has 0 copies on it, since we don't want duplicates
				String query  = "DELETE FROM tbl_book_copies WHERE noOfCopies = 0";
				PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
				conn.executeUpdate(pstmt);

				//otherwise: insert
				query = "INSERT INTO tbl_book_copies VALUES (?, ?, ?)";
				pstmt = conn.getConnection().prepareStatement(query);
				pstmt.setInt(1 ,b.getBookId());
				pstmt.setInt(2, this.branchId);
				pstmt.setInt(3, n);
				conn.executeUpdate(pstmt);
			}
		}catch(Exception e)
		{

		}
	}
	
	public ArrayList<Book> getListOfOwedBooks(int cardNo, dbConnection conn)
	{
		ArrayList<Book> list = new ArrayList<Book>();
		try{
			String query = "SELECT * FROM tbl_book as b JOIN tbl_book_loans as l ON (b.bookId=l.bookId) WHERE l.cardNo = ? AND l.branchId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, cardNo);
			pstmt.setInt(2, this.branchId);
			ResultSet rs = conn.executeQuery(pstmt);
			while (rs.next())
			{
				Book b = new Book(rs.getInt("bookId"), rs.getString("title"), rs.getInt("pubId"));
				list.add(b);
			}
			
			return list;
			
		}
		catch(SQLException c)
		{
			
		}
		return list;
	}
	
	public void returnBook(Book b, int cardNo, dbConnection conn)
	{
		try{
			String query = "UPDATE tbl_book_copies SET noOfCopies = noOfCopies + 1 WHERE bookId = ? AND branchId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, b.getBookId());
			pstmt.setInt(2, this.branchId);
			conn.executeUpdate(pstmt);
			
			query = "DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?";
			pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, b.getBookId());
			pstmt.setInt(2, this.branchId);
			pstmt.setInt(3, cardNo);
			conn.executeUpdate(pstmt);
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

}

package com.gcit.lbms.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Administrator extends Librarian {

	public Administrator()
	{

	}

	@Override public void addBook(String title, int publisherId , dbConnection conn)
	{
		try{
			String query = "INSERT INTO tbl_book (title, pubId) VALUES (?, ?)";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setInt(2, publisherId);
			conn.executeUpdate(pstmt);
		}
		catch (SQLException e)
		{

		}
	}

	@Override
	public void updateBook(int bookId, String title, int id, dbConnection conn) {
		try{
			String query = "UPDATE tbl_book SET title = ?, pubId = ? WHERE bookId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setInt(2, id);
			pstmt.setInt(3, bookId);
			conn.executeUpdate(pstmt);
		}
		catch (SQLException e)
		{

		}
	}

	@Override
	public void deleteBook(int bookId, dbConnection conn) {
		try{
			//first delete from book_author link table
			String query =  "DELETE FROM tbl_book_author WHERE bookId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, bookId);
			conn.executeUpdate(pstmt);
			
			//then delete the book genre
			query =  "DELETE FROM tbl_book_genre WHERE bookId = ?";
			pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, bookId);
			conn.executeUpdate(pstmt);
			
			//then just delete the book
			query = "DELETE FROM tbl_book WHERE bookId = ?";
			pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, bookId);
			conn.executeUpdate(pstmt);
		}
		catch (SQLException e)
		{

		}
	}
	
	@Override
	public void addPublisher(String name, String address, String phone, dbConnection conn) {
		try{
			String query = "INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES (?,?,?)";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, phone);
			conn.executeUpdate(pstmt);
		}catch (SQLException e)
		{
			
		}
	}
	@Override
	public void updatePublisher(int id, String name, String address, String phone, dbConnection conn) {
		try{
			String query = "UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ?, publisherPhone = ? WHERE publisherId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, phone);
			pstmt.setInt(4, id);
			conn.executeUpdate(pstmt);
		}
		catch (SQLException e)
		{

		}
	}
	@Override
	public void deletePublisher(int id, dbConnection conn) {
		// TODO Auto-generated method stub
		try{
			
			
		//first delete the book copies
		String query = "DELETE FROM tbl_book_copies WHERE (bookId = (SELECT b.bookId FROM tbl_book as b JOIN tbl_publisher as p ON (b.pubId = p.publisherId) WHERE p.publisherId = ? group by b.bookId ))";
		PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
		pstmt.setInt(1, id);
		conn.executeUpdate(pstmt);
		
		//then delete the book genres
		query = "DELETE FROM tbl_book_genres WHERE (bookId = (SELECT b.bookId FROM tbl_book as b JOIN tbl_publisher as p ON (b.pubId = p.publisherId) WHERE p.publisherId = ? group by b.bookId ))";
		pstmt = conn.getConnection().prepareStatement(query);
		pstmt.setInt(1, id);
		conn.executeUpdate(pstmt);
		
		//then delete the book_author table stuff
		query = "DELETE FROM tbl_book_author WHERE (bookId = (SELECT b.bookId FROM tbl_book as b JOIN tbl_publisher as p ON (b.pubId = p.publisherId) WHERE p.publisherId = ? group by b.bookId ))";
		pstmt = conn.getConnection().prepareStatement(query);
		pstmt.setInt(1, id);
		conn.executeUpdate(pstmt);
			
		
		//then delete the books
		query = "DELETE FROM tbl_book WHERE (pubId = ?)";
		pstmt = conn.getConnection().prepareStatement(query);
		pstmt.setInt(1, id);
		conn.executeUpdate(pstmt);
		//then finally delete the publisher
		query = "DELETE FROM tbl_publisher WHERE (publisherId = ?)";
		pstmt = conn.getConnection().prepareStatement(query);
		pstmt.setInt(1, id);
		conn.executeUpdate(pstmt);
		}
		catch (SQLException e)
		{
			
		}
	}
	
	@Override
	public void addLibraryBranch(String name, String address, dbConnection conn)
	{
		try{
			String query = "INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?,?)";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			conn.executeUpdate(pstmt);
		}catch (SQLException e )
		{
			
		}
	}
	
	@Override
	public void updateLibraryBranch(String name, String address, int id , dbConnection conn)
	{
		try{
			String query = "UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setInt(3, id);
			conn.executeUpdate(pstmt);
		}
		catch (SQLException e)
		{

		}
	}
	
	@Override
	public void deleteLibraryBranch(int id, dbConnection conn)
	{
		try{
			String query = "DELETE FROM tbl_book_copies WHERE branchId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, id);
			conn.executeUpdate(pstmt);

			query = "DELETE FROM tbl_book_loans WHERE branchId = ?";
			pstmt.setInt(1, id);
			conn.executeUpdate(pstmt);
			
			query = "DELETE FROM tbl_library_branch WHERE branchId = ?";
			pstmt.setInt(1, id);
			conn.executeUpdate(pstmt);
			
		}
		catch (SQLException e)
		{
			
		}
	} 
	
	@Override
	public void addBorrower(String name, String address, String phone, dbConnection conn)
	{
		try{
			String query = "INSERT INTO tbl_borrower (name, address, phone) VALUES (?,?,?)";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, phone);
			conn.executeUpdate(pstmt);
		}catch (SQLException e )
		{
			
		}
	}
	
	@Override
	public void updateBorrower(String name, String address, String phone, int cardNo, dbConnection conn)
	{
		try{
			String query = "UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, phone);
			pstmt.setInt(4, cardNo);
			conn.executeUpdate(pstmt);
		}
		catch (SQLException e)
		{

		}
	}
	
	@Override
	public void deleteBorrower(int id, dbConnection conn)
	{
		try{
			String query = "DELETE FROM tbl_book_loans WHERE cardNo = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, id);
			conn.executeUpdate(pstmt);
			
			query = "DELETE FROM tbl_borrower WHERE cardNo = ?";
			pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, id);
			conn.executeUpdate(pstmt);
			
		}catch (SQLException e)
		{
			
		}
	}
	
	@Override
	public void overrideDueDate(int cardNo, int bookId, int branchId, Date newDate, dbConnection conn)
	{
		try{
			String query = "UPDATE tbl_book_loans SET (dueDate = ?) WHERE bookId = ? AND cardNo = ? AND branchId = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setDate(1, newDate);
			pstmt.setInt(2, bookId);
			pstmt.setInt(3, cardNo);
			pstmt.setInt(4, branchId);
	
			conn.executeUpdate(pstmt);
		}
		catch (SQLException e)
		{
			
		}
	}
	 
}

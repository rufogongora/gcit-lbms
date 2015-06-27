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
	
	
	public int getNumberOfCopiesForBook(int id, dbConnection conn)
	{
		try{
			/*String query = "SELECT c.noOfCopies FROM tbl_book_copies as c "
					+ "JOIN tbl_book as b ON (c.bookId = b.bookId) "
					+ "JOIN tbl_library_branch as l ON (l.branchId = c.branchId) WHERE c.bookId = ? AND l.branchId = ?";
			*/
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
	
}

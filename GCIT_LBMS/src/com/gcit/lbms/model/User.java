package com.gcit.lbms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class User {
	
	protected int level;
	private int currentLibraryId;
	private static dbConnection conn;
	Library selectedLibrary;
	Book selectedBook;

	public void setCurrentLibrary(int i)
	{
		currentLibraryId = i;
	}
	public int getCurrentLibrary(){
		return currentLibraryId;
	}
	
	public static Borrower getBorrower(int cardId)
	{
		try {
			String query = "SELECT * FROM tbl_borrower WHERE id = ?";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			pstmt.setInt(1, cardId);
			ResultSet rs = conn.executeQuery(pstmt);
			//THIS MEANS THERE'S NO RESULT
			if (!rs.next())
			{
				return null;
			}else
			//ELSE RETURN THE OBJECT
			{
				Borrower b = new Borrower(rs.getString("name"), rs.getInt("cardId"), rs.getString("address"), rs.getString("phone"));
				return b;
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public Library getSelectedLibrary()
	{
		return selectedLibrary;
	}
	
	public void setSelectedBook(Book b)
	{
		this.selectedBook = b;
	}
	
	public Book getSelectedBook()
	{
		return this.selectedBook;
	}
	
	public void setSelectedLibrary(Library l)
	{
		selectedLibrary = l;
	}
	
	 
}

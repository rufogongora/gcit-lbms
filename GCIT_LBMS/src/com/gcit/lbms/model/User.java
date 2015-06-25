package com.gcit.lbms.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class User {
	
	protected int level;
	private int currentLibraryId;
	private static dbConnection conn;
	
	public void setLibrary(int id)
	{
		try{
		String query = "SELECT id FROM tbl_library_branch WHERE id = " +id;
		ResultSet rs = conn.executeQuery(query);
		level = rs.getInt("branchId");
		}
		catch(SQLException e) 
		{
			conn.debugError(e);
		}
	}
	
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
			String query = "SELECT * FROM tbl_borrower WHERE id = " + cardId ;
			ResultSet rs = conn.executeQuery(query);
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
	
	 
}

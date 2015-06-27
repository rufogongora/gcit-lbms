package com.gcit.lbms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Book {
	
	int bookId;
	String title;
	int pubId;
	
	public Book(int id, String title, int pubId)
	{
		this.bookId = id;
		this.title = title;
		this.pubId = pubId;
	}
	
	public String getName(){
		return title;
	}
	
	public int getBookId()
	{
		return bookId;
	}
	
	public static ArrayList<Book> listOfBooks(dbConnection conn)
	{
		ArrayList<Book> list = new ArrayList<Book>();
		try {

			String Query = "SELECT * FROM tbl_book";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(Query);
			ResultSet rs = conn.executeQuery(pstmt);
			
			while(rs.next())
			{
				Book b = new Book(rs.getInt("bookId"), rs.getString("title"), rs.getInt("pubId"));
				list.add(b);
			}

		} 
		catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
}

package com.gcit.lbms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Author {
	int authorId;
	String authorName;
	
	public Author(int id,String name)
	{
		authorId = id;
		authorName = name;
	}
	
	public int getId()
	{
		return authorId;
	}
	public String getName()
	{
		return authorName;
	}
	
	public static ArrayList<Author> getListOfAuthors(dbConnection conn)
	{
		ArrayList<Author> list = new ArrayList<Author>();
		try
		{
			String query = "SELECT * FROM tbl_author";
			PreparedStatement psmt = conn.getConnection().prepareStatement(query);
			ResultSet rs = conn.executeQuery(psmt);
			while (rs.next())
			{
				Author b = new Author(rs.getInt("authorId"), rs.getString("authorName"));
				list.add(b);
			}
			
		}
		catch (SQLException e)
		{
			
		}
		return list;
		
	}
	
}

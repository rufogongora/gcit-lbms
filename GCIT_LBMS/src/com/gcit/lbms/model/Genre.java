package com.gcit.lbms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Genre {
	
	int genreId;
	String genre_name;
	
	public Genre(int genreId, String genre_name)
	{
		this.genreId = genreId;
		this.genre_name = genre_name;
	}
	
	public int getGenreId() {
		return genreId;
	}


	public String getGenre_name() {
		return genre_name;
	}
	
	
	public static ArrayList<Genre> getListOfGenre(dbConnection conn)
	{
		ArrayList<Genre> list = new ArrayList<Genre>();
	
		try
		{
			String query = "SELECT * FROM tbl_genre";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			ResultSet rs = conn.executeQuery(pstmt);
			
			while (rs.next())
			{
				Genre g = new Genre(rs.getInt(1), rs.getString(2));
				list.add(g);
			}
		}
		catch (SQLException e)
		{
			
		}
		
		return list;
	}



	
}

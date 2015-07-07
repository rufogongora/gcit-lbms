package com.gcit.lbms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Publisher {
	int publisherId;
	String publisherName;
	String publisherAddress;
	String publisherPhone;
	
	public Publisher(int id, String name, String address, String phone)
	{
		publisherId = id;
		publisherName = name;
		publisherAddress = address;
		publisherPhone = phone;
	}
	
	
	public String getName()
	{
		return publisherName;
	}
	
	public int getId()
	{
		return publisherId;
	}
	
	public static ArrayList<Publisher> getPublisherList(dbConnection conn)
	{
		ArrayList<Publisher> list = new ArrayList<Publisher>();
		try{
			String query = "SELECT * FROM tbl_publisher";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			ResultSet rs = conn.executeQuery(pstmt);

			while (rs.next())
			{
				Publisher p = new Publisher(rs.getInt("publisherId"), rs.getString("publisherName"), rs.getString("publisherAddress"), rs.getString("publisherPhone"));
				list.add(p);
			}
			
			return list;
			
		}catch (SQLException e)
		{
			
		}
		return list;
	}
	
}

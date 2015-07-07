package com.gcit.lbms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Borrower extends User {

	private String name;
	private int cardNo;
	private String address;
	private String phone;
	
	public Borrower(String name, int cardNo, String address, String phone)
	{
		this.name = name;
		this.cardNo = cardNo;
		this.address = address;
		this.phone = phone;
		level= 1;
	}
	
	public String getName()
	{
		return name;
	}

	public static Borrower findBorrower(int cardNo, dbConnection conn)
	{
		try{
			
		
		String query = "SELECT * FROM tbl_borrower WHERE cardNo = ?";
		PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
		pstmt.setInt(1, cardNo);
		ResultSet rs = conn.executeQuery(pstmt);
		
		if (rs.next())
		{
			Borrower b = new Borrower(rs.getString("name"), rs.getInt("cardNo"), rs.getString("address"), rs.getString("phone"));
			return b;
		}
		
		}catch (SQLException e)
		{
			
		}
		
		return null;
	}
	
	
	public static ArrayList<Borrower> getListOfBorrowers(dbConnection conn)
	{
		ArrayList<Borrower> list = new ArrayList<Borrower>();
		try{
			String query = "SELECT * FROM tbl_borrower";
			PreparedStatement pstmt = conn.getConnection().prepareStatement(query);
			ResultSet rs = conn.executeQuery(pstmt);
			
			while (rs.next())
			{
				Borrower b = new Borrower(rs.getString("name"), rs.getInt("cardNo"), rs.getString("address"), rs.getString("phone"));
				list.add(b);
			}
			return list;
		}catch (SQLException e)
		{
			
		}
		return list;
	}
	
	@Override
	public int getCardNo()
	{
		return cardNo;
	}
}

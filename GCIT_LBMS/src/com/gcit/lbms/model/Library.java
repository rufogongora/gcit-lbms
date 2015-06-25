package com.gcit.lbms.model;

import java.sql.ResultSet;
import java.util.ArrayList;

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
	
	public static ArrayList<Library> getLibraries(dbConnection conn)
	{
		ArrayList<Library> list = new ArrayList<Library>();
		try {

			String Query = "SELECT * FROM tbl_library_branch";
			ResultSet rs = conn.executeQuery(Query);
			
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
	
}

package com.gcit.lbms.model;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Menu {

	private dbConnection conn;
	private ArrayList<String> libraryList; 
	
	public Menu(dbConnection c)
	{
		conn = c;
		ArrayList<String> list = new ArrayList<String>();
		try {

			String Query = "SELECT branchName FROM tbl_library_branch";
			ResultSet rs = conn.executeQuery(Query);
			
			while(rs.next())
			{
				list.add(rs.getString("branchName"));
			}

		} 
		catch (Exception e) {
			// TODO: handle exception
		}
		libraryList = list;
	}

	public ArrayList<String> getLibraryList() 
	{

		return libraryList;

	}

}

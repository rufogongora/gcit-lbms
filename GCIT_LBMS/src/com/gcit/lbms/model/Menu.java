package com.gcit.lbms.model;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Menu {

	private dbConnection conn;
	private ArrayList<Library> libraryList; 
	
	public Menu(dbConnection c)
	{
		conn = c;
		updateLibraryList();
	}

	
	private void updateLibraryList()
	{
		libraryList = Library.getLibraries(conn);
	}
	
	public ArrayList<Library> getLibraryList() 
	{

		return libraryList;

	}

}

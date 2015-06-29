package com.gcit.lbms.model;


import java.util.ArrayList;

public class Menu {

	private dbConnection conn;
	private ArrayList<Library> libraryList; 
	private ArrayList<Book> listOfBooks;
	
	public Menu(dbConnection c)
	{
		conn = c;
		updateLibraryList();
		listOfBooks = Book.listOfBooks(conn);
	}

	
	private void updateLibraryList()
	{
		libraryList = Library.getLibraries(conn);
	}
	
	public dbConnection getConnection()
	{
		return conn;
	}
	
	
	public ArrayList<Book> getBookList()
	{
		return listOfBooks;
	}
	public ArrayList<Library> getLibraryList() 
	{
		return libraryList;

	}
	
	

}

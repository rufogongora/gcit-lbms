package com.gcit.lbms.model;

import java.sql.Date;
public abstract class User {

	protected int level;
	private int currentLibraryId;
	Library selectedLibrary;
	Book selectedBook;

	public void setCurrentLibrary(int i)
	{
		currentLibraryId = i;
	}
	public int getCurrentLibrary(){
		return currentLibraryId;
	}

	public int getCardNo()
	{
		return 0;
	}

	public Library getSelectedLibrary()
	{
		return selectedLibrary;
	}

	public void setSelectedBook(Book b)
	{
		this.selectedBook = b;
	}

	public Book getSelectedBook()
	{
		return this.selectedBook;
	}

	public void setSelectedLibrary(Library l)
	{
		selectedLibrary = l;
	}

	public void addBook(String title, int publisherId, dbConnection conn)
	{

	}
	public void updateBook(int bookId, String title, int id, dbConnection connection) {

	}
	public void deleteBook(int bookId, dbConnection conn) {
		// TODO Auto-generated method stub

	}
	public void addPublisher(String name, String address, String phone, dbConnection conn) {
		// TODO Auto-generated method stub

	}
	public void updatePublisher(int id, String name, String address, String phone, dbConnection conn) {
		// TODO Auto-generated method stub

	}
	public void deletePublisher(int id, dbConnection conn) {
		// TODO Auto-generated method stub

	}
	public void addLibraryBranch(String name, String address, dbConnection conn)
	{

	}
	public void updateLibraryBranch(String name, String address, int id , dbConnection conn)
	{


	}
	public void deleteLibraryBranch(int id, dbConnection conn)
	{
	}
	public void addBorrower(String name, String address, String phone, dbConnection conn)
	{
	}

	public void updateBorrower(String name, String address, String phone, int cardNo, dbConnection conn)
	{
		
	}
	public void deleteBorrower(int id, dbConnection conn)
	{
		
	}
	public void overrideDueDate(int cardNo, int bookId, int branchId, Date newDate, dbConnection conn)
	{
		
	}
}

package com.gcit.lbms.views;

import java.util.ArrayList;
import java.util.Iterator;

import com.gcit.lbms.model.Book;
import com.gcit.lbms.model.Borrower;
import com.gcit.lbms.model.Library;
import com.gcit.lbms.model.Publisher;

public class MenuView {

	public void printLibraries(ArrayList<Library> list)
	{
		int i=1;
		for (Library l : list)
		{
			System.out.println(i+") " + l.getName());
			i++;
		}
		System.out.println(i+") Quit to previous" );
	}
	
	
	public void printPublisherList(ArrayList<Publisher> list)
	{
		int i=1;
		for (Publisher l : list)
		{
			System.out.println(i+") " + l.getName());
			i++;
		}
		System.out.println(i+") Quit to previous" );	
	}

	public void printListOfBooks(ArrayList<Book> list)
	{

		int i =1;
		for (Book b : list)
		{
			System.out.println(i+")" + b.getName());
			i++;
		}
		System.out.println(i+") Quit to previous" );
	}
	
	
	public void printBorrowers(ArrayList<Borrower> list)
	{
		int i =1;
		for (Borrower b : list)
		{
			System.out.println(i+")" + b.getName());
			i++;
		}
		System.out.println(i+") Quit to previous" );
		
	}
	public void pickBookForBranch(ArrayList<Book> list)
	{
		System.out.println("Pick the Book you want to add copies of, to your branch: ");
		printListOfBooks(list);
	}
	
	public void pickBookForCheckout(ArrayList<Book> list)
	{
		System.out.println("Pick the Book you want to check out");
		printListOfBooks(list);
	}
	public void firstMenu()
	{
		System.out.println("Welcome to the GCIT Library Management System. Which category of a user are you");
		System.out.println("1) Librarian");
		System.out.println("2) Administrator");
		System.out.println("3) Borrower");
	}
	
	public void enterBranchYouManage()
	{
		System.out.println("Select by inputting a number");
		System.out.println("1) Enter the branch you manage");
		System.out.println("2) Quit to previous");
	}
	
	public void libraryMenu()
	{
		System.out.println("1) Update the details of the library");
		System.out.println("2) Add copies of Book to the branch");
		System.out.println("3) Return to previous");
	}
	
	
	public void updateNumberOfCopiesForBook(int n)
	{
		System.out.println("Exsiting number of copies: " + n);
		System.out.println("Enter new number of copies: ");
	}
	public void updateDetailsScreen1()
	{
		System.out.println("Please enter new branch name or enter N/A for no change:");
	}
	public void updateDetailsScreen2()
	{
		System.out.println("Please enter new branch address or enter N/A for no change:");
	}
	
	public void enterCardNumber()
	{
		System.out.println("Enter the your Card Number: ");
	}
	
	public void borr1()
	{
		System.out.println("1) Check out a book");
		System.out.println("2) Return a bok");
		System.out.println("3) Quit to previous");
	}
	public void checkOutABook(ArrayList<Library> list)
	{
		System.out.println("Pick the Branch you want to check out from:");
		printLibraries(list);
	}
	public void returnABook(ArrayList<Library> list)
	{
		System.out.println("Pick the Branch in which you want to return the book to:");
		printLibraries(list);
	}
	public void selectBookToReturn(ArrayList<Book> list)
	{
		System.out.println("These are the book you've checked out, select the one you want to return");
		printListOfBooks(list);
	}
	public void adminMenu(){
		System.out.println("1) Add/Update/Delete Book and Author ");
		System.out.println("2) Add/Update/Delete Publishers ");
		System.out.println("3) Add/Update/Delete Library Branches ");
		System.out.println("4) Add/Update/Delete Borrowers");
		System.out.println("5) Over-ride Due Date for a Book Loan");
		System.out.println("6) Return to previous");
	}
	public void AUDBookAuthor()
	{
		System.out.println("1) Add a Book");
		System.out.println("2) Update a Book");
		System.out.println("3) Delete a Book");
		System.out.println("4) Return to previous");
	}
	
	public void AUDPublishers()
	{
		System.out.println("1) Add a Publisher");
		System.out.println("2) Update a Publisher");
		System.out.println("3) Delete a Publisher");
		System.out.println("4) Return to previous");
	}
	
	public void AUDLibraries()
	{
		System.out.println("1) Add a Library");
		System.out.println("2) Update a Library");
		System.out.println("3) Delete a Library");
		System.out.println("4) Return to previous");
	}
	public void AUDBorrowers()
	{
		System.out.println("1) Add a Borrower");
		System.out.println("2) Update a Borrower");
		System.out.println("3) Delete a Borrower");
		System.out.println("4) Return to previous");
	}
	
	public void addBook1()
	{
		System.out.println("Please enter the title: ");
	}
	public void updateBook(ArrayList<Book> listOfBooks)
	{
		System.out.println("Please select which book to update: ");
		printListOfBooks(listOfBooks);
	}


	public void deleteBook(ArrayList<Book> listOfBooks) {
		System.out.println("Please select the book you want to delete: ");
		printListOfBooks(listOfBooks);
		
	}
	
	public void addPublisher()
	{
		System.out.println("Please type the name of the new publisher: ");
	}


	public void updatePublisher(ArrayList<Publisher> publisherList) {
		System.out.println("Select the publisher you want to update: ");
		printPublisherList(publisherList);
		
	}

	public void deletePublisher(ArrayList<Publisher> publisherList) {
		System.out.println("Select which publisher you want to delete: ");
		printPublisherList(publisherList);
		
	}
	
	public void addLibraryBranch()
	{
		System.out.println("Please type the name of the new branch");
	}
	public void updateLibraryBranch(ArrayList<Library> list)
	{
		System.out.println("Please select the library branch you want to update: ");
		printLibraries(list);
		
	}


	public void deleteLibrary(ArrayList<Library> list) {
		// TODO Auto-generated method stub
		System.out.println("Select which library you want to delete: ");
		printLibraries(list);
		
	}


	public void addBorrower() {
		// TODO Auto-generated method stub
		System.out.println("Please input the name of the borrower: ");
		
	}


	public void updateBorrower(ArrayList<Borrower> listOfBorrowers) {
		// TODO Auto-generated method stub
		System.out.println("Please select the borrower you want to update: ");
		printBorrowers(listOfBorrowers);
		
	}
}

package com.gcit.lbms.controller;


import java.util.ArrayList;
import java.util.Scanner;

import com.gcit.lbms.model.Administrator;
import com.gcit.lbms.model.Author;
import com.gcit.lbms.model.Book;
import com.gcit.lbms.model.Bookloan;
import com.gcit.lbms.model.Borrower;
import com.gcit.lbms.model.Genre;
import com.gcit.lbms.model.Librarian;
import com.gcit.lbms.model.Library;
import com.gcit.lbms.model.Menu;
import com.gcit.lbms.model.Publisher;
import com.gcit.lbms.model.User;
import com.gcit.lbms.views.MenuView;

public class MenuController {

	private MenuView view;
	private Menu model;
	private User user;
	int screen;
	int subscreen;

	public MenuController(Menu m, MenuView v)
	{
		model = m;
		view = v;
		screen = 0;
		subscreen = 0;
	}

	public void setUser(User u)
	{
		user = u;
	}

	public void selectUser(String c)
	{
		switch (c)
		{
		//they picked Librarian
		case "1":
			Librarian lib = new Librarian();
			user = lib;
			screen = 1;
			break;
		case "2":
			Administrator admin = new Administrator();
			user = admin;		
			screen = 13;
			break;
		case "3":
			screen = 7;
			break;
		default:
			break;
		}
	}

	public void screen1(String c)
	{
		switch (c) {
		case "1":
			screen += 1;
			break;
		case "2":
			screen -= 1;
			break;
		default:
			break;
		}
	}

	public void screen2(String c)
	{
		int n = Integer.parseInt(c);
		n-=1;
		if (n < model.getLibraryList().size() &&  n >= 0)
		{

			System.out.println("Selected: " + model.getLibraryList().get(n).getName());
			user.setSelectedLibrary(model.getLibraryList().get(n));
			screen += 1;
		}
		else
			//quit to previous
			screen -= 1;

	}

	public void screen3(String c)
	{
		switch (c)
		{
		case "1":
			//update the details
			screen += 1;
			break;
		case "2":
			//Add copies
			screen += 2;
			break;
		default:
			screen = 2;
			break;
		}
	}



	public void updateLibMenu()
	{
		switch (subscreen)
		{
		case 0:
			view.updateDetailsScreen1();
			break;
		case 1:
			view.updateDetailsScreen2();
			break;
		}
	}

	public void updateDetails(String s)
	{
		switch (subscreen) {
		case 0:
			if (!s.equalsIgnoreCase("N/A"))
			{
				//TODO: UPDATE NEW BRANCH NAME
				user.getSelectedLibrary().updateName(s,model.getConnection());				
			}
			subscreen+=1;
			break;
		case 1:
			if (!s.equalsIgnoreCase("N/A"))
			{
				//TODO: UPDATE NEW BRANCH ADDRESS
				user.getSelectedLibrary().updateAddress(s, model.getConnection());
			}
			subscreen = 0;
			screen -= 1;
			break;
		default:
			break;
		}

	}

	public void screen5(String c)
	{
		int n = Integer.parseInt(c);
		n-=1;
		if (n < model.getBookList().size() &&  n >= 0)
		{
			user.setSelectedBook(model.getBookList().get(n));
			screen+=1;
		}
		else
			screen = 3;

	}

	public void screen6(String c)
	{
		int n = Integer.parseInt(c);
		user.getSelectedLibrary().updateNumberOfCopies(user.getSelectedBook(), n, model.getConnection());
		screen = 3	;

	}

	public void screen7(String c)
	{
		int n = Integer.parseInt(c);
		Borrower b = Borrower.findBorrower(n, model.getConnection());
		if (b != null)
		{
			user = b;
			screen = 8;
			
		}
	}
	
	public void screen8 (String c)
	{
		switch (c)
		{
		case "1":
			screen = 9;
			break;
		case "2":
			screen = 11;
			break;
		default:
			screen = 0;
			break;
		}
	}
	public void screen9(String c)
	{
		screen2(c);
	}
	public void screen10(String c)
	{
		int n = Integer.parseInt(c);
		n-=1;
		if (n < user.getSelectedLibrary().getListOfBooksForThisLibrary(model.getConnection()).size() &&  n >= 0)
		{
			user.setSelectedBook(user.getSelectedLibrary().getListOfBooksForThisLibrary(model.getConnection()).get(n));
			if (!user.getSelectedLibrary().checkForLoan(user.getSelectedBook(), user, model.getConnection()))
			{
				user.getSelectedLibrary().checkOutBook(user.getCardNo(), user.getSelectedBook(), model.getConnection());
				screen = 8;
			}
			else
			{
				view.alreadyCheckedOut();
			}
		}
		screen = 8;
	}
	public void screen11(String c)
	{
		int n = Integer.parseInt(c);
		n-=1;
		if (n < model.getLibraryList().size() &&  n >= 0)
		{

			System.out.println("Selected: " + model.getLibraryList().get(n).getName());
			user.setSelectedLibrary(model.getLibraryList().get(n));
			screen += 1;
		}
		else
			//quit to previous
			screen = 8;
	}
	public void screen12(String c)
	{
		ArrayList<Book> listOfOwedBooks = user.getSelectedLibrary().getListOfOwedBooks(user.getCardNo(), model.getConnection());
		int n = Integer.parseInt(c);
		n-=1;
		if (n < listOfOwedBooks.size() && n >= 0)
		{
			//System.out.println("");
			user.getSelectedLibrary().returnBook(listOfOwedBooks.get(n), user.getCardNo(), model.getConnection());
		}
		screen = 8;
	}
	
	public void screen13(String c)
	{
		switch (c)
		{
		case "1":
			screen = 14;
			break;
		case "2":
			screen = 18;
			break;
		case "3":
			screen = 22;
			break;
		case "4":
			screen = 26;
		break;
		case "5":
			screen = 30;
			break;
		default:
			screen = 0;
			break;
		}
	}
	
	public void screen14(String c)
	{
		switch(c){
		case "1":
			screen += 1;
			break;
		case "2":
			screen += 2;
			break;
		case "3":
			screen += 3;
			break;
		default:
			screen = 13;
		break;
		}
	}
	
	public void screen15(String c, Scanner sc){
		System.out.println("Now select from the list of publishers: ");
		
		//select from the list of publishers
		ArrayList<Publisher> list = Publisher.getPublisherList(model.getConnection());
		view.printPublisherList(list);
		int n = sc.nextInt();
		n -= 1;
		if (n >= list.size() || n < 0)
		{
			screen = 13;
			return;
		}
		//select from the list of authors
		ArrayList<Author> authorList = Author.getListOfAuthors(model.getConnection());
		System.out.println("Now select from the list of Authors: ");
		view.printListOfAuthors(authorList);
		int n1 = sc.nextInt();
		n1 -= 1;
		if (n >= authorList.size() || n < 0)
		{
			screen = 13;
			return;
		}	
		
		//select from the list of Genres
		ArrayList<Genre> listOfGenres = Genre.getListOfGenre(model.getConnection());
		System.out.println("Now select from the list of genres: ");
		view.printListOfGenre(listOfGenres);
		int n2 = sc.nextInt();
		n2 -= 1;
		if (n < listOfGenres.size() || n >= 0)
		{
			user.addBook(c, list.get(n).getId(), authorList.get(n1).getId(), listOfGenres.get(n2).getGenreId(), model.getConnection());
		}
		

		
		
		
		screen = 13;
		
	}
	public void screen16(String c, Scanner sc)
	{
		ArrayList<Book> listOfBooks = model.getBookList();
		int n = Integer.parseInt(c);
		n -= 1;
		if (n < listOfBooks.size() && n >= 0)
		{
			user.setSelectedBook(listOfBooks.get(n));
		}
		else
		{
			screen = 14;
			return;
			
		}System.out.println("Enter the new title: ");
		String title = sc.nextLine();
		System.out.println("Select a new publisher: ");
		ArrayList<Publisher> list = Publisher.getPublisherList(model.getConnection());
		view.printPublisherList(list);
		n = sc.nextInt();
		n -= 1;
		if (n < list.size() && n >= 0)
		{
			user.updateBook(user.getSelectedBook().getBookId(), title, list.get(n).getId(), model.getConnection());
		}
		screen = 13;
		
	}
	
	public void screen17(String c)
	{
		ArrayList<Book> listOfBooks = model.getBookList();
		int n = Integer.parseInt(c);
		n -= 1;
		if (n < listOfBooks.size() && n >= 0)
		{
			user.deleteBook(listOfBooks.get(n).getBookId(), model.getConnection());
		}
		screen = 13;
	}
	
	public void screen19(String c, Scanner sc)
	{
		System.out.println("Plese write the address: ");
		String address = sc.nextLine();
		System.out.println("Please write the Phone: ");
		String phone = sc.nextLine();
		user.addPublisher(c, address, phone, model.getConnection());
		screen = 13;
	}
	
	public void screen20(String c, Scanner sc)
	{
		ArrayList<Publisher> list = Publisher.getPublisherList(model.getConnection());

		//c = choice of publisher
		int n = Integer.parseInt(c);
		n-=1;
		if (n < list.size() && n >= 0)
		{
			System.out.println("Please write the new name: ");
			String name = sc.nextLine();
			System.out.println("Please write the new address: ");
			String address = sc.nextLine();
			System.out.println("Please write the new phone: ");
			String phone = sc.nextLine();
			user.updatePublisher(list.get(n).getId(), name, address, phone, model.getConnection());
		}
		screen = 13;
		
	}
	
	public void screen21(String c)
	{
		ArrayList<Publisher> listOfPublishers = Publisher.getPublisherList(model.getConnection());
		int n = Integer.parseInt(c);
		n -= 1;
		if (n < listOfPublishers.size() && n >= 0)
		{
			user.deletePublisher(listOfPublishers.get(n).getId(), model.getConnection());
		}
		screen = 13;
	}
	
	public void screen23(String c, Scanner sc)
	{
		System.out.println("Please type the address: ");
		String address = sc.nextLine();
		user.addLibraryBranch(c, address, model.getConnection());
		screen = 13;
	}
	
	public void screen24(String c, Scanner sc)
	{
		
		ArrayList<Library> listOfLibraries = Library.getLibraries(model.getConnection());
		int n = Integer.parseInt(c);
		n-=1;
		if (n < listOfLibraries.size() && n>= 0)
		{
			System.out.println("Please enter the name: ");
			String name = sc.nextLine();
			
			System.out.println("Please enter the address: ");
			String address = sc.nextLine();
			
			user.updateLibraryBranch(name, address, listOfLibraries.get(n).getBranchId(), model.getConnection());
		}
		screen = 13;
	}
	public void screen25(String c)
	{
	
		ArrayList<Library> listOfLibraries = Library.getLibraries(model.getConnection());
		int n = Integer.parseInt(c);
		n-=1;
		if (n < listOfLibraries.size() && n>= 0)
		{
			user.deleteLibraryBranch(listOfLibraries.get(n).getBranchId(), model.getConnection());
		}
		screen = 13;
	}
	
	public void screen27(String c, Scanner sc)
	{
		System.out.println("Please input the address for your new borrower: ");
		String address = sc.nextLine();
		System.out.println("Please input the phone for the new borrower: ");
		String phone = sc.nextLine();
		user.addBorrower(c, address, phone, model.getConnection());
		screen = 13;
		
	}
	
	
	//update 
	public void screen28(String c, Scanner sc)
	{
		ArrayList<Borrower> listOfBorrowers = Borrower.getListOfBorrowers(model.getConnection());
		int n = Integer.parseInt(c);
		n-=1;
		if (n < listOfBorrowers.size() && n>= 0)
		{
			System.out.println("Please input the new name of the borrower: ");
			String name = sc.nextLine();
			
			System.out.println("Please input the new phone of the borrower: ");
			String phone = sc.nextLine();
			
			System.out.println("Please input the address of the borrower: ");
			String address = sc.nextLine();
			
			user.updateBorrower(name, address, phone, listOfBorrowers.get(n).getCardNo(), model.getConnection());
		}
		screen = 13;
	}
	public void screen29(String c)
	{
		ArrayList<Borrower> listOfBorrowers = Borrower.getListOfBorrowers(model.getConnection());
		int n = Integer.parseInt(c);
		n -= 1;
		if (n < listOfBorrowers.size() && n >= 0)
		{
			user.deleteBorrower(listOfBorrowers.get(n).getCardNo(), model.getConnection());
		}
		screen = 13;
	}
	
	public void screen30(String c)
	{
		ArrayList<Bookloan> list = Bookloan.getBookLoans(model.getConnection());
		int n = Integer.parseInt(c);
		n-=1;
		if (n < list.size() && n >= 0)
		{
			list.get(n).addOneWeek(model.getConnection());
		}
		screen = 13;
	}
	
	/* SCREEN INFORMATION:
	 * 0 = MAIN MENU (main)
	 * 1 = ENTER THE BRANCH YOU MANAGE (lib1)
	 * 2 = DISPLAY THE LIBRARIES (lib2)
	 * 3 = DISPLAY INDIVIDUAL LIBRARY MENU (lib3)
	 * 4 = ENTER NEW BRANCH NAME(LIB3, OPTION 1)
	 * 5 = ADD COPIES OF BOOK TO THE BRANCH
	 * 6 = ENTER THE NEW NUMBER OF COPIES
	 * 7 = ENTER CARD NO (BORROWER)
	 * 8 = BORR1
	 * 9 = SELECT LIBRARY
	 * 10 = CHECK OUT A BOOK
	 * 11 = SELECT LIBRARY
	 * 12 = SELECT BOOK TO RETURN
	 * 13 = ADMIN PANEL (ADM1)
	 * 14 = AUD BOOK MENU
	 * 15 = ADD BOOK
	 * 16 = UPDATE BOOK
	 * 17 = DELETE BOOK
	 * 18 = ADD UPDATE DELETE PUBLISHER
	 * 19 = ADD PUBLISHER
	 * 20 = UPDATE PUBLISHER
	 * 21 = DELETE PUBLISHER
	 * 22 = ADD UPDATE DELETE LIBRARY
	 * 23 = ADD LIBRARY
	 * 24 = UPDATE LIBRARY
	 * 25 = DELETE LIBRARY
	 * 26 = ADD UPDATE DELETE BORROWER
	 * 27 = ADD BORROWER
	 * 28 = UPDATE BORROWER
	 * 29 = DELETE BORROWER
	 * 30 = OVERRIDE LOAN
	 */

	public void takeInput(Scanner sc)
	{
		//select the input depending on the current state of the controller


		try{
			String c = sc.nextLine();

			switch (screen)
			{
			//first menu
			case 0:
				selectUser(c);
				break;
			case 1:
				screen1(c);
				break;
			case 2:
				screen2(c);
				break;
			case 3:
				screen3(c);
				break;
			case 4:
				updateDetails(c);
				break;
			case 5:
				screen5(c);
				break;
			case 6:
				screen6(c);
				break;
			case 7:
				screen7(c);
				break;
			case 8:
				screen8(c);
				break;
			case 9:
				screen9(c);
				break;
			case 10:
				screen10(c);
				break;
			case 11:
				screen11(c);
				break;
			case 12:
				screen12(c);
				break;
			case 13:
				screen13(c);
				break;
			case 14:
				screen14(c);
				break;
			case 15:
				screen15(c, sc);
				break;
			case 16:
				screen16(c, sc);
				break;
			case 17:
				screen17(c);
				break;
			case 18:
				screen14(c);
				break;
			case 19:
				screen19(c, sc);
				break;
			case 20:
				screen20(c, sc);
				break;
			case 21:
				screen21(c);
				break;
			case 22:
				screen14(c);
				break;
			case 23:
				screen23(c, sc);
				break;
			case 24:
				screen24(c, sc);
				break;
			case 25:
				screen25(c);
				break;
			case 26:
				screen14(c);
				break;
			case 27:
				screen27(c, sc);
				break;	
			case 28:
				screen28(c,sc);
				break;
			case 29:
				screen29(c);
				break;
			case 30:
				screen30(c);
				break;
			default:
				break;
			}
		}
		catch (Exception e)
		{

		}


	}



	public void updateView()
	{
		switch (screen) {
		case 0:
			view.firstMenu();
			break;
		case 1:
			view.enterBranchYouManage();
			break;
		case 2:
			view.printLibraries(model.getLibraryList());
			break;
		case 3:
			view.libraryMenu();
			break;
		case 4:
			updateLibMenu();
			break;
		case 5:
			view.pickBookForBranch(model.getBookList());
			break;
		case 6:
			view.updateNumberOfCopiesForBook(user.getSelectedLibrary().getNumberOfCopiesForBook(user.getSelectedBook().getBookId(), model.getConnection()));
			break;
		case 7:
			view.enterCardNumber();
			break;
		case 8:
			view.borr1();
			break;
		case 9:
			view.checkOutABook(Library.getLibraries(model.getConnection()));
			break;
		case 10:
			view.pickBookForCheckout(user.getSelectedLibrary().getListOfBooksForThisLibrary(model.getConnection()));
			break;
		case 11:
			view.returnABook(Library.getLibraries(model.getConnection()));
			break;
		case 12:
			view.selectBookToReturn(user.getSelectedLibrary().getListOfOwedBooks(user.getCardNo(), model.getConnection()));
			break;
		case 13:
			view.adminMenu();
			break;
		case 14:
			view.AUDBookAuthor();
			break;
		case 15:
			view.addBook1();
			break;
		case 16:
			view.updateBook(Book.listOfBooks(model.getConnection()));
			break;
		case 17:
			view.deleteBook(Book.listOfBooks(model.getConnection()));
			break;
		case 18:
			view.AUDPublishers();
			break;
		case 19:
			view.addPublisher();
			break;
		case 20:
			view.updatePublisher(Publisher.getPublisherList(model.getConnection()));
			break;
		case 21:
			view.deletePublisher(Publisher.getPublisherList(model.getConnection()));
			break;
		case 22:
			view.AUDLibraries();
			break;
		case 23:
			view.addLibraryBranch();
			break;
		case 24:
			view.updateLibraryBranch(Library.getLibraries(model.getConnection()));
			break;
		case 25:
			view.deleteLibrary(Library.getLibraries(model.getConnection()));
			break;
		case 26:
			view.AUDBorrowers();
			break;
		case 27:
			view.addBorrower();
			break;
		case 28:
			view.updateBorrower(Borrower.getListOfBorrowers(model.getConnection()));
			break;
		case 29:
			view.deleteBorrower(Borrower.getListOfBorrowers(model.getConnection()));
			break;
		case 30:
			view.OverrideLoan(Bookloan.getBookLoans(model.getConnection()));
			break;
		default:
			break;
		}



	}



}

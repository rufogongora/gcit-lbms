package com.gcit.lbms.controller;

import java.io.IOException;
import java.lang.Character.Subset;
import java.util.Scanner;

import com.gcit.lbms.model.Administrator;
import com.gcit.lbms.model.Book;
import com.gcit.lbms.model.Librarian;
import com.gcit.lbms.model.Library;
import com.gcit.lbms.model.Menu;
import com.gcit.lbms.model.User;
import com.gcit.lbms.model.dbConnection;
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
			screen = 4;
			break;
		case "3":
			Administrator admin = new Administrator();
			user = admin;
			//screen = somenumber
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
	
	
	/* SCREEN INFORMATION:
	 * 0 = MAIN MENU (main)
	 * 1 = ENTER THE BRANCH YOU MANAGE (lib1)
	 * 2 = DISPLAY THE LIBRARIES (lib2)
	 * 3 = DISPLAY INDIVIDUAL LIBRARY MENU (lib3)
	 * 4 = ENTER NEW BRANCH NAME(LIB3, OPTION 1)
	 * 5 = ADD COPIES OF BOOK TO THE BRANCH
	 * 6 = ENTER THE NEW NUMBER OF COPIES
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
				default:
					break;
				}
			}
			catch (Exception e)
			{

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
			view.printListOfBooks(model.getBookList());
			break;
		case 6:
			view.updateNumberOfCopiesForBook(user.getSelectedLibrary().getNumberOfCopiesForBook(user.getSelectedBook().getBookId(), model.getConnection()));
			break;
		
		default:
			break;
		}



	}



}

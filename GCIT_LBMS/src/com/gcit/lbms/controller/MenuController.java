package com.gcit.lbms.controller;

import java.io.IOException;
import java.util.Scanner;

import com.gcit.lbms.model.Administrator;
import com.gcit.lbms.model.Librarian;
import com.gcit.lbms.model.Menu;
import com.gcit.lbms.model.User;
import com.gcit.lbms.model.dbConnection;
import com.gcit.lbms.views.MenuView;

public class MenuController {

	private MenuView view;
	private Menu model;
	private User user;
	int screen;

	public MenuController(Menu m, MenuView v)
	{
		model = m;
		view = v;
		screen = 0;
	}

	public void setUser(User u)
	{
		user = u;
	}

	public void selectUser(int c)
	{
		switch (c)
		{
		//they picked Librarian
		case 1:
			Librarian lib = new Librarian();
			user = lib;
			screen = 1;
			break;
		case 2:
			screen = 4;
			break;
		case 3:
			Administrator admin = new Administrator();
			user = admin;
			//screen = somenumber
			break;
		default:
			break;
		}
	}

	public void screen1(int c)
	{
		switch (c) {
		case 1:
			screen += 1;
			break;
		case 2:
			screen -= 1;
			break;
		default:
			break;
		}
	}

	public void screen2(int c)
	{
		if (c < model.getLibraryList().size() && c > 0)
		{
			System.out.println("Selected: " + model.getLibraryList().get(c-1));
			user.setCurrentLibrary(c-1);
			screen += 1;
		}
		else
			//quit to previous
			screen -= 1;

	}

	public void screen3(int c)
	{
		switch (c)
		{
		case 1:
			break;
		default:
			break;
		}
	}




	/* SCREEN INFORMATION:
	 * 0 = MAIN MENU (main)
	 * 1 = ENTER THE BRANCH YOU MANAGE (lib1)
	 * 2 = DISPLAY THE LIBRARIES (lib2)
	 * 3 = DISPLAY INDIVIDUAL LIBRARY MENU (lib3)
	 * 4 = INSERT CARD NUMBER FOR USER
	 * 
	 */

	public void takeInput(Scanner sc)
	{
		//select the input depending on the current state of the controller
		switch (screen)
		{
		//first menu
		case 0:
			try {
				int c = sc.nextInt();
				selectUser(c);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case 1:
			try{
				int c = sc.nextInt();
				screen1(c);
			}
			catch(Exception e)
			{

			}

			break;
		case 2:
			try {
				int c = sc.nextInt();
				screen2(c);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;

		case 3:
			try{

			}catch (Exception e)
			{

			}
		default:
			break;
		}
		//sc.close();

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


		default:
			break;
		}



	}



}

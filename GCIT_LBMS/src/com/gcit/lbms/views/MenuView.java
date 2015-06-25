package com.gcit.lbms.views;

import java.util.ArrayList;

import com.gcit.lbms.model.Library;

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
}

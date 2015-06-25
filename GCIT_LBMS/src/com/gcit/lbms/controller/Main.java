package com.gcit.lbms.controller;

import java.util.Scanner;

import com.gcit.lbms.model.Menu;
import com.gcit.lbms.model.dbConnection;
import com.gcit.lbms.views.MenuView;

public class Main {

	public static void main(String[] args) {
		
		//create the connection
		dbConnection conn = new dbConnection("jdbc:mysql://localhost:3306/library", "root", "myrland4ever");
		
		//Create a Model
		Menu menu = new Menu(conn);
		
		//Create a view
		MenuView mv = new MenuView();
		
		//create the controller
		MenuController mc = new MenuController(menu, mv);
		
		
		Scanner sc = new Scanner(System.in);
		while (true)
		{
			//update view
			mc.updateView();
			mc.takeInput(sc);
		}

	}

}

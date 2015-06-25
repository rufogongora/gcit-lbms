package com.gcit.lbms.model;

public class Borrower extends User {

	private String name;
	private int cardNo;
	private String address;
	private String phone;
	
	public Borrower(String name, int cardNo, String address, String phone)
	{
		this.name = name;
		this.cardNo = cardNo;
		this.address = address;
		this.phone = phone;
		level= 1;
	}
}

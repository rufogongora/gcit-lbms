package com.gcit.lms.domain;

import java.util.List;

public class Borrower {
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<BookLoans> getLoans() {
		return loans;
	}
	public void setLoans(List<BookLoans> loans) {
		this.loans = loans;
	}
	int cardNo;
	String name;
	String address;
	String phone;
	List<BookLoans> loans;
}

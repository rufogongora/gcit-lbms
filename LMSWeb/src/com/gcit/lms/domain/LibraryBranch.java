package com.gcit.lms.domain;

import java.util.List;

public class LibraryBranch {
	int branchId;
	String branchName;
	String branchAddress;
	List<BookLoans> loans;
	List<BookCopies> bookCopies;

	public List<BookCopies> getBookCopies() {
		return bookCopies;
	}
	public void setBookCopies(List<BookCopies> bookCopies) {
		this.bookCopies = bookCopies;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	public List<BookLoans> getLoans() {
		return loans;
	}
	public void setLoans(List<BookLoans> loans) {
		this.loans = loans;
	}
	public String toJson(){
	    String jsonData = "{ \"libraryBranchId\" : \"" + getBranchId()+  "\", \"libraryBranchName\" : \"" + getBranchName()+ 
	    		"\", \"libraryBranchAddress\" : \"" + getBranchAddress()+ 
	    		"\", \"books\" : [";
	    int i = 1;
	    if(bookCopies != null){
		    for(BookCopies b : bookCopies)
		    {
		    	jsonData += "{ \"bookId\" : \"" +b.getBook().getBookId() + "\", \"bookTitle\" : \"" +b.getBook().getTitle() + 
		    			"\" , \"libraryBranchId\" : \""+b.getBranch().getBranchId() + "\", \"libraryBranchName\" : \""+b.getBranch().getBranchName()
		    			+ "\", \"noOfCopies\" : \""+b.noOfCopies + "\"}";
		    	if (i < bookCopies.size())
		    		jsonData += ",";
		    	i++;
		    }
	    }
	    jsonData += "]}";
	    return jsonData;
	}
	
}

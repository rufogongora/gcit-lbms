package com.gcit.lms;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.domain.BookLoan;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Book;
import com.mongodb.util.JSON;

/**
 * Handles requests for the application home page.
 */
@RestController
public class BorrowerController {
	
	@Autowired
	BorrowerDAO borrowerDAO;	

	@Autowired
	BookDAO bookDAO;
	
	@Transactional
	@RequestMapping(value="/borrower/add", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Borrower addBorrower(@RequestBody Borrower borrower){
		try {
			borrowerDAO.addBorrower(borrower);
			return borrower;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	

	
	@RequestMapping(value="/borrower/login", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json", produces="application/json")
	public Borrower getOneBorrower(@RequestBody int cardNo){
		try {
			return borrowerDAO.readOne(cardNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Transactional
	@RequestMapping(value="/borrower/checkOut", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public BookLoan checkOut(@RequestBody BookLoan bookLoan){
		try {
			borrowerDAO.checkOut(bookLoan);
			return bookLoan;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Transactional
	@RequestMapping(value="/borrower/update", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Borrower updateBorrower(@RequestBody Borrower borrower){
		try {
			borrowerDAO.updateBorrower(borrower);
			return borrower;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	@Transactional
	@RequestMapping(value="/borrower/delete", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Borrower deleteBorrower(@RequestBody Borrower borrower){
		try {
			borrowerDAO.removeBorrower(borrower);
			return borrower;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/borrower/get/{pageNo}/{pageSize}/", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Borrower> getBorrowers(@PathVariable int pageNo, @PathVariable int pageSize){
		try {
			return borrowerDAO.search("", pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/borrower/get/{pageNo}/{pageSize}/{query}", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Borrower> getBorrowersSearch(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String query ){
		try {
			return borrowerDAO.search(query, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	@RequestMapping(value="/borrower/getCount/{query}", method={RequestMethod.GET} , produces="application/json")
	public long getCountQuery(@PathVariable String query) throws JsonProcessingException, IOException{
		try {
			return	borrowerDAO.getCount(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@RequestMapping(value="/borrower/getCount", method={RequestMethod.GET} , produces="application/json")
	public long getCount() throws JsonProcessingException, IOException{
		try {
			return	borrowerDAO.getCount("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
}

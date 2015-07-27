package com.gcit.lms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.domain.LibraryBranch;

@RestController
public class LibraryBranchController {

	@Autowired
	LibraryBranchDAO libraryBranchDAO;
	
	@RequestMapping(value="/libraryBranch/get/{pageNo}/{pageSize}/{field}", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<LibraryBranch> getAuthors(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String field){
		try {
			return libraryBranchDAO.search("", pageNo, pageSize, field);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/libraryBranch/get/{pageNo}/{pageSize}/{field}/{query}", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<LibraryBranch> getAuthorsSearch(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String field, @PathVariable String query ){
		try {
			return libraryBranchDAO.search(query, pageNo, pageSize, field);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	@Transactional
	@RequestMapping(value="/libraryBranch/add", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public LibraryBranch addAuthor(@RequestBody LibraryBranch libraryBranch){
		try {
			libraryBranchDAO.addLibraryBranch(libraryBranch);
			return libraryBranch;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping(value="/libraryBranch/getCount/{field}", method={RequestMethod.GET} , produces="application/json")
	public long getCount( @PathVariable String field) throws JsonProcessingException, IOException{
		try {
			return	libraryBranchDAO.getCount("", field);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@RequestMapping(value="/libraryBranch/getCount/{field}/{query}", method={RequestMethod.GET} , produces="application/json")
	public long getCountQuery( @PathVariable String field, @PathVariable String query) throws JsonProcessingException, IOException{
		try {
			return	libraryBranchDAO.getCount(query, field);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@Transactional
	@RequestMapping(value="/libraryBranch/delete", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public LibraryBranch deleteLibraryBranch(@RequestBody LibraryBranch libraryBranch){
		try {
			libraryBranchDAO.removeLibraryBranch(libraryBranch);
			return libraryBranch;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

	
	@Transactional
	@RequestMapping(value="/libraryBranch/update", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public LibraryBranch updateLibraryBranch(@RequestBody LibraryBranch libraryBranch){
		try {
			libraryBranchDAO.updateLibraryBranch(libraryBranch);
			return libraryBranch;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
}

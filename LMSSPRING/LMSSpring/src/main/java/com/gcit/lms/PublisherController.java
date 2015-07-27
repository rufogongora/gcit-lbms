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
import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Publisher;

@RestController
public class PublisherController {
	@Autowired
	PublisherDAO publisherDAO;
	
	@Autowired
	BookDAO bookDAO;
	
	@RequestMapping(value="/publisher/get/{pageNo}/{pageSize}/{field}", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Publisher> getAuthors(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String field){
		try {
			return publisherDAO.search("", pageNo, pageSize, field);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/publisher/get/{pageNo}/{pageSize}/{field}/{query}", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Publisher> getAuthorsSearch(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String field, @PathVariable String query ){
		try {
			return publisherDAO.search(query, pageNo, pageSize, field);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	@Transactional
	@RequestMapping(value="/publisher/add", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Publisher addAuthor(@RequestBody Publisher publisher){
		try {
			publisherDAO.addPublisher(publisher);
			return publisher;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping(value="/publisher/getCount/{field}", method={RequestMethod.GET} , produces="application/json")
	public long getCount( @PathVariable String field) throws JsonProcessingException, IOException{
		try {
			return	publisherDAO.getCount("", field);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@RequestMapping(value="/publisher/getCount/{field}/{query}", method={RequestMethod.GET} , produces="application/json")
	public long getCountQuery( @PathVariable String field, @PathVariable String query) throws JsonProcessingException, IOException{
		try {
			return	publisherDAO.getCount(query, field);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@Transactional
	@RequestMapping(value="/publisher/delete", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Publisher deletePublisher(@RequestBody Publisher publisher){
		try {
			bookDAO.removePublisher(publisher);
			publisherDAO.removePublisher(publisher);
			return publisher;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

	
	@Transactional
	@RequestMapping(value="/publisher/update", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Publisher updatePublisher(@RequestBody Publisher publisher){
		try {
			bookDAO.updatePublisher(publisher);
			publisherDAO.updatePublisher(publisher);
			return publisher;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
}

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
import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.mongodb.util.JSON;

/**
 * Handles requests for the application home page.
 */
@RestController
public class AuthorController {
	
	@Autowired
	AuthorDAO authorDAO;	

	@Autowired
	BookDAO bookDAO;
	
	@Transactional
	@RequestMapping(value="/author/add", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Author addAuthor(@RequestBody Author author){
		try {
			authorDAO.addAuthor(author);
			return author;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	

	
	@RequestMapping(value="/author/getOne", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json", produces="application/json")
	public Author getOneAuthor(@RequestBody Author author){
		try {
			return authorDAO.readOne(author.getAuthorId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}


	
	@Transactional
	@RequestMapping(value="/author/update", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Author updateAuthor(@RequestBody Author author){
		try {
			bookDAO.updateAuthor(author);
			authorDAO.updateAuthor(author);
			return author;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	@Transactional
	@RequestMapping(value="/author/delete", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Author deleteAuthor(@RequestBody Author author){
		try {
			bookDAO.removeAuthor(author.getAuthorId());
			authorDAO.removeAuthor(author);
			return author;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/author/get/{pageNo}/{pageSize}/", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Author> getAuthors(@PathVariable int pageNo, @PathVariable int pageSize){
		try {
			return authorDAO.search("", pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/author/get/{pageNo}/{pageSize}/{query}", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Author> getAuthorsSearch(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String query ){
		try {
			return authorDAO.search(query, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	@RequestMapping(value="/author/getCount/{query}", method={RequestMethod.GET} , produces="application/json")
	public long getCountQuery(@PathVariable String query) throws JsonProcessingException, IOException{
		try {
			return	authorDAO.getCount(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@RequestMapping(value="/author/getCount", method={RequestMethod.GET} , produces="application/json")
	public long getCount() throws JsonProcessingException, IOException{
		try {
			return	authorDAO.getCount("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
}

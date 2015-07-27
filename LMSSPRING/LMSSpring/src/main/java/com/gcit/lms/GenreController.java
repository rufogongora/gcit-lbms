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
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.domain.Genre;

@RestController
public class GenreController {

	@Autowired
	GenreDAO genreDAO;	

	@Autowired
	BookDAO bookDAO;
	
	@Transactional
	@RequestMapping(value="/genre/add", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Genre addGenre(@RequestBody Genre genre){
		try {
			genreDAO.addGenre(genre);
			return genre;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	

	
	@RequestMapping(value="/genre/getOne", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json", produces="application/json")
	public Genre getOneGenre(@RequestBody Genre genre){
		try {
			return genreDAO.readOne(genre.getGenreId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}


	
	@Transactional
	@RequestMapping(value="/genre/update", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Genre updateGenre(@RequestBody Genre genre){
		try {
			genreDAO.updateGenre(genre);
			bookDAO.updateGenre(genre);
			return genre;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	@Transactional
	@RequestMapping(value="/genre/delete", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public Genre deleteGenre(@RequestBody Genre genre){
		try {
			
			bookDAO.removeGenre(genre.getGenreId());
			genreDAO.removeGenre(genre);
			return genre;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/genre/get/{pageNo}/{pageSize}/", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Genre> getGenres(@PathVariable int pageNo, @PathVariable int pageSize){
		try {
			return genreDAO.search("", pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/genre/get/{pageNo}/{pageSize}/{query}", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Genre> getGenresSearch(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String query ){
		try {
			return genreDAO.search(query, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	@RequestMapping(value="/genre/getCount/{query}", method={RequestMethod.GET} , produces="application/json")
	public long getCountQuery(@PathVariable String query) throws JsonProcessingException, IOException{
		try {
			return	genreDAO.getCount(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@RequestMapping(value="/genre/getCount", method={RequestMethod.GET} , produces="application/json")
	public long getCount() throws JsonProcessingException, IOException{
		try {
			return	genreDAO.getCount("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
}

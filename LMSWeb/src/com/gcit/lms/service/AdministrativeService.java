package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;

public class AdministrativeService {

	public void createAuthor(Author author) throws Exception {
		//ConnectionUtil c = new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (author == null || author.getAuthorName() == null
					|| author.getAuthorName().length() == 0
					|| author.getAuthorName().length() > 45) {
				throw new Exception(
						"Author Name cannot be empty or more than 45 Chars");
			} else {
				AuthorDAO adao = new AuthorDAO(conn);
				adao.create(author);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public void updateAuthor(Author author) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (author == null || author.getAuthorName() == null
					|| author.getAuthorName().length() == 0
					|| author.getAuthorName().length() > 45) {
				throw new Exception(
						"Author Name cannot be empty or more than 45 Chars");
			} else {
				AuthorDAO adao = new AuthorDAO(conn);
				adao.update(author);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		
	}
	
	public void updateGenre(Genre genre) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (genre == null || genre.getGenreName() == null
					|| genre.getGenreName().length() == 0
					|| genre.getGenreName().length() > 45) {
				throw new Exception(
						"Author Name cannot be empty or more than 45 Chars");
			} else {
				GenreDAO gdao = new GenreDAO(conn);
				gdao.update(genre);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		
	}
	
	public void createGenre(Genre genre) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (genre == null || genre.getGenreName() == null
					|| genre.getGenreName().length() == 0
					|| genre.getGenreName().length() > 45) {
				throw new Exception(
						"Author Name cannot be empty or more than 45 Chars");
			} else {
				GenreDAO adao = new GenreDAO(conn);
				adao.create(genre);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public void deleteGenre(Genre genre) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gdao = new GenreDAO(conn);
		try {
			gdao.delete(genre);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	
	public void createPublisher(Publisher publisher) throws Exception {
		//ConnectionUtil c = new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		try {
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.create(publisher);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Author> readAuthors() throws Exception {
		//ConnectionUtil c = new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.readAll();
	}

	public List<Genre> readGenres() throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gdao = new GenreDAO(conn);
		return gdao.readAll();
	}
	
	public Genre readGenre(int genreId) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gdao = new GenreDAO(conn);
		return gdao.readOne(genreId);
	}
	
	public void deleteAuthor(Author author) throws Exception {
		//ConnectionUtil c = new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			adao.delete(author);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	public void deletePublisher(Publisher publisher) throws Exception {
		//ConnectionUtil c = new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try {
			pdao.delete(publisher);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public Author readAuthor(int id) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try{
			return adao.readOne(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			conn.rollback();
		}finally
		{
			conn.close();
		}
		return null;
	}
	
	public void createBook(Book book) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bdao = new BookDAO(conn);

		try{
			bdao.create(book);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public void updateBook(Book book) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bdao = new BookDAO(conn);
		try{
			bdao.update(book);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public void updatePublisher(Publisher publisher) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO bdao = new PublisherDAO(conn);
		try{
			bdao.update(publisher);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public void deleteBook(Book book) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bdao = new BookDAO(conn);

		try{
			bdao.delete(book);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	public List<Publisher> readPublishers() throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		return pdao.readAll();
	}
	public List<LibraryBranch> readLibraryBranches() throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		return lbdao.readAll();
	}
	
	public Publisher readOnePublisher(int id ) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		return pdao.readOne(id);
	}
	
	public List<Book> readBooks() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bdao = new BookDAO(conn);
		return bdao.readAll();
	}

	
	public LibraryBranch getLibrary(int id) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		return lbdao.readOne(id);
	}

	public void updateLibrary(LibraryBranch lb) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		try{
			lbdao.update(lb);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		
	}
	
	public List<Book> getListOfMissingBooksForLibrary(int libId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bdao = new BookDAO(conn);
		return bdao.getListOfMissingBooksForLibrary(libId);
		
	}

	public void updateNoOfCopies(BookCopies bc) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		try{
			bcdao.update(bc);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		
	}

	public List<Author> getAuthors(int pageNo) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO bdao = new AuthorDAO(conn);
		bdao.setPageNo(pageNo);
		
		return bdao.readAll();
		
		
	}

	public List<Genre> getGenres(int pageNo) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO bdao = new GenreDAO(conn);
		bdao.setPageNo(pageNo);
		
		return bdao.readAll();
	}
}

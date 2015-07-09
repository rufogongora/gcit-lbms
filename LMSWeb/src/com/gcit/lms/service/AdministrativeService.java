package com.gcit.lms.service;

import java.sql.Connection;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
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
	
	

	public List<Book> readBooks() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bdao = new BookDAO(conn);
		return bdao.readAll();
	}
}

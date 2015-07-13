package com.gcit.lms.web;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;








import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;













import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Publisher;
import com.gcit.lms.service.AdministrativeService;
import com.google.gson.Gson;
class Wrapper{
	String authorId;
	String authorName;
}
/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/addGenre", "/deleteGenre","/addPublisher", "/addBook", "/editBook", "/updateAuthor", "/viewAuthors", "/deleteBook",  "/deleteAuthor"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		case "/addAuthor":
			createAuthor1(request, response);
			break;
			
		case "/addPublisher":
			createPublisher(request, response);
			break;
		case "/updateAuthor":
			editAuthor(request, response);
			break;
		case "viewAuthors":
			viewAuthors(request, response);
			break;
		case "/deleteAuthor": 
			deleteAuthor(request, response);
			break;
		case "/addBook":
			createBook(request, response);
			break;
		case "/deleteBook":
			deleteBook(request, response);
			break;
		case "/editBook":
			updateBook(request, response);
			break;
		case "/addGenre":
			createGenre(request, response);
			break;
		case "/deleteGenre":
			deleteGenre(request, response);
		break;
		default:
			break;
		}
	}

	private void createAuthor(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authorName = request.getParameter("authorName");
		Author a = new Author();
		a.setAuthorName(authorName);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createAuthor(a);
			request.setAttribute("result", "Author Added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/menus/admin.jsp");
		rd.forward(request, response);
	}
	
	private void createAuthor1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authorName = request.getParameter("authorName");
		Author a = new Author();
		a.setAuthorName(authorName);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createAuthor(a);
			request.setAttribute("result", "Author Added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
		  String jsonData = "{ \"authorId\" : \"" + a.getAuthorId() +  "\"}"; 

		    // to send out the json data
		    response.getWriter().write(jsonData);
	}
	
	private void createGenre(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String genreName = request.getParameter("genreName");
		Genre g = new Genre();
		g.setGenreName(genreName);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createGenre(g);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
		  String jsonData = "{ \"genreId\" : \"" + g.getGenreId() +  "\"}"; 

		    // to send out the json data
		    response.getWriter().write(jsonData);
	}
	
	private void deleteGenre(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String genreId = request.getParameter("genreId");
		Genre genre = new Genre();
		System.out.println(genreId);
		genre.setGenreId(Integer.parseInt(genreId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/menus/admin.jsp");
		try {
			new AdministrativeService().deleteGenre(genre);
			response.getWriter().write("Success");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Author Delete Failed because: " + e.getMessage());
		}
		
	}

	private void createBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookName = request.getParameter("bookTitle");
		Book b = new Book();
		b.setTitle(bookName);
		String authorsString = request.getParameter("authors");
		String genresString = request.getParameter("genres");
		String publisherId = request.getParameter("publisherId");
		AdministrativeService adminService = new AdministrativeService();
		try {

			b.setAuthors(parseJson(authorsString));
			b.setGenres(parseJsonGenre(genresString));
			if (Integer.parseInt(publisherId) >= 0 )
			{
				Publisher p = adminService.readOnePublisher(Integer.parseInt(publisherId));
				b.setPublisher(p);
			}else
			{
				b.setPublisher(new Publisher());
				b.getPublisher().setPublisherName("No publisher");
			}
			adminService.createBook(b);
			request.setAttribute("result", "Author Added Successfully");
			
			//json to answer back
			String jsonData = "{ \"bookId\" : \"" + b.getBookId() +
					"\", \"publisherName\" : \"" + b.getPublisher().getPublisherName() +  "\", \"bookTitle\" : \"" + b.getTitle() + "\" }";
			
			response.getWriter().write(jsonData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}


	}

	private ArrayList<Author> parseJson(String s) throws Exception {
		ArrayList<Author> authors = new ArrayList<Author>();
		StringTokenizer st = new StringTokenizer(s, "\"{;:[],}");
		AdministrativeService adminService = new AdministrativeService();
		
	    int i = 0;
	    int authorId =0;
	    String authorName = "";
		while (st.hasMoreTokens()) {
			String sx = st.nextToken();
			if (i == 4)
			{
			    Author a = adminService.readAuthor(authorId);
				authors.add(a);
				i = 0;
			}
			//this is an id
			if (i == 1)
			{
				authorId = Integer.parseInt(sx);
			}
		   i++;
		}
		Author a = adminService.readAuthor(authorId);
		authors.add(a);
		return authors;
	}
	
	private ArrayList<Genre> parseJsonGenre(String s) throws Exception {
		ArrayList<Genre> genres = new ArrayList<Genre>();
		StringTokenizer st = new StringTokenizer(s, "\"{;:[],}");
		AdministrativeService adminService = new AdministrativeService();
		
	    int i = 0;
	    int genreId =0;
	    String authorName = "";
		while (st.hasMoreTokens()) {
			String sx = st.nextToken();
			if (i == 4)
			{
			   	Genre g = adminService.readGenre(genreId);
				genres.add(g);
				i = 0;
			}
			//this is an id
			if (i == 1)
			{
				genreId = Integer.parseInt(sx);
			}
		   i++;
		}
		Genre g = adminService.readGenre(genreId);
		genres.add(g);
		return genres;
	}
	
	
	private void editAuthor(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authorName = request.getParameter("authorName");
		int id = Integer.parseInt(request.getParameter("authorId"));
		Author a = new Author();
		a.setAuthorName(authorName);
		a.setAuthorId(id);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.updateAuthor(a);
			request.setAttribute("result", "Author Updated Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
		String jsonData = "{ \"authorId\" : \"" + a.getAuthorId() +  "\", \"authorName\" : \"" + a.getAuthorName() +  "\"}"; 
		
		// to send out the json data
		response.getWriter().write(jsonData);
	}

	private void updateBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		

		String bookName = request.getParameter("bookTitle");
	

		int bookId = Integer.parseInt(request.getParameter("bookId"));

		Book b = new Book();
		b.setTitle(bookName);
		b.setBookId(bookId);
		String authorsString = request.getParameter("authors");
		String genresString = request.getParameter("genres");
		String publisherId = request.getParameter("publisherId");
		AdministrativeService adminService = new AdministrativeService();

		try {

			b.setAuthors(parseJson(authorsString));
			b.setGenres(parseJsonGenre(genresString));
			if (Integer.parseInt(publisherId) >= 0 )
			{
				Publisher p = adminService.readOnePublisher(Integer.parseInt(publisherId));
				b.setPublisher(p);
			}else
			{
				b.setPublisher(new Publisher());
				b.getPublisher().setPublisherName("No publisher");
			}
			adminService.updateBook(b);
			//json to answer back
			String jsonData = "{ \"bookId\" : \"" + b.getBookId() +
					"\", \"publisherName\" : \"" + b.getPublisher().getPublisherName() +  "\", \"bookTitle\" : \"" + b.getTitle() + "\" }";
			
			response.getWriter().write(jsonData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
	}

	private void createPublisher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String publisherName = request.getParameter("publisherName");
		String publisherAddress = request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");
		Publisher p = new Publisher();
		p.setPublisherName(publisherName);
		p.setPublisherAddress(publisherAddress);
		p.setPublisherPhone(publisherPhone);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createPublisher(p);
			request.setAttribute("result", "Publisher added Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Publisher add failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/menus/admin.jsp");
		rd.forward(request, response);
	}
	
	private List<Author> viewAuthors(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			return new AdministrativeService().readAuthors();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void deleteAuthor(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authorId = request.getParameter("authorId");
		Author author = new Author();
		author.setAuthorId(Integer.parseInt(authorId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/menus/admin.jsp");
		try {
			new AdministrativeService().deleteAuthor(author);

			request.setAttribute("result", "Author Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Author Delete Failed because: " + e.getMessage());
		}
		
	}
	
	
	
	private void deleteBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = new Book();
		book.setBookId(Integer.parseInt(bookId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/menus/admin.jsp");
		try {
			new AdministrativeService().deleteBook(book);
			response.getWriter().write("Success");
			request.setAttribute("result", "Author Deleted Succesfully!");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Author Delete Failed because: " + e.getMessage());
		}
		
	}
	


}

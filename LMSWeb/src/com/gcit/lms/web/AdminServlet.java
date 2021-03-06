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
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
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
@WebServlet({ "/addAuthor", "/getBooks", "/getPublishers","/getGenres","/getAuthors" ,"/updateNoOfCopies", "/getLibrary", "/updatePublisher", "/deletePublisher", "/updateGenre",
	"/addGenre", "/deleteGenre","/addPublisher", "/updateLibrary","/addBook", "/editBook",
	"/updateAuthor", "/viewAuthors", "/deleteBook",  "/deleteAuthor"})
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
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		case "/getLibrary":
			getLibrary(request, response);
			break;
		case "/getAuthors":
			getAuthors(request, response);
			break;
		case "/getGenres":
			getGenres(request, response);
			break;
		case "/getBooks":
			getBooks(request, response);
			break;
		case "/getPublishers":
			getPublishers(request, response);
			break;
		
		}
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
		case "/updateGenre":
			editGenre(request, response);
			break;
		case "/deletePublisher":
			deletePublisher(request, response);
			break;
		case "/updatePublisher":
			updatePublisher(request, response);
			break;
		case "/updateLibrary":
			updateLibrary(request, response);
			break;
		case "/updateNoOfCopies":
			updateNoOfCopies(request, response);
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
		a.setBooks(new ArrayList<Book>());
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createAuthor(a);
			request.setAttribute("result", "Author Added Successfully");

		    // to send out the json data
			Gson gson = new Gson();
			
		    response.getWriter().write(gson.toJson(a));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
			
			

	}
	
	private void updateLibrary(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String branchName = request.getParameter("libraryBranchName");
		int id = Integer.parseInt(request.getParameter("libraryBranchId"));
		String branchAddress = request.getParameter("libraryBranchAddress");
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchId(id);
		lb.setBranchAddress(branchAddress);
		lb.setBranchName(branchName);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.updateLibrary(lb);
			response.getWriter().write(lb.toJson());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}

		// to send out the json data

	}
	
	private void getLibrary(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int libraryId = Integer.parseInt(request.getParameter("libraryBranchId"));
		
		AdministrativeService adminService = new AdministrativeService();
		try {
			LibraryBranch lb = adminService.getLibrary(libraryId);

		    // to send out the json data
		    response.getWriter().write(lb.toJson());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
	}
	private void getAuthors(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		
		AdministrativeService adminService = new AdministrativeService();
		try {
			List<Author> authors = adminService.getAuthors(pageNo);
			Gson gson = new Gson();
		    // to send out the json data
		    response.getWriter().write(gson.toJson(authors));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
	}

	private void getPublishers(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		AdministrativeService adminService = new AdministrativeService();
		try {
			List<Publisher> publishers = adminService.readPublishers();
			Gson gson = new Gson();
		    // to send out the json data
		    response.getWriter().write(gson.toJson(publishers));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
	}
	private void getBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		
		AdministrativeService adminService = new AdministrativeService();
		try {
			List<Book> books = adminService.readBooks();
			Gson gson = new Gson();
		    // to send out the json data
		    response.getWriter().write(gson.toJson(books));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
	}
		
	
	private void getGenres(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		
		AdministrativeService adminService = new AdministrativeService();
		try {
			List<Genre> genres = adminService.getGenres(pageNo);
			Gson gson = new Gson();
		    // to send out the json data
		    response.getWriter().write(gson.toJson(genres));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
	}
	
	
	
	
	private void createGenre(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String genreName = request.getParameter("genreName");
		Genre g = new Genre();
		g.setGenreName(genreName);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.createGenre(g);
			Gson gson = new Gson();
		    response.getWriter().write(gson.toJson(g));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}


		    // to send out the json data

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

			Gson gson = new Gson();
			
			response.getWriter().write(gson.toJson(b));
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
	
	
	private void editGenre(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String genreName = request.getParameter("genreName");
		int id = Integer.parseInt(request.getParameter("genreId"));
		Genre g = new Genre();
		g.setGenreName(genreName);
		g.setGenreId(id);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.updateGenre(g);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
		String jsonData = "{ \"genreId\" : \"" + g.getGenreId() +  "\", \"genreName\" : \"" + g.getGenreName() +  "\"}"; 
		
		// to send out the json data
		response.getWriter().write(jsonData);
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
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(a));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
		// to send out the json data

	}
	
	private void updatePublisher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String publisherName = request.getParameter("publisherName");
		int id = Integer.parseInt(request.getParameter("publisherId"));
		String publisherAddress = request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");
		Publisher p = new Publisher();
		p.setPublisherId(id);
		p.setPublisherName(publisherName);
		p.setPublisherAddress(publisherAddress);
		p.setPublisherPhone(publisherPhone);
		AdministrativeService adminService = new AdministrativeService();
		try {
			adminService.updatePublisher(p);
			request.setAttribute("result", "Author Updated Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author add failed " + e.getMessage());
		}
		String jsonData = "{ \"publisherId\" : \"" + p.getPublisherId() + 
				"\", \"publisherName\" : \"" + p.getPublisherName() +
				"\", \"publisherAddress\" : \"" + p.getPublisherAddress() + 
				"\", \"publisherPhone\" : \"" + p.getPublisherPhone() +  "\"}";	
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
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(b));
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
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(p));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Publisher add failed " + e.getMessage());
		}
		

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

	private void deletePublisher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String publisherId = request.getParameter("publisherId");
		Publisher publisher = new Publisher();
		publisher.setPublisherId(Integer.parseInt(publisherId));

		try {
			new AdministrativeService().deletePublisher(publisher);
			response.getWriter().write("Success");
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
	
	private void updateNoOfCopies(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		
		Book book = new Book();
		book.setBookId(bookId);
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchId(branchId);
		
		BookCopies bc = new BookCopies();
		bc.setBook(book);
		bc.setBranch(branch);
		bc.setNoOfCopies(Integer.parseInt(request.getParameter("noOfCopies")));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/menus/admin.jsp");
		try {
			new AdministrativeService().updateNoOfCopies(bc);
			response.getWriter().write("Success");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Author Delete Failed because: " + e.getMessage());
		}
		
	}
	


}

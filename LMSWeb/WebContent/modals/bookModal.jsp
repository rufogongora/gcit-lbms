      <!-- Modal -->
<%@page import="com.gcit.lms.domain.Book"%>
<%@page import="com.gcit.lms.domain.Genre"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%@page import="com.gcit.lms.domain.Author"%>

<%

	List<Book> books = adminService.readBooks();
	List<Genre> genres = adminService.readGenres();
	List<Publisher> publishers = adminService.readPublishers();
%>

<div class="modal fade" id="bookModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel2">Book Menu</h4>
      </div>
      <div class="modal-body">
		<table class="table" id="bookTable">

			<tr>
				<th>Book ID</th>
				<th>Title</th>
				<th>Publisher</th>
				<th>Edit Book</th>
				<th>Delete Book</th>
			</tr>
			<%for(Book b: books){ %>
			<tr class="bookRow">
				<td><%out.print(b.getBookId()); %></td>
				<td><%out.print(b.getTitle()); %></td>
				<td><% if (b.getPublisher() != null){
					out.print(b.getPublisher().getPublisherName());
				}else
					{
					out.print("No publisher");
					}%></td>
				<td><button type="button" data-toggle="modal" data-target="#addBookModal"
				
				authors = "[<% int i =0; for (Author a : b.getAuthors()){ 
						%>{ &quot;authorId&quot; : &quot;<%
						out.print(a.getAuthorId());
						%>&quot; , &quot;authorName&quot; : &quot;<%
						out.print(a.getAuthorName());
						%>&quot;}<% i++; if (i!=b.getAuthors().size()){ out.print(",");} }%>]"

				genres = "[<% i =0;
						if (b.getGenres() != null){
						for (Genre g : b.getGenres()){ 
						%>{ &quot;genreId&quot; : &quot;<%
						out.print(g.getGenreId());
						%>&quot; , &quot;genreName&quot; : &quot;<%
						out.print(g.getGenreName());
						%>&quot;}<% i++; if (i!=b.getGenres().size()){ out.print(",");}} }%>]"		

						 class="btn btn-md btn-success editBook" bookId = "<%out.print(b.getBookId()); %>">Edit</button></td>
				<td><button type="button" class="btn btn-md btn-danger deleteBook" bookId = "<%out.print(b.getBookId()); %>">Delete</button></td>
			</tr>
			<%} %>
			<tr id="cloneBookRow" style="display:none">
				<td>book id</td>
				<td>title</td>
				<td>publisher</td>
				<td><button type="button" data-toggle="modal" data-target="#addBookModal" class="btn btn-md btn-success editBook" bookId="1" authors="0">Edit</button></td>
				<td><button type="button" class="btn btn-md btn-danger deleteBook" bookId = "1">Delete</button></td>
			</tr>
		</table>
			
	
	
			<h2>Click to add a Book</h2>
			<p>
		        <button type="submit" id="addBookButton" class="btn btn-primary" data-toggle="modal" data-target="#addBookModal">Add</button>
		        <span class = "successMessageBook" style="color:green"></span>
			</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>




<!-- Modal -->
<div class="modal fade" id="addBookModal" tabindex="-1" role="dialog" aria-labelledby="addBookLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="addBookLabel">Add Book</h4>
      </div>
      <div class="modal-body">
      
      <table class="table">
      <tr>
       	<td><h2>Book Title:</h2> <input class="form-control" placeholder="Enter the title..." type="text" name="authorName" id="bookTitleInput"/></td>       	
       	</tr>
       	<tr>
       <td>	<h2>Book Author:</h2> 
       	
       	<select id = "authorDropdown" class="form-control"> 
       		<% for (Author a : authors) {%>
       			<option value="<%out.print(a.getAuthorId()); %>"><%out.print(a.getAuthorName()); %></option>
       		<%} %>
       	</select>
       	</td>
       	<table class="table table-hover" id ="selectedAuthorsList" >
			<tr><th>Author Name: </th><th>Remove</th></tr>
			<tr id="copyMeRowAuthor" style="display:none">
				<td>One</td>
				<td>
					<a href="#" authorId="">
						<span class="glyphicon glyphicon-remove" aria-hidden="true">
						</span>
					</a>
				</td>
			</tr>
		</table>
		</tr>
		
		<tr>
       <td>	<h2>Book Genre:</h2> 
       	
       	<select id = "genreDropdown" class="form-control"> 
       		<% for (Genre g : genres) {%>
       			<option value="<%out.print(g.getGenreId()); %>"><%out.print(g.getGenreName()); %></option>
       		<%} %>
       	</select>
       	</td>
       	<td>
       	<table class="table table-hover" id ="selectedGenreList">
			<tr><th>Genre Name: </th><th>Remove</th></tr>
			<tr id="copyMeRowGenre" style="display:none">
				<td>One</td>
				<td>
					<a href="#" genreId="">
						<span class="glyphicon glyphicon-remove" aria-hidden="true">
						</span>
					</a>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		<tr>
			<td>
			<h2>Select Publisher: </h2>
			<select id = "publisherDropdown" class="form-control"> 
       		<% for (Publisher p : publishers) {%>
       			<option value="<%out.print(p.getPublisherId()); %>"><%out.print(p.getPublisherName()); %></option>
       		<%} %>
       	</select>
       			</td>	
		</tr>

       	</table>
       	
      </div>
      <div class="modal-footer">
      	<span class = "bookErrorMsg" style="color:red"></span>
        <button type="button" class="btn btn-default closeModal" data-dismiss="modal" >Close</button>
        <button type="button" class="btn btn-primary addBook">Submit</button>
      </div>
    </div>
  </div>
</div>


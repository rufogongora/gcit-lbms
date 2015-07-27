      <!-- Modal -->

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
				<div class="form-group">
					<label for="searchBook">Search Book</label> <input type="text"
						class="form-control" id="searchBook"
						placeholder="Enter your author name ...">
				</div>

				<table class="table" id="bookTable">

			<tr>
				<th>Book ID</th>
				<th>Title</th>
				<th>Publisher</th>
				<th>Edit Book</th>
				<th>Delete Book</th>
			</tr>
			<tr id="cloneBookRow" style="display:none">
				<td>book id</td>
				<td><a tabindex="0" role="button" data-toggle="popover" data-html="true" data-trigger="focus" title="List of Books for: " data-content="a">title</a></td>
				<td>publisher</td>
				<td><button type="button" data-toggle="modal" data-target="#addBookModal" class="btn btn-md btn-success editBook" bookId="1" authors="0">Edit</button></td>
				<td><button type="button" class="btn btn-md btn-danger deleteBook" bookId = "1">Delete</button></td>
			</tr>
		</table>
				<nav>
					<ul class="pagination">
						<li id="insertAfterMeBook" class="originalStartBook"><a href="#"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
						<li id="copyMePaginationBook" style="display: none"><a href="#"
							pagNo="0">1</a></li>
						<li><a href="#" aria-label="Next"> <span
								aria-hidden="true">&raquo;</span>
						</a></li>
					</ul>
				</nav>


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
<%--        		<% for (Author a : authors) {%>
       			<option value="<%out.print(a.getAuthorId()); %>"><%out.print(a.getAuthorName()); %></option>
       		<%} %> --%>
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
<%--        		<% for (Genre g : genres) {%>
       			<option value="<%out.print(g.getGenreId()); %>"><%out.print(g.getGenreName()); %></option>
       		<%} %> --%>
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


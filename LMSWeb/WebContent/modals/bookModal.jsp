      <!-- Modal -->
<%@page import="com.gcit.lms.domain.Book"%>
<%

	List<Book> books = adminService.readBooks();
%>

<div class="modal fade" id="bookModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel2">Author Menu</h4>
      </div>
      <div class="modal-body">
		<table class="table" id="bookTable">

			<tr>
				<th>Book ID</th>
				<th>Title</th>
				<th>Publisher</th>
				<th>Edit Author</th>
				<th>Delete Author</th>
			</tr>
			<%for(Book b: books){ %>
			<tr class="authorRow">
				<td><%out.println(b.getBookId()); %></td>
				<td><%out.println(b.getTitle()); %></td>
				<td>Placeholder</td>
				<td><button type="button" class="btn btn-md btn-success">Edit</button></td>
				<td><button type="button" class="btn btn-md btn-danger deleteBook" bookId = "<%out.print(b.getBookId()); %>">Delete</button></td>
			</tr>
			<%} %>
		</table>
			
	
	
			<h2>Click to add a Book</h2>
			<p>
		        <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#addBookModal">Add</button>
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
        <h4 class="modal-title" id="addBookLabel">Modal title</h4>
      </div>
      <div class="modal-body">
      
      <table class="table">
      <tr>
       	<td>Book Title: <input type="text" name="authorName" id="bookTitleInput"/></td>       	</tr>
       	<tr>
       <td>	Book Author: 
       	
       	<select id = "authorDropdown"> 
       		<% for (Author a : authors) {%>
       			<option value="<%out.print(a.getAuthorId()); %>"><%out.print(a.getAuthorName()); %></option>
       		<%} %>
       	</select>
       	</td>
       	<table class="table table-hover" id ="selectedAuthorsList">
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
       	</table>
       	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary addBook">Submit</button>
      </div>
    </div>
  </div>
</div>


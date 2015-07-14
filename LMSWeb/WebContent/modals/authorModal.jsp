      <!-- Modal -->
<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@ page import="java.util.List" %>
<%@page import="com.gcit.lms.domain.Author"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%@page import="com.gcit.lms.domain.Genre"%>
<%@page import="com.gcit.lms.domain.Publisher"%>

<%AdministrativeService adminService = new AdministrativeService();
	List<Author> authors = adminService.readAuthors();
%>

<div class="modal fade" id="authorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Author Menu</h4>
      </div>
      <div class="modal-body">
				<div class="form-group">
					<label for="searchAuthor">Search Author</label> <input
						type="text" class="form-control" id="searchAuthor"
						placeholder="Enter your author name ...">
				</div>
				<table class="table" id="authorTable">

			<tr>
				<th>Author ID</th>
				<th>Author Name</th>
				<th>Edit Author</th>
				<th>Delete Author</th>
			</tr>
			<tr id="authorCloneMe" style="display:none">
				<td>authorId</td>
				<td authorId="0">
				<a tabindex="0" role="button" data-toggle="popover" data-html="true" data-trigger="focus" title="List of Books for: " data-content="a">
				authorName
				</a>
				</td>
				<td><button type="button" class="btn btn-md btn-success editAuthor" data-toggle="modal" data-target="#editAuthorModal"
				authorId = "0" authorName = "no">Edit</button></td>
				<td><button type="button" class="btn btn-md btn-danger deleteAuthor" authorId = "authorId">Delete</button></td>
			</tr>
			
		</table>
				<nav>
					<ul class="pagination">
						<li id="insertAfterMe" class="originalStart"><a href="#" aria-label="Previous"> <span
								aria-hidden="true">&laquo;</span>
						</a></li>
						<li id="copyMePagination" style="display:none"><a href="#" pagNo="0">1</a></li>
						<li><a href="#" aria-label="Next"> <span
								aria-hidden="true">&raquo;</span>
						</a></li>
					</ul>
				</nav>


				<h2>Hello Admin - Enter Author Details</h2>
			<p>
				Enter Author Name: <input type="text" name="authorName" id="authorNameInput"/>
		        <button type="submit" class="btn btn-primary addAuthor">Add</button>
			</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
<!--         <button type="submit" class="btn btn-primary">Save changes</button> -->
      </div>
    </div>
  </div>
</div>


<!--  Edit author sub-modal -->

<div class="modal fade" id="editAuthorModal" tabindex="-1" role="dialog" aria-labelledby="editAuthorLabel">  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body">
        <div class = "row">
        	 <div class="col-lg-6">
		    <div class="input-group">
		      <input type="text" id="newAuthorName" authorId="0" class="form-control" placeholder="Enter new name...">
		      <span class="input-group-btn">
		        <button class="btn btn-default" type="button" data-dismiss="modal" id="updateAuthor">Update</button>
		        <button class="btn btn-default" type="button" data-dismiss="modal" >Close</button>
		        
		      </span>
		    </div><!-- /input-group -->
		  </div><!-- /.col-lg-6 -->
        </div>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->






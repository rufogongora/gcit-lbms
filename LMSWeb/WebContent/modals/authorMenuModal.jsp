      <!-- Modal -->
<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Author"%>
<%AdministrativeService adminService = new AdministrativeService();
	List<Author> authors = adminService.readAuthors();
%>
<!-- <script>
	function deleteAuthor(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		
		document.getElementById("authorId").value = id;
		document.deleteFrm.submit();
	}

</script>  -->
   
    
<!--  <form action="/LMSWeb/addAuthor" method="post"> -->
<div class="modal fade" id="authorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Author Menu</h4>
      </div>
      <div class="modal-body">
		<table class="table">

			<tr>
				<th>Author ID</th>
				<th>Author Name</th>
				<th>Edit Author</th>
				<th>Delete Author</th>
			</tr>
			<%for(Author a: authors){ %>
			<tr >
				<td><%out.println(a.getAuthorId()); %></td>
				<td><%out.println(a.getAuthorName()); %></td>
				<td><button type="button" class="btn btn-md btn-success">Edit</button></td>
				<td><button type="button" class="btn btn-md btn-danger deleteAuthor" authorId = "<%out.print(a.getAuthorId()); %>">Delete</button></td>
			</tr>
			<%} %>
		</table>
			
	
	
			<h2>Hello Admin - Enter Author Details</h2>
			<p>
				Enter Author Name: <input type="text" name="authorName" id="authorNameInput"/>
		        <button type="submit" class="btn btn-primary addAuthor">Add</button>
			</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>
<!-- </form> -->



<!-- 
<form action="/LMSWeb/deleteAuthor" method="post" name="deleteFrm">
	<input type="hidden" name="authorId" id="authorId"/>
</form>
 -->


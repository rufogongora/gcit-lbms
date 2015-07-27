<div class="modal fade" id="publisherModal" tabindex="-1" role="dialog" aria-labelledby="publisherModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Publisher Menu</h4>
      </div>
      <div class="modal-body">
		<table class="table" id="publisherTable">

			<tr>
				<th>Publisher ID</th>
				<th>Publisher Name</th>
				<th>Publisher Address</th>
				<th>Publisher Phone</th>
				<th>Edit Publisher</th>
				<th>Delete Publisher</th>
			</tr>
			
			<tr id="publisherCloneMe" style="display:none">
				<td>publisherId</td>
				<td publisherId="0" pfield="name">publisherName</td>
				<td publisherId="0" pfield="address">publisherAddress</td>
				<td publisherId="0" pfield="phone">publihserPhone</td>
				<td><button type="button" class="btn btn-md btn-success editPublisher" data-toggle="modal" data-target="#editPublisherModal"
				publisherId = "0" publisherName = "no">Edit</button></td>
				<td><button type="button" class="btn btn-md btn-danger deletePublisher" publisherId = "publisherId">Delete</button></td>
			</tr>
			
<%-- 			<%for(Publisher g: publishers){ %>
			<tr >
				<td><%out.println(g.getPublisherId()); %></td>
				<td publisherId="<%out.print(g.getPublisherId()); %>" pfield="name"><%out.print(g.getPublisherName()); %></td>
				<td publisherId="<%out.print(g.getPublisherId()); %>" pfield="address"><%out.print(g.getPublisherAddress()); %></td>
				<td publisherId="<%out.print(g.getPublisherId()); %>" pfield="phone"><%out.print(g.getPublisherPhone()); %></td>
				<td><button type="button" class="btn btn-md btn-success editPublisher" data-toggle="modal" data-target="#editPublisherModal" 
				 publisherId = "<%out.print(g.getPublisherId()); %>" publisherName = "<%out.print(g.getPublisherName()); %>">Edit</button></td>
				<td><button type="button" class="btn btn-md btn-danger deletePublisher" publisherId = "<%out.print(g.getPublisherId()); %>">Delete</button></td>
			</tr>
			<%}; %> --%>
		</table>
				<nav>
					<ul class="pagination">
						<li id="insertAfterMePublisher" class="originalStartPublisher"><a href="#"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
						<li id="copyMePaginationPublisher" style="display: none"><a href="#"
							pagNo="0">1</a></li>
						<li><a href="#" aria-label="Next"> <span
								aria-hidden="true">&raquo;</span>
						</a></li>
					</ul>
				</nav>


				<h2>Hello Admin - Enter Publisher Details</h2>
			<p>
				Enter Publisher Name: <input type="text" class="form-control" name="publisherName" id="publisherNameInput"/>
				Enter Publisher Address:  <input type="text" class="form-control" name="publisherName" id="publisherAddressInput"/>
				Enter Publisher Phone <input type="text" class="form-control" name="publisherName" id="publisherPhoneInput"/>
		        <button type="submit" class="btn btn-primary addPublisher">Add</button>
			</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
<!--         <button type="submit" class="btn btn-primary">Save changes</button> -->
      </div>
    </div>
  </div>
</div>


<!--  Edit publisher sub-modal -->

<div class="modal fade" id="editPublisherModal" tabindex="-1" role="dialog" aria-labelledby="editPublisherLabel">  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body">
        <div class = "row">
        	 <div class="col-lg-6">
		    <div class="input-group">
		      <input type="text" id="newPublisherName" publisherId="0" class="form-control" placeholder="Enter new name...">

	          <input type="text" id="newPublisherAddress" class="form-control" placeholder="Enter new Address...">
	         	       	  <input type="text" id="newPublisherPhone"  class="form-control" placeholder="Enter new Phone...">
		      <span class="input-group-btn">
		        <button class="btn btn-default" type="button" data-dismiss="modal" id="updatePublisher">Update</button>
		        <button class="btn btn-default" type="button" data-dismiss="modal" >Close</button>
		        
		      </span>
		    </div><!-- /input-group -->
		  </div><!-- /.col-lg-6 -->
        </div>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

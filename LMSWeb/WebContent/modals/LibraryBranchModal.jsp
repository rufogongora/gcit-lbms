<!-- Modal -->
<div class="modal fade" id="editLibrary" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Edit Library</h4>
      </div>
      <div class="modal-body">
				<form>
					<div class="form-group">
						<label for="changeLibraryNameInput">Change Library Name</label> 
						<input type="text" class="form-control" id="changeLibraryNameInput" placeholder="0">
					</div>
					<div class="form-group">
						<label for="changeLibraryAddressInput">Address</label> <input
							type="text" class="form-control" id="changeLibraryAddressInput"
							placeholder="0">
					</div>
				</form>
			</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="commitChanges" >Save changes</button>
      </div>
    </div>
  </div>
</div>


<!-- Modal -->
<div class="modal fade" id="addCopiesOfBook" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Add copies of book</h4>
      </div>
      <div class="modal-body" >
		<table class="table" id="listOfBookCopies">
			<th>Book Id</th>
			<th>Book Title</th>
			<th>Number Of Copies</th>
			<th>Update</th>
			<tr id="bookCopyToClone" style="display:none">
				<td>1</td>
				<td>SomeBook</td>
				<td><form class="form-inline"><div class="form-group"><button type="button" class="btn btn-sm">-</button>
				<input type="number" class="form-control input-sm" currValue="50" placeholder="50">			
				<button type="button" class="btn btn-sm">+</button></div></form></td>
				<td><button type="button" class="btn btn-md btn-success updateNoOfCopies" bookId = "0">Update</button></td>
			</tr>
		</table>
		
	   </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" >Save changes</button>
      </div>
    </div>
  </div>
</div>
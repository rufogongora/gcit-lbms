<div class="modal fade" id="genreModal" tabindex="-1" role="dialog" aria-labelledby="genreModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Genre Menu</h4>
      </div>
      <div class="modal-body">

				<div class="form-group">
					<label for="searchGenre">Search Genre</label> <input type="text"
						class="form-control" id="searchGenre"
						placeholder="Enter your genre name ...">
				</div>


				<table class="table" id="genreTable">

			<tr>
				<th>Genre ID</th>
				<th>Genre Name</th>
				<th>Edit Genre</th>
				<th>Delete Genre</th>
			</tr>
			
			<tr id="genreCloneMe" style="display:none">
				<td>genreId</td>
				<td genreId="0">genreName</td>
				<td><button type="button" class="btn btn-md btn-success editGenre" data-toggle="modal" data-target="#editGenreModal"
				genreId = "0" genreName = "no">Edit</button></td>
				<td><button type="button" class="btn btn-md btn-danger deleteGenre" genreId = "genreId">Delete</button></td>
			</tr>
		
		</table>
		<nav>
			<ul class="pagination">
				<li id="insertAfterMeGenre" class="originalStartGenre"><a href="#" aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<li id="copyMePaginationGenre" style="display: none"><a href="#" pagNo="0">1</a></li>
				<li><a href="#" aria-label="Next"> <span
						aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</nav>
	
	
				<h2>Hello Admin - Enter Genre Details</h2>
			<p>
				Enter Genre Name: <input type="text" name="genreName" id="genreNameInput"/>
		        <button type="submit" class="btn btn-primary addGenre">Add</button>
			</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
<!--         <button type="submit" class="btn btn-primary">Save changes</button> -->
      </div>
    </div>
  </div>
</div>


<!--  Edit genre sub-modal -->

<div class="modal fade" id="editGenreModal" tabindex="-1" role="dialog" aria-labelledby="editGenreLabel">  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body">
        <div class = "row">
        	 <div class="col-lg-6">
		    <div class="input-group">
		      <input type="text" id="newGenreName" genreId="0" class="form-control" placeholder="Enter new name...">
		      <span class="input-group-btn">
		        <button class="btn btn-default" type="button" data-dismiss="modal" id="updateGenre">Update</button>
		        <button class="btn btn-default" type="button" data-dismiss="modal" >Close</button>
		        
		      </span>
		    </div><!-- /input-group -->
		  </div><!-- /.col-lg-6 -->
        </div>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

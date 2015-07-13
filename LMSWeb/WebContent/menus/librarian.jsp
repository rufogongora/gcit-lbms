
<jsp:include page="../Header.jsp"/>
 

<%@page import="com.gcit.lms.service.AdministrativeService"%>
<%@page import="java.util.List" %>
<%@page import="com.gcit.lms.domain.LibraryBranch" %>
<% AdministrativeService adminService = new AdministrativeService();
	List<LibraryBranch> branches = adminService.readLibraryBranches(); %>
    <!-- Marketing messaging and featurettes
    ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->
<div class="filler"></div>
<div class="container borrowerDropdown">
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8 "><h1>Select Library Branch</h1>
		<form class="form-inline">
			<div class="form-group">
				<select class="form-control" id="selectLibrary">
					<%
						for (LibraryBranch lb : branches) {
					%>
					<option value="<%out.print(lb.getBranchId());%>"><%out.print(lb.getBranchName());%></option>
					<%
						}
					%>
				</select>
				<button type="button" class="btn btn-primary" id="selectLibraryButton">Select Branch</button>
			</div>
			</form>
		</div>
		<div class="col-md-2"></div>
	</div>
</div>


<div class="container libraryMenu" style="display:none">
<div class="row">
<h1 id="selectedLibraryTitle">Selected Library: </h1>
</div>
<div class="row">
		<div class="col-lg-4">
			<a href="#" data-toggle="modal" data-target="#editLibrary" id="openEditLibrary"><img
				class="img-circle"
				src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
				alt="Generic placeholder image" width="140" height="140"> </a>
			<h2>Modify Library Details</h2>
		</div>
		<!-- /.col-lg-4 -->
		<div class="col-lg-4">
			<a href="#" data-toggle="modal" data-target="#addCopiesOfBook"> <img
				class="img-circle"
				src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
				alt="Generic placeholder image" width="140" height="140">
			</a>
			<h2>Add Copies of a book</h2>

		</div>
		<!-- /.col-lg-4 -->
		<div class="col-lg-4">
			<a href="#" id="goBackToLibrarian"> <img
				class="img-circle"
				src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
				alt="Generic placeholder image" width="140" height="140">
			</a>
			<h2>Go Back</h2>
		</div>
		<!-- /.col-lg-4 -->
	</div>
</div>
<jsp:include page="../modals/LibraryBranchModal.jsp"/>
<jsp:include page="../Footer.jsp"/>

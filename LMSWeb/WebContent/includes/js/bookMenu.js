var allBooks = []
var allBooksCopy = []
var currPageBook = 1
var allPublishers = []
var allPublishersCopy = []

$( document ).ready(function() {

	getAllBooks()
});


function getAllBooks(){
	$.get("/LMSWeb/getBooks", {pageNo : -1}).done(function(data){
		
		var books = JSON.parse(data);
		allBooks = books
		allBooksCopy = books
		spawnBooks(books, 1)
		spawnPaginationButtonsBook(allBooks)
	})
}

function spawnBooks(books, page){
	
	$(".cloneRowBook").remove()
	$(".originalStartBook").attr("id", "insertAfterMeBook")
	start = (page-1)*elementsPerPage;
	if(start>0){
		
		for(i = start; i < books.length && i < (start + elementsPerPage); i++){
			addBookToDOM(books[i])
		}
		
	}else{
		for(i = 0; i < elementsPerPage && i < books.length; i++){
			addBookToDOM(books[i])
		}
	}
	$('[data-toggle="popover"]').popover()	
}


function addBookToDOM(addedBook)
{
	
	var clone = $("#cloneBookRow").clone()
	clone.removeAttr("id")
	clone.addClass("cloneRowBook")
	clone.children().eq(0).text(addedBook.bookId)
	clone.children().eq(1).children().eq(0).text(addedBook.title)
	clone.children().eq(2).text(addedBook.publisher.publisherName)
	clone.children().eq(3).children().eq(0).attr("bookId", addedBook.bookId)
	clone.children().eq(3).children().eq(0).attr("authors", JSON.stringify(addedBook.authors))
	clone.children().eq(3).children().eq(0).attr("genres", JSON.stringify(addedBook.genres))
	clone.children().eq(4).children().eq(0).attr("bookId", addedBook.bookId)
	clone.children().eq(4).children().eq(0).bind("click", deleteBook)
	clone.children().eq(3).children().eq(0).bind("click", editBook)
	addPopoverInformationBook(addedBook, clone)
	clone.show()
	$("#bookTable").append(clone)

	//add the option to the selector
/*	var newOption = $("<option>")
	newOption.attr("value", author.authorId)
	newOption.text(author.authorName)
	$("#authorDropdown").append(newOption)

	$("#authorNameInput").val("")*/
}

function spawnPaginationButtonsBook(books){
	$(".paginationCloneBook").remove()
	for (j = 0; j < Math.ceil(books.length/elementsPerPage); j++){
		var clone = $("#copyMePaginationBook").clone()
		clone.addClass("paginationCloneBook")
		clone.removeAttr("id")
		clone.children().text(j+1)
		clone.children().attr("pagNo", j+1)
		clone.children().bind("click", changePageBook)
		$("#insertAfterMeBook").after(clone)
		$("#insertAfterMeBook").removeAttr("id")
		clone.attr("id", "insertAfterMeBook")
		clone.show()
	}
}

function changePageBook(){
	currPageBook = parseInt($(this).attr("pagNo"))
	changeToPageBook(currPageBook)
}

function changeToPageBook(pageNumber){
	if(pageNumber > Math.ceil(allBooks.length/elementsPerPage))
		pageNumber = Math.ceil(allBooks.length/elementsPerPage)
	spawnBooks(allBooks, pageNumber)
}

function addPopoverInformationBook(book, rowColumnClone){
	rowColumnClone.children().eq(1).children().eq(0).attr("title", "List of Authors for: " + book.title)
	booksString = "";
	for(j = 0; j < book.authors.length; j++){
		booksString += book.authors[j].authorName
		booksString += "<br>"
	}
	booksString += "<h4> List of Genre(s) </h4>"
	for (j = 0; j < book.genres.length; j++){
		booksString += book.genres[j].genreName
	}
	rowColumnClone.children().eq(1).children().eq(0).attr("data-content", booksString)
}

$("#searchBook").keyup(function(data){
	searchString = $("#searchBook").val()
	if (searchString)
	{
		allBooks = $.grep(allBooksCopy, function (e) {
			return e.title.toLowerCase().indexOf(searchString.toLowerCase()) == 0;
		});
	
		changeToPageBook(1)
		spawnPaginationButtonsBook(allBooks)
	}
	else{
		getAllBooks()
	}
})

var authorsToInsert = [];
var genresToInsert = [];
$(".closeModal").click(function(data){
	closeAddBookModal()
})

var status;

function deleteBook()
{
	var bn = $(this)
	var bookIdvar = $(this).attr("bookId")
	var jsonToSend =  {bookId : bookIdvar}
	$.post("/LMSWeb/deleteBook", jsonToSend).done(function(data){
		if (data){
			console.log(data)
			bn.parent().parent().hide()
		}
	})
}

function closeAddBookModal()
{
	restartAddingAuthors()
	restartAddingGenres()
	$("#bookTitleInput").val("")
	$("#bookTitleInput").removeAttr("bookId")
	$(".authorRowForList").remove()
	$(".genreRowForList").remove()
}


$(".addBook").click(


		function (data){
			if (status == "addBook"){
				//ADD A NEW BOOK
				if (!validateAddingBook())
					return

					var bookTitlevar = $("#bookTitleInput").val()
					var publisherIdvar = $("#publisherDropdown").val()
					var jsonToSend =  { 
						bookTitle : bookTitlevar,
						authors : JSON.stringify(authorsToInsert),
						genres : JSON.stringify(genresToInsert),
						publisherId : publisherIdvar}
				$.post("/LMSWeb/addBook", jsonToSend).done(function(data)
						{

					if (data)
					{

						$("#addBookModal").modal("toggle")
						$(".successMessageBook").show();
						$(".successMessageBook").text("Book added succesfully")
						$(".successMessageBook").fadeOut(2600, "linear")
						var addedBook = JSON.parse(data)
						allBooks.push(addedBook)
						changeToPageBook(Math.ceil(allBooks.length/elementsPerPage))
						spawnPaginationButtonsBook(allBooks)
						closeAddBookModal()
					}
				})
			}
			else
			{
				if (!validateAddingBook())
					return;
				
				var bookIdvar = $("#bookTitleInput").attr("bookId")
				var bookTitlevar = $("#bookTitleInput").val()
				var publisherIdvar = $("#publisherDropdown").val()
				var jsonToSend =  { bookTitle : bookTitlevar, 
					bookId : bookIdvar,
					authors : JSON.stringify(authorsToInsert),
					genres : JSON.stringify(genresToInsert),
					publisherId : publisherIdvar}
			$.post("/LMSWeb/editBook", jsonToSend).done(function(data)
					{

				if (data)
				{
					$("#addBookModal").modal("toggle")
					$(".successMessageBook").show();
					$(".successMessageBook").text("Book updated succesfully")
					$(".successMessageBook").fadeOut(2600, "linear")
					getAllBooks()
					changeToPageBook(currPageBook)
					spawnPaginationButtonsBook(allBooks)
					closeAddBookModal()
					
				}
					})
				
				
			}
			
		})


		
$("#authorDropdown").change(function(data){
	var author = { authorId : $("#authorDropdown").val(), authorName : $("#authorDropdown option:selected").text()}
	addAuthorToList(author)

})

$("#genreDropdown").change(function(data){
	var genre = { genreId : $("#genreDropdown").val(), genreName : $("#genreDropdown option:selected").text()}
	addGenreToList(genre)

})
$("#addBookButton").click(function(data){
		status = "addBook"
		$("#addBookLabel").text("Add Book")
})


$("#addBookModal").on('shown.bs.modal', function (e) {
	spawnAuthorsAndGenresOnModal()
})

function spawnAuthorsAndGenresOnModal(){
	for (j = 0; j < allAuthors.length; j++){
		var opt = $("<option>")
		opt.attr("value", allAuthors[j].authorId)
		opt.text(allAuthors[j].authorName)
		$("#authorDropdown").append(opt)
		opt.show()
	}
	for (j = 0; j < allGenres.length; j++){
		var opt = $("<option>")
		opt.attr("value", allGenres[j].genreId)
		opt.text(allGenres[j].genreName)
		$("#genreDropdown").append(opt)
		opt.show()
	}	
	
}

function editBook()
{
	status = "editBook"
	authorsToInsert = [];
	var bookName = $(this).parent().parent().children().eq(1).text();
	var bookId = parseInt($(this).attr("bookId"))
	$("#bookTitleInput").val(bookName)
	$("#bookTitleInput").attr("bookId", bookId)
	$("#addBookLabel").text("Edit book : " + bookName)
	var authors = JSON.parse($(this).attr("authors"))
	console.log(authors)
	for( i = 0; i < authors.length; i++){
		addAuthorToList(authors[i])
	}
	var genres = JSON.parse($(this).attr("genres"))
	for( i = 0; i < genres.length; i++){
		addGenreToList(genres[i])
	}
}

function checkIfInArray(val)
{
	for (i = 0; i < authorsToInsert.length; i++ )
	{
		if (authorsToInsert[i].authorId == val.authorId)
		{
			return true;
		}
	}
}

function checkIfInArrayGenre(val)
{
	for (i = 0; i < genresToInsert.length; i++ )
	{
		if (genresToInsert[i].genreId == val.genreId)
		{
			return true;
		}
	}
}

function deleteAuthorFromList()
{
	var id = $(this).attr("authorId")
	for (i = 0; i < authorsToInsert.length; i++)
	{

		if (parseInt(authorsToInsert[i].authorId) == id)
		{
			authorsToInsert.splice(i, 1)
			break;
		}
	}

	
	$(this).parent().parent().remove()	
}

function deleteGenreFromList()
{
	var id = $(this).attr("genreId")
	for (i = 0; i < genresToInsert.length; i++)
	{

		if (parseInt(genresToInsert[i].genreId) == id)
		{
			genresToInsert.splice(i, 1)
			break;
		}
	}

	$(this).parent().parent().remove()	
}


function restartAddingAuthors(){
	
	authorsToInsert = [];
	$(".authorRowForList").remove()
}

function restartAddingGenres(){
	
	genresToInsert = [];
	$(".genreRowForList").remove()
}

function validateAddingBook()
{
	if (!$("#bookTitleInput").val())
	{
		$(".bookErrorMsg").show()
		$(".bookErrorMsg").text("Error: Name should not be empty")
		$(".bookErrorMsg").fadeOut(2600, "linear")
		return false;
	}
	
	if (authorsToInsert.length == 0)
	{
		$(".bookErrorMsg").show()
		$(".bookErrorMsg").text("Error: Select at least one author")
		$(".bookErrorMsg").fadeOut(2600, "linear")
		return false;
	}
	if (genresToInsert.length == 0)
	{
		$(".bookErrorMsg").show()
		$(".bookErrorMsg").text("Error: Select at least one genre:")
		$(".bookErrorMsg").fadeOut(2600, "linear")
		return false;
	}
	return true
}

function addAuthorToList(author)
{
	//if not in array insert
	if (!checkIfInArray(author))
	{
		authorsToInsert.push(author)
		var copy = $("#copyMeRowAuthor").clone()
		copy.removeAttr("id")
		copy.attr("class", "authorRowForList")
		copy.children().eq(0).text(author.authorName)
		$("#selectedAuthorsList").append(copy)
		copy.children().eq(1).children().eq(0).attr("authorId", author.authorId)
		copy.children().eq(1).children().eq(0).bind("click", deleteAuthorFromList)
		copy.show()
	}
}

function addGenreToList(genre)
{
	//if not in array insert
	if (!checkIfInArrayGenre(genre))
	{
		genresToInsert.push(genre)
		var copy = $("#copyMeRowGenre").clone()
		copy.removeAttr("id")
		copy.attr("class", "genreRowForList")
		copy.children().eq(0).text(genre.genreName)
		$("#selectedGenreList").append(copy)
		copy.children().eq(1).children().eq(0).attr("genreId", genre.genreId)
		copy.children().eq(1).children().eq(0).bind("click", deleteGenreFromList)
		copy.show()
	}
}


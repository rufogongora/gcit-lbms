var authorsToInsert = [];
var genresToInsert = [];
$(".closeModal").click(function(data){
	closeAddBookModal()
})
$(".editBook").bind("click", editBook)
$(".deleteBook").bind("click", deleteBook)

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
						addedBook.authors = JSON.stringify(authorsToInsert)
						addedBook.genres = JSON.stringify(genresToInsert)
						addBookToDOM(addedBook)
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
					var addedBook = JSON.parse(data)
					$("button[bookId='" + bookIdvar +"']").parent().parent().children().eq(1).text(bookTitlevar)
					$("button[bookId='"+ bookIdvar +"']").parent().parent().children().eq(2).text(addedBook.publisherName)
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

function addBookToDOM(addedBook)
{
	var clone = $("#cloneBookRow").clone()
	clone.removeAttr("id")
	clone.children().eq(0).text(addedBook.bookId)
	clone.children().eq(1).text(addedBook.bookTitle)
	clone.children().eq(2).text(addedBook.publisherName)
	clone.children().eq(3).children().eq(0).attr("bookId", addedBook.bookId)
	clone.children().eq(3).children().eq(0).attr("authors", addedBook.authors)
	clone.children().eq(3).children().eq(0).attr("genres", addedBook.genres)
	clone.children().eq(4).children().eq(0).attr("bookId", addedBook.bookId)
	clone.children().eq(4).children().eq(0).bind("click", deleteBook)
	clone.children().eq(3).children().eq(0).bind("click", editBook)
	clone.show()
	$("#bookTable").append(clone)
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
		var copy = $("#cop	yMeRowGenre").clone()
		copy.removeAttr("id")
		copy.attr("class", "genreRowForList")
		copy.children().eq(0).text(genre.genreName)
		$("#selectedGenreList").append(copy)
		copy.children().eq(1).children().eq(0).attr("genreId", genre.genreId)
		copy.children().eq(1).children().eq(0).bind("click", deleteGenreFromList)
		copy.show()
	}
}


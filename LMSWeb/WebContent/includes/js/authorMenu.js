
var allAuthors = []
var allAuthorsCopy = []
var elementsPerPage = 5
var currPage = 1

$( document ).ready(function() {

	getAllAuthors()
});

function getAllAuthors(){
	$.get("/LMSWeb/getAuthors", {pageNo : -1}).done(function(data){
		
		var authors = JSON.parse(data);
		allAuthors = authors
		allAuthorsCopy = authors
		spawnAuthors(authors, 1)
		spawnPaginationButtons(allAuthors)
	})
}

$(function () {
  $('[data-toggle="popover"]').popover()
})
$("#updateAuthor").click(function(data){
	var authorIdvar = parseInt($("#newAuthorName").attr("authorid"));
	var authorNamevar = $("#newAuthorName").val();
	
	if (!authorNamevar)
		{
			return
		}
	
	var jsonToSend = {authorId : authorIdvar, authorName : authorNamevar}
	
	$.post("/LMSWeb/updateAuthor", jsonToSend).done(function(data)
			{
				var editedAuthor = JSON.parse(data)
				$("td[authorId='"+authorIdvar+"']").children().eq(0).text(editedAuthor.authorName)
				$("td[authorId='"+authorIdvar+"']").children().eq(0).attr("data-original-title", "List of Books for: " + editedAuthor.authorName)
				$("option[value='"+authorIdvar+"']").text(editedAuthor.authorName)
				updateJsonInBooks(authorIdvar, editedAuthor.authorName)
				
			})
	

})

function updateJsonInBooks(id, newName)
{
	var arrayOfButtons = $("button[authors]")

	for (i = 0; i < arrayOfButtons.length; i++){
		var currButton = $("button[authors]").eq(i)
		var jsonObj = JSON.parse(currButton.attr("authors"))
		
		

		for (j = 0; j < jsonObj.length; j++){
			if(jsonObj[j].authorId == id)
			{
				jsonObj[j].authorName = newName
				currButton.attr("authors", JSON.stringify(jsonObj))
				return
			}
		}	
	}
	
}





function deleteAuthor()
{
	var thisAuthor = $(this)
	var authorIdvar = parseInt($(this).attr("authorId"));

	var x =  { authorId : authorIdvar}
	$.post("/LMSWeb/deleteAuthor", x ).done(function (data){

		for(i=0;i< allAuthors.length; i++){
			if (allAuthors[i].authorId == x.authorId){
				allAuthors.splice(i,1)
				break;
			}
		}
		changeToPage(currPage)
		spawnPaginationButtons(allAuthors)

	});
}

function updateEditAuthorModal()
{
	var authorIdvar = parseInt($(this).attr("authorid"));
	$("#newAuthorName").attr("authorId", authorIdvar)
	
}




$(".addAuthor").click(
		function(data){
			var authorNameVar = $("#authorNameInput").val()

			if (!authorNameVar){
				return
			}
			
			var x =  { authorName : authorNameVar}

			$.post("/LMSWeb/addAuthor", x ).done(function (data){

				var author = JSON.parse(data)
				allAuthors.push(author)
				changeToPage(Math.ceil(allAuthors.length/elementsPerPage))
				spawnPaginationButtons(allAuthors)
				$("#authorNameInput").val("")
				
			}).fail(function(data){
				console.log("fail")
			});

		});

$("#searchAuthor").keyup(function(data){
	searchString = $("#searchAuthor").val()
	if (searchString)
	{
		allAuthors = $.grep(allAuthorsCopy, function (e) {
			return e.authorName.toLowerCase().indexOf(searchString.toLowerCase()) == 0;
		});
	
		console.log(allAuthors)
		changeToPage(1)
		spawnPaginationButtons(allAuthors)
	}
	else{
		getAllAuthors()
	}
})

function spawnAuthors(authors, page){
	
	$(".cloneRowAuthor").remove()
	$(".originalStart").attr("id", "insertAfterMe")
	start = (page-1)*elementsPerPage;
	if(start>0){
		
		for(i = start; i < authors.length && i < (start + elementsPerPage); i++){
			addAuthorToDOM(authors[i])
		}
		
	}else{
		for(i = 0; i < elementsPerPage && i < authors.length; i++){
			addAuthorToDOM(authors[i])
		}
	}


	$('[data-toggle="popover"]').popover()	
}

function addAuthorToDOM(author)
{
	
	var rowColumnClone = $("#authorCloneMe").clone()
	rowColumnClone.removeAttr("id")
	rowColumnClone.addClass("cloneRowAuthor")
	rowColumnClone.children().eq(0).text(author.authorId) //authorId
	rowColumnClone.children().eq(1).children().eq(0).text(author.authorName)
	addPopoverInformation(author, rowColumnClone)
	rowColumnClone.children().eq(1).attr("authorId",author.authorId	)
	rowColumnClone.children().eq(2).children().eq(0).attr("authorName",author.authorName)
	rowColumnClone.children().eq(2).children().eq(0).attr("authorId",author.authorId)
	rowColumnClone.children().eq(2).children().eq(0).bind("click", updateEditAuthorModal)
	rowColumnClone.children().eq(3).children().eq(0).attr("authorId",author.authorId)
	rowColumnClone.children().eq(3).children().eq(0).bind("click", deleteAuthor)
	$("#authorTable").append(rowColumnClone)
	rowColumnClone.show();

	//add the option to the selector
/*	var newOption = $("<option>")
	newOption.attr("value", author.authorId)
	newOption.text(author.authorName)
	$("#authorDropdown").append(newOption)

	$("#authorNameInput").val("")*/
}

function addPopoverInformation(author, rowColumnClone){
	rowColumnClone.children().eq(1).children().eq(0).attr("title", "List of Books for: " + author.authorName)
	booksString = "";
	for(j = 0; j < author.books.length; j++){
		booksString += author.books[j].title
		booksString += "<br>"
	}
	rowColumnClone.children().eq(1).children().eq(0).attr("data-content", booksString)
}
function spawnPaginationButtons(authors){
	$(".paginationClone").remove()
	for (j = 0; j < Math.ceil(authors.length/elementsPerPage); j++){
		var clone = $("#copyMePagination").clone()
		clone.addClass("paginationClone")
		clone.removeAttr("id")
		clone.children().text(j+1)
		clone.children().attr("pagNo", j+1)
		clone.children().bind("click", changePage)
		$("#insertAfterMe").after(clone)
		$("#insertAfterMe").removeAttr("id")
		clone.attr("id", "insertAfterMe")
		clone.show()
	}
}

function changePage(){
	currPage = parseInt($(this).attr("pagNo"))
	changeToPage(currPage)
}

function changeToPage(pageNumber){
	if(pageNumber > Math.ceil(allAuthors.length/elementsPerPage))
		pageNumber = Math.ceil(allAuthors.length/elementsPerPage)
	spawnAuthors(allAuthors, pageNumber)
}
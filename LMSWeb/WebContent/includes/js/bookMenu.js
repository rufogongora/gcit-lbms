
$(".addBook").click(
		function (data){
			var bookTitle = $("#bookTitleInput").val()
			var authorName = $("#selectedAuthor").val()

			console.log(bookTitle)
			console.log(authorName)
		})
		
var authorsToInsert = [];
		
$("#authorDropdown").change(function(data){
	var author = { authorId : $("#authorDropdown").val(), authorName : $("#authorDropdown option:selected").text()}
	
	//if not in array insert
	if (!checkIfInArray(author,authorsToInsert))
	{
		authorsToInsert.push(author)
		var copy = $("#copyMeRowAuthor").clone()
		copy.removeAttr("id")
		copy.children().eq(0).text(author.authorName)
		$("#selectedAuthorsList").append(copy)
		copy.children().eq(1).children().eq(0).attr("authorId", author.authorId)
		copy.children().eq(1).children().eq(0).bind("click", deleteAuthorFromList)
		copy.show()
	}
})


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

function deleteAuthorFromList()
{
	var id = $(this).attr("authorId")
	for (i = 0; i < authorsToInsert.length; i++)
	{

		if (parseInt(authorsToInsert[i].authorId) == id)
		{
			authorsToInsert.splice(i, 1)
			break
		}
	}

	
	$(this).parent().parent().remove()
	
}
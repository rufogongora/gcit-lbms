
$(".deleteAuthor").bind("click", deleteAuthor);
$(".editAuthor").bind("click", updateEditAuthorModal)


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
				$("td[authorId='"+authorIdvar+"']").text(editedAuthor.authorName)
				
				$("option[value='"+authorIdvar+"']").text(editedAuthor.authorName)

				
			})
	

})

function deleteAuthor()
{
	var thisAuthor = $(this)
	var authorIdvar = parseInt($(this).attr("authorId"));

	var x =  { authorId : authorIdvar}
	$.post("/LMSWeb/deleteAuthor", x ).done(function (data){
		thisAuthor.parent().parent().hide();
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
				//console.log(author[0].authorId)

				var rowColumnClone = $("#authorCloneMe").clone()
				rowColumnClone.removeAttr("id")
				rowColumnClone.children().eq(0).text(author.authorId) //authorId
				rowColumnClone.children().eq(1).text(authorNameVar)
				rowColumnClone.children().eq(1).attr("authorId",author.authorId	)
				rowColumnClone.children().eq(2).children().eq(0).attr("authorName",authorNameVar)
				rowColumnClone.children().eq(2).children().eq(0).attr("authorId",author.authorId)
				rowColumnClone.children().eq(2).children().eq(0).bind("click", updateEditAuthorModal)
				rowColumnClone.children().eq(3).children().eq(0).attr("authorId",author.authorId)
				rowColumnClone.children().eq(3).children().eq(0).bind("click", deleteAuthor)
				$("#authorTable").append(rowColumnClone)
				rowColumnClone.show();

				//add the option to the selector
				var newOption = $("<option>")
				newOption.attr("value", author.authorId)
				newOption.text(authorNameVar)
				$("#authorDropdown").append(newOption)

			}).fail(function(data){
				console.log("fail")
			});

		});


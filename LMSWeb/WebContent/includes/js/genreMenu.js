$(".addGenre").click(
		function(data){
	
			var genreNameVar = $("#genreNameInput").val()

			if (!genreNameVar){
				return
			}
			
			var x =  { genreName : genreNameVar}

			$.post("/LMSWeb/addGenre", x ).done(function (data){

				var genre = JSON.parse(data)
				var rowColumnClone = $("#genreCloneMe").clone()
				rowColumnClone.removeAttr("id")
				rowColumnClone.children().eq(0).text(genre.genreId) //authorId
				rowColumnClone.children().eq(1).text(genreNameVar)
				rowColumnClone.children().eq(1).attr("genreid",genre.genreId	)
				rowColumnClone.children().eq(2).children().eq(0).attr("genreName",genreNameVar)
				rowColumnClone.children().eq(2).children().eq(0).attr("genreid",genre.genreId)
				rowColumnClone.children().eq(2).children().eq(0).bind("click", updateEditGenreModal)
				rowColumnClone.children().eq(3).children().eq(0).attr("genreId",genre.genreId)
				rowColumnClone.children().eq(3).children().eq(0).bind("click", deleteGenre)
				$("#genreTable").append(rowColumnClone)
				rowColumnClone.show();

				//add the option to the selector
				var newOption = $("<option>")
				newOption.attr("value", genre.genreId)
				newOption.text(genreNameVar)
				$("#genreDropdown").append(newOption)

			$("#genreNameInput").val("")
				
			}).fail(function(data){
				console.log("fail")
			});

		});
$(".deleteGenre").bind("click", deleteGenre)
$(".editGenre").bind("click", updateEditGenreModal)
function updateEditGenreModal()
{
	var genreIdvar = parseInt($(this).attr("genreid"));
	$("#newGenreName").attr("genreid", genreIdvar)
	
}

function deleteGenre()
{
	var thisGenre = $(this)
	var genreIdvar = parseInt($(this).attr("genreid"));

	var x =  { genreId : genreIdvar}
	console.log(x.genreId)
	$.post("/LMSWeb/deleteGenre", x ).done(function (data){
		thisGenre.parent().parent().hide();
		deleteJsonInBooks(x.genreId)
		$("#genreDropdown option[value='"+x.genreId +"']").remove()

	});
}
$("#updateGenre").click(function(data){
	var genreIdvar = parseInt($("#newGenreName").attr("genreid"));
	var genreNamevar = $("#newGenreName").val();
	
	if (!genreNamevar)
		{
			return
		}
	
	var jsonToSend = {genreId : genreIdvar, genreName : genreNamevar}
	console.log(jsonToSend)
	$.post("/LMSWeb/updateGenre", jsonToSend).done(function(data)
			{
				var editedGenre = JSON.parse(data)
				console.log(editedGenre)
				$("td[genreid="+genreIdvar+"]").text(editedGenre.genreName)
				$("option[value="+genreIdvar+"]").text(editedGenre.genreName)
				updateJsonInBooksGenre(genreIdvar, editedGenre.genreName)
				
			})
	

})


function updateJsonInBooksGenre(id, newName)
{
	var arrayOfButtons = $("button[genres]")

	for (i = 0; i < arrayOfButtons.length; i++){
		var currButton = $("button[genres]").eq(i)
		var jsonObj = JSON.parse(currButton.attr("genres"))
		
		

		for (j = 0; j < jsonObj.length; j++){
			if(jsonObj[j].genreId == id)
			{
				jsonObj[j].genreName = newName
				currButton.attr("genres", JSON.stringify(jsonObj))
				return
			}
		}	

	}
}

function deleteJsonInBooksGenre(id)
{
	var arrayOfButtons = $("button[genre]")

	for (i = 0; i < arrayOfButtons.length; i++){
		var currButton = $("button[genre]").eq(i)
		var jsonObj = JSON.parse(currButton.attr("genres"))

		for (j = 0; j < jsonObj.length; j++){
			if(jsonObj[j].genreId == id)
			{
				jsonObj.splice(j, 1)
				currButton.attr("genre", JSON.stringify(jsonObj))
				return
			}
		}	

	}
	
}


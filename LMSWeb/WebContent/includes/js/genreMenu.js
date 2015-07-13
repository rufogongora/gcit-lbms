$(".addGenre").click(
		function(data){
	
			var genreNameVar = $("#genreNameInput").val()

			if (!genreNameVar){
				return
			}
			
			var x =  { genreName : genreNameVar}

			$.post("/LMSWeb/addGenre", x ).done(function (data){

				var genre = JSON.parse(data)
				//console.log(author[0].authorId)
				var rowColumnClone = $("#genreCloneMe").clone()
				rowColumnClone.removeAttr("id")
				rowColumnClone.children().eq(0).text(genre.genreId) //authorId
				rowColumnClone.children().eq(1).text(genreNameVar)
				rowColumnClone.children().eq(1).attr("authorId",genre.authorId	)
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

function updateEditGenreModal()
{
	var authorIdvar = parseInt($(this).attr("genreid"));
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
		//deleteJsonInBooks(authorIdvar)
		$("#genreDropdown option[value='"+x.genreId +"']").remove()

	});
}


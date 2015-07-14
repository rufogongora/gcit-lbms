var allGenres = []
var allGenresCopy = []
var currPageGenre = 1

$( document ).ready(function() {

	getAllGenres()
});

function getAllGenres(){
	$.get("/LMSWeb/getGenres", {pageNo : -1}).done(function(data){
		
		var genres = JSON.parse(data);
		allGenres = genres
		allGenresCopy = genres
		spawnGenres(genres, 1)
		spawnPaginationButtonsGenre(allGenres)
	})
}
function spawnPaginationButtonsGenre(genres){
	$(".paginationCloneGenre").remove()
	for (j = 0; j < Math.ceil(genres.length/elementsPerPage); j++){
		var clone = $("#copyMePaginationGenre").clone()
		clone.addClass("paginationCloneGenre")
		clone.removeAttr("id")
		clone.children().text(j+1)
		clone.children().attr("pagNo", j+1)
		clone.children().bind("click", changePageGenre)
		$("#insertAfterMeGenre").after(clone)
		$("#insertAfterMeGenre").removeAttr("id")
		clone.attr("id", "insertAfterMeGenre")
		clone.show()
	}
}


function spawnGenres(genres, page){
	
	$(".cloneRowGenre").remove()
	$(".originalStartGenre").attr("id", "insertAfterMeGenre")
	start = (page-1)*elementsPerPage;
	if(start>0){
		
		for(i = start; i < genres.length && i < (start + elementsPerPage); i++){
			addGenreToDOM(genres[i])
		}
		
	}else{
		for(i = 0; i < elementsPerPage && i < genres.length; i++){
			addGenreToDOM(genres[i])
		}
	}

}

function addGenreToDOM(genre)
{
	
	var rowColumnClone = $("#genreCloneMe").clone()
	rowColumnClone.removeAttr("id")
	rowColumnClone.addClass("cloneRowGenre")
	rowColumnClone.children().eq(0).text(genre.genreId) //authorId
	rowColumnClone.children().eq(1).text(genre.genreName)
	rowColumnClone.children().eq(1).attr("genreid",genre.genreId)
	rowColumnClone.children().eq(2).children().eq(0).attr("genreName",genre.genreName)
	rowColumnClone.children().eq(2).children().eq(0).attr("genreid",genre.genreId)
	rowColumnClone.children().eq(2).children().eq(0).bind("click", updateEditGenreModal)
	rowColumnClone.children().eq(3).children().eq(0).attr("genreId",genre.genreId)
	rowColumnClone.children().eq(3).children().eq(0).bind("click", deleteGenre)
	$("#genreTable").append(rowColumnClone)
	rowColumnClone.show();

	//add the option to the selector
/*	var newOption = $("<option>")
	newOption.attr("value", author.authorId)
	newOption.text(author.authorName)
	$("#authorDropdown").append(newOption)

	$("#authorNameInput").val("")*/
}


function changePageGenre(){
	currPageGenre = parseInt($(this).attr("pagNo"))
	changeToPageGenre(currPageGenre)
}

function changeToPageGenre(pageNumber){
	if(pageNumber > Math.ceil(allGenres.length/elementsPerPage))
		pageNumber = Math.ceil(allGenres.length/elementsPerPage)
	spawnGenres(allGenres, pageNumber)
}

$("#searchGenre").keyup(function(data){
	searchString = $("#searchGenre").val()
	if (searchString)
	{
		allGenres = $.grep(allGenresCopy, function (e) {
			return e.genreName.toLowerCase().indexOf(searchString.toLowerCase()) == 0;
		});
	
		changeToPageGenre(1)
		spawnPaginationButtonsGenre(allGenres)
	}
	else{
		getAllGenres()
	}
})

$(".addGenre").click(function(data){
	var genreNameVar = $("#genreNameInput").val()

	if (!genreNameVar){
		return
	}
	
	var x =  { genreName : genreNameVar}

	$.post("/LMSWeb/addGenre", x ).done(function (data){

		var genre = JSON.parse(data)
		allGenres.push(genre)
		changeToPageGenre(Math.ceil(allGenres.length/elementsPerPage))
		spawnPaginationButtonsGenre(allGenres)
		$("#genreNameInput").val("")
		
	}).fail(function(data){
		console.log("fail")
	});
	
})

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
	$.post("/LMSWeb/deleteGenre", x ).done(function (data){

		for(j= 0; j < allGenres.length; j++){
			console.log(x.genreId)
			console.log(allGenres[j].genreId)
			if (x.genreId == allGenres[j].genreId){
				allGenres.splice(j,1)
				break
			}
		
		}
		changeToPageGenre(currPageGenre)
		spawnPaginationButtonsGenre(allGenres)
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
				
				for (i = 0; i < allGenres.length; i++){
					if (editedGenre.genreId == allGenres[i].genreId)
					{
						allGenres[i].genreName = editedGenre.genreName
						break
					}
				}
				changeToPageGenre(currPageGenre)
				
			})
	

})





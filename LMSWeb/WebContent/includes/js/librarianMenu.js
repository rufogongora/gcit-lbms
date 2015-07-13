var selectedLibrary;

$("#selectLibraryButton").click(function(data){
	selectedLibrary = $("#selectLibrary option:selected").attr("value")
	
	$(".borrowerDropdown").fadeOut(1000, function(){
		$(".libraryMenu").fadeIn(1000)
	})
	
		var jsonToSend = {libraryBranchId : selectedLibrary}
	
	$.get("/LMSWeb/getLibrary", jsonToSend).done(function(data)
			{
				selectedLibrary = JSON.parse(data)
				console.log(selectedLibrary)
				updateLibraryInDom()
				
			})
	
})


$("#goBackToLibrarian").click(function(data){
	selectedLibrary = null
	
	
	$(".libraryMenu").fadeOut(1000, function(){
		$(".borrowerDropdown").fadeIn(1000)
	})
})


$("#openEditLibrary").click(function(data){
	updateDOM()
})

$("#commitChanges").click(function(data){
	
	selectedLibrary.libraryBranchName = $("#changeLibraryNameInput").val()
	selectedLibrary.libraryBranchAddress = $("#changeLibraryAddressInput").val()
	
	$.post("/LMSWeb/updateLibrary", selectedLibrary).done(function(data)
	{
		console.log(data)
		updateLibraryInDom()
	})
})

function updateDOM(){
	$("#changeLibraryNameInput").val(selectedLibrary.libraryBranchName)
	$("#changeLibraryAddressInput").val(selectedLibrary.libraryBranchAddress)
}

function updateLibraryInDom(){
	$("option[value="+selectedLibrary.libraryBranchId+"]").text(selectedLibrary.libraryBranchName)
	$("#selectedLibraryTitle").text("Selected Library: " + selectedLibrary.libraryBranchName)
	spawnBookCopies()
	
}

function spawnBookCopies(){
	
	for (i = 0; i < selectedLibrary.books.length; i++){
		console.log("hi")
		var bookCopy = selectedLibrary.books[i];
		var clone = $("#bookCopyToClone").clone()
		clone.removeAttr("id")
		clone.children().eq(0).text(bookCopy.bookId)
		clone.children().eq(1).text(bookCopy.bookTitle)
		clone.children().eq(2).children().eq(0).children().eq(0).children().eq(1).val(bookCopy.noOfCopies)
		clone.children().eq(2).children().eq(0).children().eq(0).children().eq(1).attr("currValue", bookCopy.noOfCopies)
		clone.children().eq(2).children().eq(0).children().eq(0).children().eq(1).attr("placeholder", bookCopy.noOfCopies)
		clone.children().eq(2).children().eq(0).children().eq(0).children().eq(1).bind("change", checkIfDifferentValue)
		clone.children().eq(2).children().eq(0).children().eq(0).children().eq(1).bind("keyup", checkIfDifferentValue)
		clone.children().eq(2).children().eq(0).children().eq(0).children().eq(1).bind("paste", checkIfDifferentValue)
		clone.children().eq(2).children().eq(0).children().eq(0).children().eq(2).bind("click", plusButton)
		clone.children().eq(2).children().eq(0).children().eq(0).children().eq(0).bind("click", minusButton)
		clone.children().eq(3).children().eq(0).attr("bookId", bookCopy.bookId)
		clone.children().eq(3).children().eq(0).bind("click", submitChanges)
		clone.show()
		$("#listOfBookCopies").append(clone)
		
	}

}

function submitChanges(){
	var bttn = $(this)
	if (bttn.hasClass("btn-success"))
		return
	
	var jsonToSend = { branchId : selectedLibrary.libraryBranchId, bookId : $(this).attr("bookid"), noOfCopies : $(this).attr("noOfCopies")}
	console.log(jsonToSend)
	$.post("/LMSWeb/updateNoOfCopies", jsonToSend).done(function(data)
			{
				if (data)
				{
					bttn.removeClass("btn-danger").addClass("btn-success")
					bttn.parent().parent().children().eq(2).children().eq(0).children().eq(0).children().eq(1).attr("currValue", bttn.attr("noOfCopies"))
					bttn.parent().parent().children().eq(2).children().eq(0).children().eq(0).children().eq(1).attr("placeHolder", bttn.attr("noOfCopies"))
					
				}
			})
}

function plusButton(){
	var x = parseInt($(this).parent().children().eq(1).val())
	x+=1;
	$(this).parent().children().eq(1).val(x)
	$(this).parent().children().eq(1).change()
}
function minusButton(){
	var x = parseInt($(this).parent().children().eq(1).val())
	if(x>1)
		x-=1;
	$(this).parent().children().eq(1).val(x)
	$(this).parent().children().eq(1).change()
}

function checkIfDifferentValue()
{
	if($(this).val() != $(this).attr("currValue"))
	{
		$(this).parent().parent().parent().parent().children().eq(3).children().eq(0).removeClass("btn-success").addClass("btn-danger")
		$(this).parent().parent().parent().parent().children().eq(3).children().eq(0).attr("noOfCopies", $(this).val())
	}
	else{
		$(this).parent().parent().parent().parent().children().eq(3).children().eq(0).removeClass("btn-danger").addClass("btn-success")

	}
}


$(".addPublisher").click(
		function(data){
	
			var publisherNameVar = $("#publisherNameInput").val()
			var publisherAddressVar = $("#publisherAddressInput").val()
			var publisherPhoneVar = $("#publisherPhoneInput").val()
			if (!publisherNameVar || !publisherAddressVar || !publisherPhoneVar){
				return
			}
			

			var x =  { publisherName : publisherNameVar,
						publisherAddress : publisherAddressVar,
						publisherPhone : publisherPhoneVar}

			$.post("/LMSWeb/addPublisher", x).done(function (data){
				console.log(data)
				var publisher = JSON.parse(data)
				addPublisherToDOM(publisher)
			}).fail(function(data){
				console.log("fail")
			});

		});
$(".deletePublisher").bind("click", deletePublisher)
$(".editPublisher").bind("click", updateEditPublisherModal)
function updateEditPublisherModal()
{
	var publisherIdvar = parseInt($(this).attr("publisherid"));
	$("#newPublisherName").attr("publisherid", publisherIdvar)
	
}

function deletePublisher()
{
	var thisPublisher = $(this)
	var publisherIdvar = parseInt($(this).attr("publisherid"));

	var x =  { publisherId : publisherIdvar}
	$.post("/LMSWeb/deletePublisher", x ).done(function (data){
		thisPublisher.parent().parent().hide();
		$("#publisherDropdown option[value='"+x.publisherId +"']").remove()

	});
}
$("#updatePublisher").click(function(data){
	var publisherIdvar = parseInt($("#newPublisherName").attr("publisherid"));
	var publisherNamevar = $("#newPublisherName").val();
	var publisherAddressvar =  $("#newPublisherAddress").val();
	var publisherPhonevar =  $("#newPublisherPhone").val();
	if (!publisherNamevar || !publisherAddressvar || !publisherPhonevar)
		{
			return
		}
	
	var jsonToSend = {publisherId : publisherIdvar, publisherName : publisherNamevar, publisherAddress: publisherAddressvar, publisherPhone : publisherPhonevar}
	$.post("/LMSWeb/updatePublisher", jsonToSend).done(function(data)
			{
				var editedPublisher = JSON.parse(data)
				$("td[publisherid="+publisherIdvar+"]").text(editedPublisher.publisherName)
				$("td[publisherid="+publisherIdvar+"][pfield='address']").text(editedPublisher.publisherAddress)
				$("td[publisherid="+publisherIdvar+"][pfield='phone']").text(editedPublisher.publisherPhone)
				$("option[value="+publisherIdvar+"]").text(editedPublisher.publisherName)
			})
	

})




function addPublisherToDOM(publisher){
	
	//console.log(author[0].authorId)
	var rowColumnClone = $("#publisherCloneMe").clone()
	rowColumnClone.removeAttr("id")
	rowColumnClone.children().eq(0).text(publisher.publisherId) //authorId
	rowColumnClone.children().eq(1).text(publisher.publisherName)
	rowColumnClone.children().eq(1).attr("publisherid",publisher.publisherId)
	rowColumnClone.children().eq(2).text(publisher.publisherAddress)
	rowColumnClone.children().eq(2).attr("publisherid",publisher.publisherId)
	rowColumnClone.children().eq(3).text(publisher.publisherPhone)
	rowColumnClone.children().eq(3).attr("publisherid",publisher.publisherId)
	
	rowColumnClone.children().eq(4).children().eq(0).attr("publisherName",publisher.publisherName)
	rowColumnClone.children().eq(4).children().eq(0).attr("publisherid",publisher.publisherId)
	rowColumnClone.children().eq(4).children().eq(0).bind("click", updateEditPublisherModal)
	rowColumnClone.children().eq(5).children().eq(0).attr("publisherId",publisher.publisherId)
	rowColumnClone.children().eq(5).children().eq(0).bind("click", deletePublisher)
	$("#publisherTable").append(rowColumnClone)
	rowColumnClone.show();

	//add the option to the selector
	var newOption = $("<option>")
	newOption.attr("value", publisher.publisherId)
	newOption.text(publisher.publisherName)
	$("#publisherDropdown").append(newOption)

	$("#publisherNameInput").val("")
	
}


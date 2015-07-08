
$(".deleteAuthor").click(
		function(data){
			
			var thisAuthor = $(this)
			
			var authorIdvar = parseInt($(this).attr("authorId"));
			//console.log(authorIdvar)
			
			var x =  { authorId : authorIdvar}
			console.log(x)
			$.post("/LMSWeb/deleteAuthor", x ).done(function (data){
				thisAuthor.parent().parent().hide();
			});

		});


$(".addAuthor").click(
		function(data){
			
			var authorNameVar = $("#authorNameInput").val()
			
			var x =  { authorName : authorNameVar}
			
			$.post("/LMSWeb/addAuthor", x ).done(function (data){
				var row = $("<tr>").append("<td>").val()
				
				
				
				/*<tr >
				<td><%out.println(a.getAuthorId()); %></td>
				<td><%out.println(a.getAuthorName()); %></td>
				<td><button type="button" class="btn btn-md btn-success">Edit</button></td>
				<td><button type="button" class="btn btn-md btn-danger deleteAuthor" authorId = "<%out.print(a.getAuthorId()); %>">Delete</button></td>
			</tr>*/
			});
			
			
		});

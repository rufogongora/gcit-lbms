		
libraryModule.controller('bookCtrl', function($scope, $http, $cookieStore, $modal, $log) {
	$scope.books = []
	$scope.currentPage = 1
	$scope.numPerPage = 5
	$scope.maxSize = 5
	$scope.numberOfBooks = 0
	$scope.searchBookQuery = ""
	$scope.alerts = []
	$scope.selectedSearchChoice = {"id" : "title"}
	
	$scope.searchChoices = [{"id": "title", "name" : "Book Title"}, {"id" : "authorName", "name" : "Author Name"}, {"id" : "genre", "name" : "Genre"}, {"id": "publisher", "name" : "Publisher"}]

	$scope.getBooks = function getBooks()
	{
		//get N books
		$http.get('http://localhost:8080/lms/book/get/'+$scope.currentPage +'/'+$scope.numPerPage +'/'+ $scope.selectedSearchChoice.id+'/'+$scope.searchBookQuery)
		.success(
				function(data){
					$scope.books = data;					
				})
				$http.get('http://localhost:8080/lms/book/getCount/'+ $scope.selectedSearchChoice.id+'/' +$scope.searchBookQuery)
				.success(
						function(data){
							$scope.numberOfBooks = data;
							if (data <= 0 ){
								$scope.searchAlert = {msg : "No Books where found!", type : "danger"}
							}
							else{
								$scope.searchAlert = null
							}
						})
	}
	$scope.getBooks();


	$scope.addBook = function addBook() {
		/*if($scope.addBookFrm.$valid){*/

		$http.post('http://localhost:8080/lms/book/add/', $scope.newBook).
		success(function(data) {
			$scope.getBooks()

			$scope.alerts.push({type: 'success', msg: 'Book ' + $scope.newBook.name + ' added!'});
			$scope.newBook = {}
		});

		//}
	};


	$scope.deleteBook = function deleteBook(book){
		console.log(book)
		$http.post('http://localhost:8080/lms/book/delete', book).success(function(data)	
				{

			$scope.getBooks()
				})

	}

	$scope.searchBook = function searchBook(){

	}

	//popover stuff
	$scope.dynamicPopover = {
			templateUrl: 'myPopoverTemplate.html',

	};




	$scope.pageChanged = function() {
		$scope.getBooks()
	};


	$scope.$watch("currentPage + numPerPage",  $scope.getBooks);



	$scope.editing = false;
	$scope.openAddBook = function(size, bookToEdit){
		if (bookToEdit != null)
			$scope.bookCopy = JSON.parse(JSON.stringify(bookToEdit))
		var modalInstance = $modal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'AddBookModalCtrl.html',
			controller: 'AddBookModalCtrl',
			size: size,
			resolve: {
				bookToEdit: function () {
					return bookToEdit;
				},
				editing : function(){
					if (bookToEdit == null)
					{
						$scope.editing = false;
						return $scope.editing
					}
					else{
						$scope.editing = true;
						return $scope.editing
					}
					
				}

			}});

		modalInstance.result.then(function (bookToAdd) {
			if (!$scope.editing){
			$scope.bookToAdd = bookToAdd;
			$http.post('http://localhost:8080/lms/book/add/', $scope.bookToAdd).
			success(function(data) {
				$scope.getBooks()
			});
			}else{

			$http.post('http://localhost:8080/lms/book/update/', bookToAdd).
				success(function(data) {
					$scope.getBooks()
				});
			}

		}, function () {
			bookToEdit = $scope.bookCopy
			$scope.getBooks()
		});


	}

	$scope.animationsEnabled = true;



	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

	
	


});



libraryModule.controller('AddBookModalCtrl', function ($scope, $modalInstance, $http, bookToEdit, editing) {
	
	$scope.editing = editing;
	if (!editing)
	{
		$scope.newBook = {'authors' : [], 'genres' : []}
		$scope.msg = "Add"
	}
	else
	{
		$scope.newBook = bookToEdit
		$scope.msg = "Edit"

	}
		
	
	$http.get('http://localhost:8080/lms/author/get/0/-1/')
	.success(
			function(data){
				$scope.authors = data;					
			})
			
	 $http.get('http://localhost:8080/lms/genre/get/0/-1/')
	.success(
			function(data){
				$scope.genres = data;					
			})
			
	 $http.get('http://localhost:8080/lms/publisher/get/0/-1/name')
	.success(
			function(data){
				$scope.publishers = data;	
				if ($scope.editing)
				{
					for (i=0; i < $scope.publishers.length; i++){
						if ($scope.newBook.publisher.id == $scope.publishers[i].id)
							$scope.newBook.publisher = $scope.publishers[i]
					}
				}
			})
	
	$scope.addAuthorToList = function(author)
	{
		for (i=0; i < $scope.newBook.authors.length; i++)
		{
			if($scope.newBook.authors[i].authorId == author.authorId)
				return
		}
		$scope.newBook.authors.push(author)
	}
	
	$scope.deleteAuthorFromList = function(author)
	{
		for (i=0; i < $scope.newBook.authors.length; i++)
		{
			if($scope.newBook.authors[i].authorId == author.authorId)
				{
				$scope.newBook.authors.splice(i, 1)
				return
				}
		}
	}
	
	$scope.addGenreToList = function(genre)
	{
		for (i=0; i < $scope.newBook.genres.length; i++)
		{
			if($scope.newBook.genres[i].genreId == genre.genreId)
				return
		}
		$scope.newBook.genres.push(genre)
	}
	
	$scope.deleteGenreFromList = function(genre)
	{
		for (i=0; i < $scope.newBook.genres.length; i++)
		{
			if($scope.newBook.genres[i].genreId == genre.genreId)
				{
					$scope.newBook.genres.splice(i, 1)
					return
				}
		}
	}
			
	
	function validate(){
		if (!$scope.newBook)
			return false
		if (!$scope.newBook.title)
			return false
		if (!$scope.newBook.authors || $scope.newBook.authors.length <= 0)
			return false
		if (!$scope.newBook.genres|| $scope.newBook.genres.length <= 0)
			return false
		if (!$scope.newBook.publisher)
			return false
		return true
						
			
	}
	
	
	$scope.ok = function () {
		
		if (!validate())
			return
		
		$modalInstance.close($scope.newBook, $scope.editing);
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});

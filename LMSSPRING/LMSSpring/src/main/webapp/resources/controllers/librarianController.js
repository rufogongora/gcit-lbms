libraryModule.controller('librarianCtrl', function($scope, $http, $cookieStore, $modal, $log) {
	
	$scope.changed = false;
	
	$scope.getLibraryBranchs = function getLibraryBranchs()
	{
		//get N libraryBranchs
		$http.get('http://localhost:8080/lms/libraryBranch/get/0/0/branchName')
		.success(
				function(data){
					$scope.libraries = data;
				})
	}
	$scope.getLibraryBranchs();
	
	
	$scope.updateLibrary = function updateLibrary(){
		$http.post('http://localhost:8080/lms/libraryBranch/update', $scope.selectedLibrary)
		.success(function (data){
			$scope.changed = false;
		
		})
	}
	
	$scope.getBooks = function getBooks(){
		$http.get('http://localhost:8080/lms/book/get/0/-1/title')
		.success(function(data){
			$scope.books = data
		})
	}
	$scope.getBooks()
	
	$scope.addCopiesOfBook = function addCopiesOfBook(){
		if (!$scope.copyToAdd)
			return
		if (!$scope.copyToAdd.book)
			return
		if (!$scope.copyToAdd.noOfCopies)
			return
		if (isNaN($scope.copyToAdd.noOfCopies))
			return
		if (!$scope.selectedLibrary.bookCopies)
			$scope.selectedLibrary.bookCopies = []
		
		
		for(i = 0; i < $scope.selectedLibrary.bookCopies.length; i ++){
			if ($scope.copyToAdd.book.bookId == $scope.selectedLibrary.bookCopies[i].book.bookId)
				return
		}
		var newBook = JSON.parse(JSON.stringify($scope.copyToAdd))
		$scope.selectedLibrary.bookCopies.push(newBook)
		$scope.changed = true;
		
	}
	
	$scope.changeValue = function changeValue(bookCopy, v){
			for (i=0; i < $scope.selectedLibrary.bookCopies.length; i++){
				if (bookCopy.bookId == $scope.selectedLibrary.bookCopies[i].book.bookId)
					{
						if (v != '-')
							$scope.selectedLibrary.bookCopies[i].noOfCopies++;
						else
							$scope.selectedLibrary.bookCopies[i].noOfCopies--;
						break
					}

					
			}
			
			$scope.changed = true;

		
	}
	
})
libraryModule.controller('borrowerCtrl', function($scope, $http, $cookieStore, $modal, $log) {
	

	$scope.returnBook = function returnBook(bookLoan){
		for (i=0; i < $scope.user.bookLoans.length; i ++){
			if ($scope.user.bookLoans[i].book.bookId == bookLoan.book.bookId)
			{
				$scope.user.bookLoans.splice(i, 1)
				break;
			}
		}
		
		

		$http.post('http://localhost:8080/lms/borrower/update', $scope.user)
		.success(function (data){
			
		})
		
	}
	
	
	$scope.go = function go(){
		$http.post('http://localhost:8080/lms/borrower/login', $scope.cardNo)
		.success( function(data){
			$scope.user = data;
		})
	}
	
	
	$scope.getLibraryBranchs = function getLibraryBranchs()
	{
		//get N libraryBranchs
		$http.get('http://localhost:8080/lms/libraryBranch/get/0/0/branchName')
		.success(
				function(data){
					$scope.libraries = data;
					console.log(data)
				})
	}
	$scope.getLibraryBranchs();
	
	
	$scope.checkOut = function checkOut(){
		
		for (i=0; i < $scope.user.bookLoans.length; i++){
			if ($scope.user.bookLoans[i].book.bookId == $scope.bookCopy.book.bookId)
				return
		}
		
		$scope.bookLoan.book = $scope.bookCopy.book
		
		for (i = 0; i < $scope.bookLoan.libraryBranch.bookCopies.length; i++){
			console.log($scope.bookLoan.libraryBranch.bookCopies[i].book.bookId)
			console.log($scope.bookLoan.book.bookId)
			if ($scope.bookLoan.libraryBranch.bookCopies[i].book.bookId == $scope.bookLoan.book.bookId){
				$scope.bookLoan.libraryBranch.bookCopies[i].noOfCopies--;
				break;
			}
		}
		
		$scope.bookLoan.dateOut = new Date();
		$scope.bookLoan.dateDue = new Date($scope.bookLoan.dateOut)
		$scope.bookLoan.dateDue.setDate($scope.bookLoan.dateOut.getDate()+7)
		
		$scope.bookLoan.borrower = $scope.user
		
		
		$http.post('http://localhost:8080/lms/libraryBranch/update', $scope.bookLoan.libraryBranch).success(function(data){
			$scope.bookLoan.libraryBranch.bookCopies = null
			
			$http.post('http://localhost:8080/lms/borrower/checkOut', $scope.bookLoan)
			.success(function(data){
				$scope.go()
				$scope.getLibraryBranchs()
				$scope.bookLoan = {}
			})
		})
		

		
		
	}
	
})
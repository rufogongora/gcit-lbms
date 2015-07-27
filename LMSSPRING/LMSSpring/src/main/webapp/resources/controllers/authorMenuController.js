
libraryModule.controller('authorCtrl', function($scope, $http, $cookieStore, $modal, $log) {
	$scope.authors =[]
	$scope.currentPage = 1
	$scope.numPerPage = 5
	$scope.maxSize = 5
	$scope.numberOfAuthors = 0
	$scope.searchAuthorQuery = ""
	$scope.alerts = []
	
	$scope.getAuthors = function getAuthors()
	{
		//get N authors
		$http.get('http://localhost:8080/lms/author/get/'+$scope.currentPage +'/'+$scope.numPerPage +'/'+$scope.searchAuthorQuery)
		.success(
				function(data){
					$scope.authors = data;					
				})
		$http.get('http://localhost:8080/lms/author/getCount/' +$scope.searchAuthorQuery)
		.success(
				function(data){
					$scope.numberOfAuthors = data;
				})
	}
	$scope.getAuthors();
	
	$scope.addAuthor = function addAuthor() {
		if (!$scope.newAuthor)
			return
			/*if($scope.addAuthorFrm.$valid){*/
		 $http.post('http://localhost:8080/lms/author/add/', $scope.newAuthor).
		  success(function(data) {
			 //alert('Author Added');
			 $scope.authors.push(data)
			 $scope.getAuthors()

			 
			 $scope.alerts.push({type: 'success', msg: 'Author ' + $scope.newAuthor.authorName + ' added!'});
			 $scope.newAuthor = {}
		    });
		    
		//}
	};
	
	
	$scope.deleteAuthor = function deleteAuthor(author){
		console.log(author)
		$http.post('http://localhost:8080/lms/author/delete', author).success(function(data)	
		{
			//$scope.authors.splice()
			for (i = 0; i < $scope.authors.length; i++){
				if ($scope.authors[i].authorId == author.authorId)
					{
						$scope.authors.splice(i, 1)
						break;
					}
				
			}
			 $scope.getAuthors()
		})
		
	}
	
	$scope.searchAuthor = function searchAuthor(){
		
	}
	
	//popover stuff
	  $scope.dynamicPopover = {
			  	templateUrl: 'myPopoverTemplate.html',

			  };
	  
	  
	  
	  
	  $scope.pageChanged = function() {
		  $scope.getAuthors()
		  };
	  

	  $scope.$watch("currentPage + numPerPage",  $scope.getAuthors);
	  
	  

	  $scope.editAuthor = function(author, size){
		   $scope.authorCopy = JSON.parse(JSON.stringify(author));
		   $scope.open(author, size)
	  }
	  
	  $scope.animationsEnabled = true;
	  
	  
	  
	  $scope.open = function (author, size) {

	    var modalInstance = $modal.open({
	      animation: $scope.animationsEnabled,
	      templateUrl: 'myModalContent.html',
	      controller: 'ModalInstanceCtrl',
	      size: size,
	      resolve: {
	        authorToEdit: function () {
	          return author;
	        }
	    
	      }
	    });

	    modalInstance.result.then(function (authorToEdit) {
	      $scope.authorToEdit = authorToEdit;
	      
		 $http.post('http://localhost:8080/lms/author/update/', $scope.authorToEdit).
		  success(function(data) {
			 console.log($scope.authors)
			  $scope.getAuthors()
		    });
			    
	      
	    }, function () {
	    	for (i = 0; i < $scope.authors.length; i++){
	    		if ($scope.authors[i].authorId == $scope.authorCopy.authorId)
    			{
	    				$scope.authors[i] = $scope.authorCopy
	    				break;
    			}
	    	}
	    	 $scope.getAuthors()
	    	
	    });
	  };

	  $scope.closeAlert = function(index) {
		    $scope.alerts.splice(index, 1);
		  };
	  
	  
	
});



libraryModule.controller('ModalInstanceCtrl', function ($scope, $modalInstance, authorToEdit) {


	  $scope.authorToEdit = authorToEdit;
	  
	  $scope.ok = function () {
	    $modalInstance.close($scope.authorToEdit);
	  };

	  $scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	  };
	});

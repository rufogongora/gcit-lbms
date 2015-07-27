
libraryModule.controller('genreCtrl', function($scope, $http, $cookieStore, $modal, $log) {
	$scope.genres =[]
	$scope.currentPage = 1
	$scope.numPerPage = 5
	$scope.maxSize = 5
	$scope.numberOfGenres = 0
	$scope.searchGenreQuery = ""
	$scope.alerts = []
	
	$scope.getGenres = function getGenres()
	{
		//get N genres
		$http.get('http://localhost:8080/lms/genre/get/'+$scope.currentPage +'/'+$scope.numPerPage +'/'+$scope.searchGenreQuery)
		.success(
				function(data){
					$scope.genres = data;					
				})
		$http.get('http://localhost:8080/lms/genre/getCount/' +$scope.searchGenreQuery)
		.success(
				function(data){
					$scope.numberOfGenres = data;
					if (data <= 0 ){
						$scope.searchAlert = {msg : "No Genres where found!", type : "danger"}
					}
					else{
						$scope.searchAlert = null
					}
				})
	}
	$scope.getGenres();
	
	$scope.addGenre = function addGenre() {
		if (!$scope.newGenre)
			return
		/*if($scope.addGenreFrm.$valid){*/
		 $http.post('http://localhost:8080/lms/genre/add/', $scope.newGenre).
		  success(function(data) {
			 //alert('Genre Added');
			 $scope.genres.push(data)
			 $scope.getGenres()

			 
			 $scope.alerts.push({type: 'success', msg: 'Genre ' + $scope.newGenre.name + ' added!'});
			 $scope.newGenre = {}
		    });
		    
		//}
	};
	
	
	$scope.deleteGenre = function deleteGenre(genre){
		console.log(genre)
		$http.post('http://localhost:8080/lms/genre/delete', genre).success(function(data)	
		{
			 $scope.getGenres()
		})
		
	}
	
	$scope.searchGenre = function searchGenre(){
		
	}
	
	//popover stuff
	  $scope.dynamicPopover = {
			  	templateUrl: 'myPopoverTemplate.html',

			  };
	  
	  
	  
	  
	  $scope.pageChanged = function() {
		  $scope.getGenres()
		  };
	  

	  $scope.$watch("currentPage + numPerPage",  $scope.getGenres);
	  
	  

	  $scope.editGenre = function(genre, size){
		   $scope.genreCopy = JSON.parse(JSON.stringify(genre));
		   $scope.open(genre, size)
	  }
	  
	  $scope.animationsEnabled = true;
	  
	  
	  
	  $scope.open = function (genre, size) {

	    var modalInstance = $modal.open({
	      animation: $scope.animationsEnabled,
	      templateUrl: 'myModalContent.html',
	      controller: 'EditGenreCtrl',
	      size: size,
	      resolve: {
	        genreToEdit: function () {
	          return genre;
	        }
	    
	      }
	    });

	    modalInstance.result.then(function (genreToEdit) {
	      $scope.genreToEdit = genreToEdit;
	      
		 $http.post('http://localhost:8080/lms/genre/update/', $scope.genreToEdit).
		  success(function(data) {
			 console.log($scope.genres)
			  $scope.getGenres()
		    });
			    
	      
	    }, function () {
	    	for (i = 0; i < $scope.genres.length; i++){
	    		if ($scope.genres[i].genreId == $scope.genreCopy.genreId)
    			{
	    				$scope.genres[i] = $scope.genreCopy
	    				break;
    			}
	    	}
	    	 $scope.getGenres()
	    	
	    });
	  };

	  $scope.closeAlert = function(index) {
		    $scope.alerts.splice(index, 1);
		  };
	  
	  
	
});



libraryModule.controller('EditGenreCtrl', function ($scope, $modalInstance, genreToEdit) {


	  $scope.genreToEdit = genreToEdit;
	  
	  $scope.ok = function () {
	    $modalInstance.close($scope.genreToEdit);
	  };

	  $scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	  };
	});

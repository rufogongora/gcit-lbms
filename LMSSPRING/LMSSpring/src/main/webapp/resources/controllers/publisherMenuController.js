
libraryModule.controller('publisherCtrl', function($scope, $http, $cookieStore, $modal, $log) {
	$scope.publishers = []
	$scope.currentPage = 1
	$scope.numPerPage = 5
	$scope.maxSize = 5
	$scope.numberOfPublishers = 0
	$scope.searchPublisherQuery = ""
	$scope.alerts = []
	$scope.selectedSearchChoice = {"id" : "name"}
	
	$scope.searchChoices = [{"id": "name", "name" : "Name"}, {"id": "phoneNumber", "name" : "Phone Number"}, {"id": "address", "name" : "Address"}]

	$scope.getPublishers = function getPublishers()
	{
		//get N publishers
		$http.get('http://localhost:8080/lms/publisher/get/'+$scope.currentPage +'/'+$scope.numPerPage +'/'+ $scope.selectedSearchChoice.id+'/'+$scope.searchPublisherQuery)
		.success(
				function(data){
					$scope.publishers = data;					
				})
				$http.get('http://localhost:8080/lms/publisher/getCount/'+ $scope.selectedSearchChoice.id+'/' +$scope.searchPublisherQuery)
				.success(
						function(data){
							$scope.numberOfPublishers = data;
							if (data <= 0 ){
								$scope.searchAlert = {msg : "No Publishers where found!", type : "danger"}
							}
							else{
								$scope.searchAlert = null
							}
						})
	}
	$scope.getPublishers();

	function validate()
	{
		if (!$scope.newPublisher)
			return false
		if (!$scope.newPublisher.name)
			return false
		return true
	}
	$scope.addPublisher = function addPublisher() {
		/*if($scope.addPublisherFrm.$valid){*/
		if (!validate())
			return
		$http.post('http://localhost:8080/lms/publisher/add/', $scope.newPublisher).
		success(function(data) {
			$scope.getPublishers()

			$scope.alerts.push({type: 'success', msg: 'Publisher ' + $scope.newPublisher.name + ' added!'});
			$scope.newPublisher = {}
		});

		//}
	};


	$scope.deletePublisher = function deletePublisher(publisher){
		console.log(publisher)
		$http.post('http://localhost:8080/lms/publisher/delete', publisher).success(function(data)	
				{
			//$scope.publishers.splice()
			for (i = 0; i < $scope.publishers.length; i++){
				if ($scope.publishers[i].publisherId == publisher.publisherId)
				{
					$scope.publishers.splice(i, 1)
					break;
				}

			}
			$scope.getPublishers()
				})

	}

	$scope.searchPublisher = function searchPublisher(){

	}

	//popover stuff
	$scope.dynamicPopover = {
			templateUrl: 'myPopoverTemplate.html',

	};




	$scope.pageChanged = function() {
		$scope.getPublishers()
	};


	$scope.$watch("currentPage + numPerPage",  $scope.getPublishers);



	$scope.editPublisher = function(publisher, size){
		$scope.publisherCopy = JSON.parse(JSON.stringify(publisher));
		$scope.open(publisher, size)
	}

	$scope.animationsEnabled = true;



	$scope.open = function (publisher, size) {

		var modalInstance = $modal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'EditPublisherModalCtrl.html',
			controller: 'EditPublisherModalCtrl',
			size: size,
			resolve: {
				publisherToEdit: function () {
					return publisher;
				}

			}
		});

		modalInstance.result.then(function (publisherToEdit) {
			$scope.publisherToEdit = publisherToEdit;

			$http.post('http://localhost:8080/lms/publisher/update/', $scope.publisherToEdit).
			success(function(data) {
				$scope.getPublishers()
			});


		}, function () {
			for (i = 0; i < $scope.publishers.length; i++){
				if ($scope.publishers[i].id == $scope.publisherCopy.id)
				{
					$scope.publishers[i] = $scope.publisherCopy
					break;
				}
			}
			$scope.getPublishers()

		});
	};

	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

	
	


});



libraryModule.controller('EditPublisherModalCtrl', function ($scope, $modalInstance, publisherToEdit) {


	$scope.publisherToEdit = publisherToEdit;

	$scope.ok = function () {
		$modalInstance.close($scope.publisherToEdit);
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});

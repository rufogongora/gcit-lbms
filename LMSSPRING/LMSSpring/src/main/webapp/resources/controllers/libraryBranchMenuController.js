
libraryModule.controller('libraryBranchCtrl', function($scope, $http, $cookieStore, $modal, $log) {
	$scope.libraryBranchs = []
	$scope.currentPage = 1
	$scope.numPerPage = 5
	$scope.maxSize = 5
	$scope.numberOfLibraryBranchs = 0
	$scope.searchLibraryBranchQuery = ""
	$scope.alerts = []
	$scope.selectedSearchChoice = {"id" : "branchName"}
	
	$scope.searchChoices = [{"id": "branchName", "name" : "Library Branch Name"}, {"id": "branchAddress", "name" : "Address"}]

	$scope.getLibraryBranchs = function getLibraryBranchs()
	{
		//get N libraryBranchs
		$http.get('http://localhost:8080/lms/libraryBranch/get/'+$scope.currentPage +'/'+$scope.numPerPage +'/'+ $scope.selectedSearchChoice.id+'/'+$scope.searchLibraryBranchQuery)
		.success(
				function(data){
					$scope.libraryBranchs = data;					
				})
				$http.get('http://localhost:8080/lms/libraryBranch/getCount/'+ $scope.selectedSearchChoice.id+'/' +$scope.searchLibraryBranchQuery)
				.success(
						function(data){
							$scope.numberOfLibraryBranchs = data;
							if (data <= 0 ){
								$scope.searchAlert = {msg : "No LibraryBranchs where found!", type : "danger"}
							}
							else{
								$scope.searchAlert = null
							}
						})
	}
	$scope.getLibraryBranchs();

	function validate()
	{
		if (!$scope.newLibraryBranch)
			return false
		if (!$scope.newLibraryBranch.branchName)
			return false
			
		return true
	}
	$scope.addLibraryBranch = function addLibraryBranch() {
		/*if($scope.addLibraryBranchFrm.$valid){*/
		if (!validate())
			return
		$http.post('http://localhost:8080/lms/libraryBranch/add/', $scope.newLibraryBranch).
		success(function(data) {
			$scope.getLibraryBranchs()

			$scope.alerts.push({type: 'success', msg: 'LibraryBranch ' + $scope.newLibraryBranch.name + ' added!'});
			$scope.newLibraryBranch = {}
		});

		//}
	};


	$scope.deleteLibraryBranch = function deleteLibraryBranch(libraryBranch){
		console.log(libraryBranch)
		$http.post('http://localhost:8080/lms/libraryBranch/delete', libraryBranch).success(function(data)	
				{
			//$scope.libraryBranchs.splice()
			for (i = 0; i < $scope.libraryBranchs.length; i++){
				if ($scope.libraryBranchs[i].libraryBranchId == libraryBranch.libraryBranchId)
				{
					$scope.libraryBranchs.splice(i, 1)
					break;
				}

			}
			$scope.getLibraryBranchs()
				})

	}

	$scope.searchLibraryBranch = function searchLibraryBranch(){

	}

	//popover stuff
	$scope.dynamicPopover = {
			templateUrl: 'myPopoverTemplate.html',

	};




	$scope.pageChanged = function() {
		$scope.getLibraryBranchs()
	};


	$scope.$watch("currentPage + numPerPage",  $scope.getLibraryBranchs);



	$scope.editLibraryBranch = function(libraryBranch, size){
		$scope.libraryBranchCopy = JSON.parse(JSON.stringify(libraryBranch));
		$scope.open(libraryBranch, size)
	}

	$scope.animationsEnabled = true;



	$scope.open = function (libraryBranch, size) {

		var modalInstance = $modal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'EditLibraryBranchModalCtrl.html',
			controller: 'EditLibraryBranchModalCtrl',
			size: size,
			resolve: {
				libraryBranchToEdit: function () {
					return libraryBranch;
				}

			}
		});

		modalInstance.result.then(function (libraryBranchToEdit) {
			$scope.libraryBranchToEdit = libraryBranchToEdit;

			$http.post('http://localhost:8080/lms/libraryBranch/update/', $scope.libraryBranchToEdit).
			success(function(data) {
				$scope.getLibraryBranchs()
			});


		}, function () {
			for (i = 0; i < $scope.libraryBranchs.length; i++){
				if ($scope.libraryBranchs[i].id == $scope.libraryBranchCopy.id)
				{
					$scope.libraryBranchs[i] = $scope.libraryBranchCopy
					break;
				}
			}
			$scope.getLibraryBranchs()

		});
	};

	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

	
	


});



libraryModule.controller('EditLibraryBranchModalCtrl', function ($scope, $modalInstance, libraryBranchToEdit) {


	$scope.libraryBranchToEdit = libraryBranchToEdit;

	$scope.ok = function () {
		$modalInstance.close($scope.libraryBranchToEdit);
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});

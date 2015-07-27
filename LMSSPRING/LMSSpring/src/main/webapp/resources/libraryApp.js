var libraryModule = angular.module('libraryApp', [ 'ngRoute', 'ngCookies', 'ui.bootstrap']);


libraryModule.config([ "$routeProvider", function($routeProvider) {
	return $routeProvider.when("/", {
		redirectTo : "/home"
	}).when("/home", {
		templateUrl : "home.html"
	}).when("/adminMenu", {
		templateUrl : "admin.html"
	}).when ("/publisherMenu", {
		templateUrl : "modules/publisherModal.html"
	}).when ("/genreMenu", {
		templateUrl : "modules/genreModal.html"
	}).when ("/bookMenu", {
		templateUrl : "modules/bookModal.html"
	}).when("/authorMenu", {
		templateUrl : "modules/authorModal.html"
	}).when("/libraryMenu", {
		templateUrl : "modules/libraryBranchModal.html"
	}).when("/librarian", {
		templateUrl : "librarian.html"
	}).when("/borrower", {
		templateUrl : "borrower.html"
	})
	
} ]);







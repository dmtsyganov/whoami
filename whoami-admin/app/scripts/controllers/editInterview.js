'use strict';

  myApp.controller('EditInterviewCtrl', ['$scope','$route','$location', 'template', function ($scope, $route, $location, template) {

    $scope.template = template;

    $scope.save = function() {
/*
        $scope.recipe.$save(function(recipe) {
            $location.path('/view/' + recipe.id);
        });
*/
        $location.path('/viewInterview/' + $scope.template.id);
    };

    $scope.cancel = function() {
        $location.path('/interview/');
    };
/*
    $scope.remove = function() {
        delete $scope.recipe;
        $location.path('/');
    };
*/

  }]);

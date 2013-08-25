'use strict';

angular.module('whoamiAdminApp')
  .controller('EditInterviewCtrl', ['$scope','$route','$location', 'templates', function ($scope, $route, $location, templates) {
    var id = $route.current.params.interviewId;
    $scope.template = templates[id];

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

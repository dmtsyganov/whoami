'use strict';

  myApp.controller('EditInterviewCtrl', ['$scope','$route','$location', 'template', 'traits',
      function ($scope, $route, $location, template, traits) {

        $scope.template = template;
        $scope.traits = traits;

        $scope.save = function() {
            $scope.template.$save(function(template) {
                $location.path('/viewInterview/' + template.id);
            }, function(response) {
                var error = 'blah';
            });
        };

        $scope.cancel = function() {
            $location.path('/interview/');
        };

        //DELETE method is not configured yet
/*
        $scope.remove = function() {
            $scope.template.$delete();
            $location.path('/interview/');
        };
*/

  }]);

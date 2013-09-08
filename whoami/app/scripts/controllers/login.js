'use strict';

angular.module('whoamiApp')
  .controller('LoginCtrl', ['$scope', '$location', function ($scope, $location) {

        $scope.login = function(username, password) {
            console.log($scope.loginForm);
            console.log($scope.createForm);
            console.log("User: " + username + " password " + password);
        };

        $scope.create = function() {
            console.log("New User");
        };

        $scope.cancel = function() {
            console.log("Cancel");
            $location.path("/");
        };
  }]);

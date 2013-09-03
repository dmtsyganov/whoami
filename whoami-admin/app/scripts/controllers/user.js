'use strict';

  myApp.controller('UserCtrl', ['$scope', '$location', 'User',
      function ($scope, $location, User) {

        $scope.newUser = function() {
            $location.path('/editUser/0');
        };

        $scope.find = function() {

        };

  }]);

'use strict';

  myApp.controller('UserCtrl', ['$scope', '$location', 'User',
      function ($scope, $location, User) {

        $scope.new = function() {
            $location.path('/editUser/0');
        }

  }]);

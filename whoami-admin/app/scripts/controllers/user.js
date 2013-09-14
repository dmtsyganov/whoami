'use strict';

  myApp.controller('UserCtrl', ['$scope', '$location', 'User',
      function ($scope, $location, User) {

        $scope.newUser = function() {
            $location.path('/editUser/0');
        };

        // find user form
        $scope.isFindUser = true;

        $scope.showFind = function() {
          $scope.isFindUser = true;
        }

        $scope.find = function(login) {

          //             if(response.status === 404) {
          // not found
          $scope.alerts.push({
              type: 'error',
              msg: "Данный пользователь не найден."
          });
        //              }
        };

        $scope.cancelFind = function() {
          $scope.isFindUser = false;
        }

        // alerts
        $scope.alerts = [];

        $scope.closeAlert = function(index) {
          $scope.alerts.splice(index, 1);
        };
  }]);

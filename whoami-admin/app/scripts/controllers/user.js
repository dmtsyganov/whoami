'use strict';

  myApp.controller('UserCtrl', ['$scope', '$location', 'User',
      function ($scope, $location, User) {

        $scope.newUser = function() {
            $location.path('/editUser/0');
        };

        // find user form
        $scope.isFindUser = false;

        $scope.showFind = function() {
          $scope.isFindUser = true;
        }

        $scope.find = function(login) {

          User.get({id:null, query:'query', login:login},
              function(user) {
                  $location.path('/editUser/' + user.id);
              }, function(response) {
                if(response.status === 404) {
                  // not found
                  $scope.alerts.push({
                      type: 'error',
                      msg: "Данный пользователь [" + login + "] не найден."
                  });
                }
              });
        };

        $scope.cancelFind = function() {
          $scope.isFindUser = false;
          $scope.alerts = [];
        }

        // alerts
        $scope.alerts = [];

        $scope.closeAlert = function(index) {
          $scope.alerts.splice(index, 1);
        };
  }]);

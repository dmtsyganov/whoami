'use strict';

  myApp.controller('ViewUserCtrl', ['$scope', '$route', '$location', 'user',
      function ($scope, $route, $location, user) {

          $scope.user = user;

          // back to the user list
          $scope.cancel = function() {
              $location.path('/user/');
          };

          // switch to the user edit view
          $scope.edit = function() {
              $location.path('/editUser/' + $scope.user.id);
          };


      }]);

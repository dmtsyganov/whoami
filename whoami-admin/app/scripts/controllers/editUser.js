'use strict';

  myApp.controller('EditUserCtrl', ['$scope','$route','$location', 'user',
      function ($scope, $route, $location, user) {

          $scope.user = user;

          // user roles
          var userRoles = [
              {
                  role: 'USER',
                  name: 'пользователь'
              },
              {
                  role: 'ADMINISTRATOR',
                  name: 'администратор'
              },
              {
                  role: 'EDITOR',
                  name: 'редактор'
              }
          ];

          $scope.userRoles = userRoles;

          $scope.save = function() {
              $scope.user.$save(function(user) {
                  $location.path('/viewUser/' + user.id);
              }, function(response) {
                  console.log("Error: " + response)
              });
          };

          $scope.cancel = function() {
              $location.path('/user/');
          };

          //DELETE method is not configured yet
          /*
           $scope.remove = function() {
           $scope.template.$delete();
           $location.path('/interview/');
           };
           */

      }]);

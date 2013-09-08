'use strict';

angular.module('whoamiApp')
  .controller('MainCtrl', ['$scope', '$location', function ($scope, $location) {
        $scope.login = function() {
            $location.path('/login/');
        };
  }]);

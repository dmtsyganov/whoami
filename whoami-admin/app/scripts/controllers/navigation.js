'use strict';

  myApp.controller('NavigationCtrl', ['$scope', '$location', function ($scope, $location) {
    $scope.routeIs = function(routeName) {
        return $location.path() === routeName;
    };
  }]);

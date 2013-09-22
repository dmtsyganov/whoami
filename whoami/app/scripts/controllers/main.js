'use strict';

myApp
    .controller('MainCtrl', ['$scope', '$location', function ($scope, $location) {
        $scope.login = function () {
            $location.path('/login/');
        };

        $scope.$on('event:loginRequired', function () {
            $location.path('/login');
        });
    }]);

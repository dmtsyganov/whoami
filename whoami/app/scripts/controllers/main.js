'use strict';

myApp
    .controller('MainCtrl', ['$scope', '$rootScope', '$location', function ($scope, $rootScope, $location) {
        $scope.login = function () {
            $location.path('/login/');
        };

        $rootScope.$on('event:loginRequired', function () {
            $location.path('/login');
        });
    }]);

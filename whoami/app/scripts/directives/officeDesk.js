'use strict';

myApp
    .directive('officeDesk', [function () {
        return {
            template: '<div class="breadcrumb user-login-to">ваш логин: <b>{{ user.login }}</b> <a href="" ng-click="onChange()" class="pull-right">Выйти</a></div>',
            restrict: 'E',
            scope: {},
            controller: ('LogOffCtrl', ['$scope', '$location', 'CurrentUser', 'CurrentInterview',
                function ($scope, $location, CurrentUser, CurrentInterview) {
                $scope.user = CurrentUser;
                $scope.onChange = function () {
                    CurrentUser.id = undefined;
                    CurrentUser.login = undefined;
                    CurrentUser.password = undefined;
                    CurrentUser.profile = {};

                    CurrentInterview.clear();

                    $location.url('/');
                }
            }])
        };
    }]);


//Добро пожаловать: {{customer.name}}

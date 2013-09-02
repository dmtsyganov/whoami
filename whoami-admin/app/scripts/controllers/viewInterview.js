'use strict';

myApp.controller('ViewInterviewCtrl',
    ['$scope', '$route', '$location', 'template',
        function ($scope, $route, $location, template) {

            $scope.template = template;

            // back to the interview list
            $scope.cancel = function() {
                $location.path('/interview/');
            };

            // switch to the interview edit view
            $scope.edit = function() {
                $location.path('/editInterview/' + $scope.template.id);
            };
    }]);

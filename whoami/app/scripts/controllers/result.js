'use strict';

myApp
    .controller('ResultCtrl', ['$scope', 'results', '$location', function ($scope, results, $location) {

        $scope.results = results;

        $scope.goBack = function() {
            var id = results.userId;
            if(id === null || id === "") {
                id = '0';
            }
            $location.path("/user/" + id);
        }

    }]);

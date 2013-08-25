'use strict';

angular.module('whoamiAdminApp')
  .controller('InterviewCtrl', ['$scope', '$location', 'templates', function ($scope, $location, templates) {
    templates.query(function(res) {
         $scope.templates = res;
    }, function(response) {
        if(response.status === 404) {
            var aaa = 'baaad';
        }
    });

    $scope.new = function() {
        $location.path('/editInterview/' + 0);
    };
  }]);

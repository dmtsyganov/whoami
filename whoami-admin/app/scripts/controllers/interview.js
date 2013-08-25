'use strict';

angular.module('whoamiAdminApp')
  .controller('InterviewCtrl', ['$scope', '$location', 'templates', function ($scope, $location, templates) {
    $scope.templates = templates;

    $scope.new = function() {
        $location.path('/editInterview/' + 0);
    };
  }]);

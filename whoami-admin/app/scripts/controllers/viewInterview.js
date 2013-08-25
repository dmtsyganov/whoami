'use strict';

angular.module('whoamiAdminApp')
  .controller('ViewInterviewCtrl', ['$scope', '$route', '$location', 'templates', function ($scope, $route, $location, templates) {
    var id = $route.current.params.interviewId;
    $scope.template = templates[id];
    $scope.edit = function() {
        $location.path('/editInterview/' + $scope.template.id);
    };
  }]);

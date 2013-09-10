'use strict';

angular.module('whoamiApp')
  .controller('InterviewCtrl', ['$scope', 'CurrentInterview', function ($scope, CurrentInterview) {
        $scope.userId = CurrentInterview.userId;
        $scope.templateId = CurrentInterview.templateId;
        $scope.interview = CurrentInterview.interview;

  }]);

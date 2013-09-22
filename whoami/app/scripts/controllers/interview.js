'use strict';

angular.module('whoamiApp')
    .controller('InterviewCtrl', ['$scope', 'CurrentInterview', '$location',
        function ($scope, CurrentInterview, $location) {
            $scope.userId = CurrentInterview.userId;
            $scope.templateId = CurrentInterview.templateId;
            $scope.interview = CurrentInterview.interview;

            // save the interview answers
            $scope.save = function () {
                $scope.interview.$save({userId: null, templateId: null},
                    function (interview) {
                        $location.path("/user/" + $scope.userId);
                    }, function (response) {
                        console.log("Error saving interview: " + response.toString());
                    });
            };

            // cancel and return to the user pane
            $scope.cancel = function () {
                $location.path("/user/" + $scope.userId);
            };

        }]);

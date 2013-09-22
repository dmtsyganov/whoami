'use strict';

angular.module('whoamiApp')
    .controller('InterviewCtrl', ['$scope', 'CurrentInterview', '$location',
        function ($scope, CurrentInterview, $location) {
            $scope.userId = CurrentInterview.userId;
            $scope.templateId = CurrentInterview.templateId;
            $scope.interview = CurrentInterview.interview;

            $scope.answers = [];

            if($scope.userId === null) {
                $location.path("/user/0");
            }

            // initialize scope questions var
            $scope.initialized = false;
            var initialize = function () {
                if ($scope.initialized ||
                    angular.isUndefined($scope.interview) ||
                    $scope.interview === null)
                    return;

                //console.log("Initializing");
                for (var i = 0; i < $scope.interview.answers.length; i++) {

                    var type = $scope.interview.answers[i].valueType;
                    var valueEffect = $scope.interview.answers[i].valueEffect;
                    var value = $scope.interview.answers[i].value;

                    if(value !== null) {
                        value = parseInt(value); // value is a string
                    }

                    if(value !== null && type === 'YES_NO')  {
                        if(value > 0 && valueEffect < 1) {
                            value = 0;
                        } else if (value < 1 && valueEffect < 1) {
                            value = 1;
                        }
                    }

                    $scope.answers.push({
                        question: $scope.interview.answers[i].question,
                        valueType: type,
                        value: value
                    });
                }

                $scope.initialized = true;
            };

            initialize();


            // save the interview answers
            $scope.save = function () {

                // before we save it we need to convert yes/no answers based on the value effect (+/-)
                for (var i = 0; i < $scope.answers.length; i++) {

                    var type = $scope.answers[i].valueType;
                    var valueEffect = $scope.interview.answers[i].valueEffect;
                    var value = $scope.answers[i].value;

                    if(value === null)
                        continue;

                    if(type === 'YES_NO')  {
                        if(value > 0 && valueEffect < 1) {
                            value = 0;
                        } else if (value < 1 && valueEffect < 1) {
                            value = 1;
                        }
                    }

                    // save the value
                    $scope.interview.answers[i].value = value;
                }

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

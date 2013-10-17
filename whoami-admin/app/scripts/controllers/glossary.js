'use strict';

angular.module('whoamiAdminApp')
  .controller('GlossaryCtrl', ['$scope', 'glossary', 'GlossaryTerm',
        function ($scope, glossary, GlossaryTerm) {

        var newTerm = {
            term:null,
            definition:null
        }

        $scope.glossary = glossary;
        $scope.isAddTerm = false;
        $scope.alerts = [];
        $scope.newTerm = newTerm;

        $scope.showAdd = function() {
            $scope.isAddTerm = true;
        }

        $scope.add = function() {
            var term = new GlossaryTerm({term: $scope.newTerm.term, definition:$scope.newTerm.definition});
            term.$save(function(t) {
                $scope.glossary.push(t);
            }, function(response) {
                if (response.status === 409) {
                    // duplicate term
                    $scope.alerts.push({
                        type: 'error',
                        msg: "Данный термин уже присутствует в глоссарии!"
                    });
                } else {
                    console.log("Response error: " + response.toString());
                    $scope.alerts.push({
                        type: 'error',
                        msg: "Не удалось добавить новый термин!"
                    });
                }
            });
        };

        $scope.cancel = function() {
            $scope.isAddTerm = false;
            $scope.alerts = [];
        }

        $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
        };
  }]);

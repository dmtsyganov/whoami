'use strict';

myApp
    .controller('QuestionCtrl', ['$scope', function ($scope) {

        $scope.isYesNo = function (index) {
            return $scope.answers[index].valueType === 'YES_NO';
        };

        $scope.isScore = function (index) {
            return $scope.answers[index].valueType === 'SCORE';
        };

        $scope.isNullOrUndefined = function (value) {
            return (angular.isUndefined(value) || value === null);
        }

        $scope.rate = 0;
        $scope.max = 7;

        $scope.ratingStates = [
            {stateOn: 'icon-ok-sign', stateOff: 'icon-ok-circle'},
            {stateOn: 'icon-star', stateOff: 'icon-star-empty'},
            {stateOn: 'icon-heart', stateOff: 'icon-ban-circle'},
            {stateOn: 'icon-heart'},
            {stateOff: 'icon-off'}
        ];
    }]);

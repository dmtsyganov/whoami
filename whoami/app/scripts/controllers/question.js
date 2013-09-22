'use strict';

myApp
    .controller('QuestionCtrl', ['$scope', function ($scope) {

        $scope.isYesNo = function (index) {
            return $scope.interview.answers[index].valueType === 'YES_NO';
        };

        $scope.isScore = function (index) {
            return $scope.interview.answers[index].valueType === 'SCORE';
        };

        /*
         <td>{{answer.trait}}</td>
         <td>{{answer.type}}</td>
         <td>{{answer.valueEffect}}</td>
         */

        $scope.onYes = function (index) {
            $scope.interview.answers[index].value = 1;
        };

        $scope.onNo = function (index) {
            $scope.interview.answers[index].value = 0;
        };

//        $scope.radioValue = 1;
        $scope.onClick = function (index) {
//            $scope.interview.answers[index].value = $scope.radioValue;
        };

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

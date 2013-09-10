'use strict';

myApp
  .controller('QuestionCtrl', ['$scope', function ($scope) {

        $scope.isYesNo = function(index) {
            return $scope.interview.answers[index].valueType === 'YES_NO';
        };

        $scope.isScore = function(index) {
            return $scope.interview.answers[index].valueType === 'SCORE';
        };

        $scope.rate = 5;
        $scope.max = 7;
        $scope.isReadonly = false;

        $scope.hoveringOver = function(value) {
            $scope.overStar = value;
            $scope.preview = value;
        };

        $scope.ratingStates = [
            {stateOn: 'icon-ok-sign', stateOff: 'icon-ok-circle'},
            {stateOn: 'icon-star', stateOff: 'icon-star-empty'},
            {stateOn: 'icon-heart', stateOff: 'icon-ban-circle'},
            {stateOn: 'icon-heart'},
            {stateOff: 'icon-off'}
        ];

/*



        <td>{{answer.trait}}</td>
            <td>{{answer.type}}</td>
        <td>{{answer.valueEffect}}</td>
*/

    }]);

'use strict';

  myApp .controller('EditQuestionCtrl', ['$scope', function ($scope) {

      // question type
      var questionTypes = [
          {
              type: 'DIRECT',
              name: 'прямой'
          },
          {
              type: 'INDIRECT',
              name: 'косвенный'
          },
          {
              type: 'INFORMATION',
              name: 'информация'
          }
      ];

      $scope.questionTypes = questionTypes;

      //question value type
      var valueTypes = [
          {
              type: 'YES_NO',
              name: 'да/нет'
          },
          {
              type: 'SCORE',
              name: 'оценка'
          },
          {
              type: 'TEXT',
              name: 'текст'
          }
      ];

      $scope.valueTypes = valueTypes;


      $scope.addQuestion = function() {
          var questions = $scope.template.questions;
          questions[questions.length] = {
              type: 'INDIRECT',
              valueType: 'YES_NO'
          };
      };
      $scope.removeQuestion = function(index) {
          $scope.template.questions.splice(index, 1);
      };


  }]);

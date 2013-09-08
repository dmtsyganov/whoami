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

      //question value effect
      var valueEffects = [
          {
              type: 0,
              name: '-'
          },
          {
              type: 1,
              name: '+'
          }
      ];

      $scope.valueEffects = valueEffects;

      $scope.showEffect = function(index) {
          return $scope.template.questions[index].type === 'INDIRECT';
      }

      $scope.addQuestion = function() {
          var questions = $scope.template.questions;
          questions[questions.length] = {
              type: 'INDIRECT',
              valueType: 'YES_NO',
              valueEffect: 1
          };
      };

      $scope.typeChanged = function(index) {
          var newType = $scope.template.questions[index].type;
          if(newType === 'INDIRECT') {
              $scope.template.questions[index].valueType = 'YES_NO';
          } else if(newType === 'DIRECT') {
              $scope.template.questions[index].valueType = 'SCORE';
              $scope.template.questions[index].valueEffect = 1;
          } else if(newType === 'INFORMATION') {
              $scope.template.questions[index].valueType = 'TEXT';
              $scope.template.questions[index].valueEffect = 0;
          }
          console.log("Question changed:" + $scope.template.questions[index]);
      }

      $scope.removeQuestion = function(index) {
          $scope.template.questions.splice(index, 1);
      };

      // watch questions
/*
      $scope.watchCollection('template.questions', function() {

      });
*/

  }]);

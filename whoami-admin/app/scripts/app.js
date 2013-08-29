'use strict';

var myApp = angular.module('whoamiAdminApp', ['whoamiAdminApp.services']);


  myApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/user', {
        templateUrl: 'views/user.html',
        controller: 'UserCtrl'
      })
      .when('/interview', {
        templateUrl: 'views/interview.html',
        resolve: {
            templates: function(LoadTemplates) {
                return LoadTemplates();
            }
        },
        controller: 'InterviewCtrl'
      })
      .when('/help', {
        templateUrl: 'views/help.html',
        controller: 'HelpCtrl'
      })
      .when('/editInterview/:interviewId', {
        templateUrl: 'views/editInterview.html',
        resolve: {
            template: function(LoadTemplate) {
                return LoadTemplate();
            }
        },
        controller: 'EditInterviewCtrl'
      })
      .when('/viewInterview/:interviewId', {
        templateUrl: 'views/viewInterview.html',
        resolve: {
            template: function(LoadTemplate) {
                return LoadTemplate();
            },
            questions: function(LoadQuestions) {
                return LoadQuestions();
            }
        },
        controller: 'ViewInterviewCtrl'
      })
      .when('/viewUser/:userId', {
        templateUrl: 'views/viewUser.html',
        controller: 'ViewUserCtrl'
      })
      .when('/editUser/:userId', {
        templateUrl: 'views/editUser.html',
        controller: 'EditUserCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  }]);

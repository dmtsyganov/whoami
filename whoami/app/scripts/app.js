'use strict';

var myApp = angular.module('whoamiApp', ['ui.bootstrap', 'whoamiApp.userServices', 'whoamiApp.interviewServices']);

  myApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/user/:userId', {
        templateUrl: 'views/user.html',
        resolve: {
            user: function(LoadUser) {
                return LoadUser();
            },
            templates: function(LoadTemplates) {
                return LoadTemplates();
            }
        },
        controller: 'UserCtrl'
      })
      .when('/profile', {
        templateUrl: 'views/profile.html',
        controller: 'ProfileCtrl'
      })
      .when('/interview', {
        templateUrl: 'views/interview.html',
        controller: 'InterviewCtrl'
      })
      .when('/result', {
        templateUrl: 'views/result.html',
        controller: 'ResultCtrl'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  }]);

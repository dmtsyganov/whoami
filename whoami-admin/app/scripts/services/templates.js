'use strict';

var services = angular.module('whoamiAdminApp.services', ['ngResource']);

// base templates request object
services.factory('Templates', ['$resource', function($resource) {
        return $resource('/whoami-rs/rest/templates/:id', {id:'@id'});
    }]);

// base questions request object
services.factory('Questions', ['$resource', function($resource) {
        return $resource('/whoami-rs/rest/templates/:id/questions', {id:'id'});
    }]);

services.factory('LoadTemplates', ['Templates', '$q',
    function(Templates, $q) {
        return function() {
            var deferred = $q.defer();
            Templates.query(function(templates) {
                deferred.resolve(templates);
            },function(err) {
                deferred.reject(err);
            });
            return deferred.promise;
        };
    }]);

services.factory('LoadTemplate', ['Templates', '$route', '$q',
    function(Templates, $route, $q) {
        return function() {
            var deferred = $q.defer();
            Templates.get({id: $route.current.params.interviewId}, function(template) {
                deferred.resolve(template);
            }, function(err) {
                deferred.reject(err);
            });
            return deferred.promise;
        };
    }]);

// load template's questions
services.factory('LoadQuestions', ['Questions', '$route', '$q',
    function(Questions, $route, $q) {
        return function() {
            var deferred = $q.defer();
            Questions.query({id: $route.current.params.interviewId}, function(questions) {
                deferred.resolve(questions);
            },function(err) {
                deferred.reject(err);
            });
            return deferred.promise;
        };
    }]);

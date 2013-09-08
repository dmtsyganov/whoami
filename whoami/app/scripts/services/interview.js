'use strict';

var services = angular.module('whoamiApp.interviewServices', ['ngResource']);

// base Template request object
services.factory('Template', ['$resource', function($resource) {
    return $resource('/whoami-rs/rest/templates/:id', {id: '@id'});
}]);

// base Interview request object
services.factory('Interview', ['$resource', function($resource) {
    return $resource('/whoami-rs/rest/interviews/:id/:userId/:templateId', {id: '@id', userId: 'userId', templateId: 'templateId'});
}]);

// load all active templates
services.factory('LoadTemplates', ['Template', '$q',
    function(Template, $q) {
        return function() {
            var deferred = $q.defer();
            Template.query({active:true}, function(templates) {
                deferred.resolve(templates);
            },function(err) {
                deferred.reject(err);
            });
            return deferred.promise;
        };
    }]);

// load all user's interviews
services.factory('LoadInterviews', ['Interview', '$q', '$route', 'CurrentUser',
    function(Interview, $q, $route, CurrentUser) {
        return function() {
            var deferred = $q.defer();

            var userId = $route.current.params.userId;
            if( userId === '0') {
                // do we have logged in user?
                if(CurrentUser && CurrentUser.id) {
                    userId = CurrentUser.id;
                } else {
                    deferred.reject("You must login.");
                }
            }

            if( userId !== '0') {
                Interview.query({id: $route.current.params.userId, userId: null, templateId: null}, function(interviews) {
                    deferred.resolve(interviews);
                },function(err) {
                    deferred.reject(err);
                });
            }

            return deferred.promise;
        };
    }]);

/*
 // cache the templates
 var cachedTraits = cache.get(1);
 if(cachedTraits) {
 deferred.resolve(cachedTraits);
 } else {


services.factory('LoadTemplate', ['Template', '$route', '$q',
    function(Template, $route, $q) {
        return function() {
            var deferred = $q.defer();
            Template.get({id: $route.current.params.interviewId}, function(template) {
                deferred.resolve(template);
            }, function(err) {
                deferred.reject(err);
            });
            return deferred.promise;
        };
    }]);
*/

'use strict';

var services = angular.module('whoamiApp.interviewServices', ['ngResource']);

// base Template request object
services.factory('Template', ['$resource', function ($resource) {
    return $resource('/whoami-rs/rest/templates/:id', {id: '@id'});
}]);

// base Interview request object
services.factory('Interview', ['$resource', function ($resource) {
    return $resource('/whoami-rs/rest/interviews/:id/:userId/:templateId', {id: '@id', userId: 'userId', templateId: 'templateId'});
}]);

// base Interview result object
services.factory('Results', ['$resource', function ($resource) {
    return $resource('/whoami-rs/rest/interviews/:id/results', {id: '@id'});
}]);

// load all active templates
services.factory('LoadTemplates', ['Template', '$q',
    function (Template, $q) {
        return function () {
            var deferred = $q.defer();
            Template.query({active: true}, function (templates) {
                deferred.resolve(templates);
            }, function (err) {
                deferred.reject(err);
            });
            return deferred.promise;
        };
    }]);

// load all user's interviews
services.factory('LoadInterviews', ['Interview', '$q', '$route', 'CurrentUser', '$rootScope',
    function (Interview, $q, $route, CurrentUser, $rootScope) {
        return function () {
            var deferred = $q.defer();

            var userId = $route.current.params.userId;
            if (userId === '0') {
                // do we have logged in user?
                if (CurrentUser && CurrentUser.id) {
                    userId = CurrentUser.id;
                } else {
                    $rootScope.$broadcast('event:loginRequired');
                    deferred.reject("You must login.");
                }
            }

            if (userId !== '0') {
                Interview.query({id: userId, userId: null, templateId: null}, function (interviews) {
                    deferred.resolve(interviews);
                }, function (err) {
                    deferred.reject(err);
                });
            }

            return deferred.promise;
        };
    }]);

// load user's interview results
services.factory('LoadResults', ['Results', '$q', '$route', 'CurrentUser', '$rootScope',
    function (Results, $q, $route, CurrentUser, $rootScope) {
        return function () {
            var deferred = $q.defer();

            var userId = $route.current.params.userId;
            if (userId === '0') {
                // do we have logged in user?
                if (CurrentUser && CurrentUser.id) {
                    userId = CurrentUser.id;
                } else {
                    $rootScope.$broadcast('event:loginRequired');
                    deferred.reject("You must login.");
                }
            }

            if (userId !== '0') {
                Results.get({id: userId}, function (results) {
                    deferred.resolve(results);
                }, function (err) {
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

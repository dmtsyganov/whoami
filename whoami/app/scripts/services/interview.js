'use strict';

var services = angular.module('whoamiApp.interviewServices', ['ngResource']);

// base Template request object
services.factory('Template', ['$resource', function($resource) {
    return $resource('/whoami-rs/rest/templates/:id', {id: '@id'});
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

/*
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

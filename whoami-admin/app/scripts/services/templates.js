'use strict';

var services = angular.module('whoamiAdminApp.services', ['ngResource']);

// base Template request object
services.factory('Template', ['$resource', function($resource) {
        return $resource('/whoami-rs/rest/templates/:id/', {id:'@id'});
    }]);

// personality traits request object
services.factory('Traits', ['$resource', function($resource) {
        return $resource('/whoami-rs/rest/traits/');
    }]);

services.factory('LoadTemplates', ['Template', '$q',
    function(Template, $q) {
        return function() {
            var deferred = $q.defer();
            Template.query(function(templates) {
                deferred.resolve(templates);
            },function(err) {
                deferred.reject(err);
            });
            return deferred.promise;
        };
    }]);

services.factory('LoadTemplate', ['Template', '$route', '$q',
    function(Template, $route, $q) {
        return function() {
            var deferred = $q.defer();
            if($route.current.params.interviewId === '0') {
                deferred.resolve( new Template({
                    name: undefined,
                    description: undefined,
                    active: false,
                    order: 0,
                    questions: [{
                        type: 'INDIRECT',
                        trait: 'ENERGETIC', // just pick the first one
                        valueType: 'YES_NO',
                        valueEffect: 1
                    }]
                }));
            } else {
                Template.get({id: $route.current.params.interviewId}, function(template) {
                    deferred.resolve(template);
                }, function(err) {
                    deferred.reject(err);
                });
            }
            return deferred.promise;
        };
    }]);

// load personality traits
services.factory('LoadTraits', ['Traits', '$q', '$cacheFactory',
    function(Traits, $q, $cacheFactory) {

        // cache the result
        var cache = $cacheFactory('Traits');

        return function() {
            var deferred = $q.defer();
            var cachedTraits = cache.get(1);
            if(cachedTraits) {
                deferred.resolve(cachedTraits);
            } else {
                Traits.query(function(traits) {
                    cache.put(1, traits);
                    deferred.resolve(traits);
                }, function(err) {
                   deferred.reject(err);
                });
            }
            return deferred.promise;
        }
    }]);

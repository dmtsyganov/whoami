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
                    name: 'Новое Интервью',
                    description: 'Описание',
                    active: false,
                    questions: [{}]
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
services.factory('LoadTraits', ['Traits', '$q',
    function(Traits, $q) {
        return function() {
            var deferred = $q.defer();
            Traits.query(function(traits) {
                deferred.resolve(traits);
            }, function(err) {
               deferred.reject(err);
            });
            return deferred.promise;
        }
    }]);
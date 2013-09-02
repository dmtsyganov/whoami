'use strict';

var services = angular.module('whoamiAdminApp.userServices', ['ngResource']);

// base Template request object
services.factory('User', ['$resource', function($resource) {
    return $resource('/whoami-rs/rest/users/:id/', {id:'@id'});
}]);

services.factory('LoadUser', ['User', '$route', '$q',
    function(User, $route, $q) {
        return function() {
            var deferred = $q.defer();

            if($route.current.params.userId === '0') {
                // Create new user
                deferred.resolve(new User({role:'USER'}));
            } else {
                User.get({id: $route.current.params.userId}, function(user) {
                    deferred.resolve(user);
                }, function(err) {
                    deferred.reject(err);
                });
            }
            return deferred.promise;
        };
    }]);

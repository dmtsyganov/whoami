'use strict';

var services = angular.module('whoamiApp.userServices', ['ngResource']);

// base User request object
services.factory('User', ['$resource', function ($resource) {
    return $resource('/whoami-rs/rest/users/:id/:login/:password', {id: '@id', login: 'login', password: 'password'});
}]);

services.factory('CurrentUser', ['$rootScope', function ($rootScope) {
    var LOCAL_STORAGE_ID = 'currentUser',
        currentUser = localStorage[LOCAL_STORAGE_ID];

    var customer = currentUser ? JSON.parse(currentUser) : {
        id: null,
        login: null,
        profile: {
            fullName: null,
            email: null,
            age: null,
            gender: null
        }
    };

    $rootScope.$watch(function () {
        return customer;
    }, function () {
        localStorage[LOCAL_STORAGE_ID] = JSON.stringify(customer);
    }, true);

    return customer;
}]);

services.factory('LoadUser', ['User', 'CurrentUser', '$route', '$q', '$rootScope',
    function (User, CurrentUser, $route, $q, $rootScope) {
        return function () {
            var deferred = $q.defer();

            if ($route.current.params.userId === '0') {
                // do we have logged in user?
                if (CurrentUser && CurrentUser.id) {
                    deferred.resolve(CurrentUser);
                } else {
                    // login
                    $rootScope.$broadcast('event:loginRequired');
                    deferred.reject("You must login.");
                }
            } else {
                User.get({id: $route.current.params.userId, login: null, password: null}, function (user) {
                    /*
                     CurrentUser = user;
                     */
                    CurrentUser.id = user.id;
                    CurrentUser.login = user.login;
                    CurrentUser.password = user.password;
                    if (user.profile) {
                        CurrentUser.profile.fullName = user.profile.fullName;
                        CurrentUser.profile.email = user.profile.email;
                        CurrentUser.profile.age = user.profile.age;
                        CurrentUser.profile.gender = user.profile.gender;
                    }

                    deferred.resolve(user);
                }, function (err) {
                    deferred.reject(err);
                });
            }
            return deferred.promise;
        };
    }]);

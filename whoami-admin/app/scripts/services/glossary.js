'use strict';

var services = angular.module('whoamiAdminApp.glossaryServices', ['ngResource']);

services.factory('Glossary', ['$resource', function($resource) {
    return $resource('/whoami-rs/rest/glossary/', {id:'@id'});
  }]);

services.factory('GlossaryTerm', ['$resource', function($resource) {
    return $resource('/whoami-rs/rest/glossary/:id/:term', {id:'@id', term: ''});
  }]);

services.factory('LoadGlossary', ['Glossary', '$q',
    function(Glossary, $q) {
        return function() {
            var deferred = $q.defer();
            Glossary.query({}, function(glossary) {
                deferred.resolve(glossary);
            },function(err) {
                deferred.reject(err);
            });
            return deferred.promise;
        };
    }]);

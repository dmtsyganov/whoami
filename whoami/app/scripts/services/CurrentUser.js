'use strict';

myApp
  .factory('CurrentUser', ['$rootScope', function($rootScope) {
        var LOCAL_STORAGE_ID = 'currentUser',
            currentUser = localStorage[LOCAL_STORAGE_ID];

        var customer = currentUser ? JSON.parse(currentUser) : {
            id: undefined,
            login: undefined,
            profile: {
                fullName: undefined,
                email: undefined,
                age: undefined
            }
        };

        $rootScope.$watch(function() { return customer; }, function() {
            localStorage[LOCAL_STORAGE_ID] = JSON.stringify(customer);
        }, true);

        return customer;
  }]);

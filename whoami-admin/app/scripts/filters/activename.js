'use strict';

angular.module('whoamiAdminApp')
  .filter('activename', [function () {
    return function (input) {
        if(input) {
            return 'Активно';
        } else {
            return 'Выключено';
        }
    };
  }]);

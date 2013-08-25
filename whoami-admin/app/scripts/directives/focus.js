'use strict';

angular.module('whoamiAdminApp')
  .directive('focus', [function () {
    return {
      link: function postLink(scope, element, attrs) {
        element[0].focus();
      }
    };
  }]);

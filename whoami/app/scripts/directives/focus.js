'use strict';

angular.module('whoamiApp')
  .directive('focus', [function () {
    return {
        link: function postLink(scope, element, attrs) {
            element[0].focus();
        }
    };
  }]);

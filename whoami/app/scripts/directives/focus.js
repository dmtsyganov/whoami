'use strict';

myApp
    .directive('focus', [function () {
    return {
        link: function postLink(scope, element, attrs) {
            element[0].focus();
        }
    };
  }]);

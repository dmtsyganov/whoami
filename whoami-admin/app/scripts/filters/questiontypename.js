'use strict';

angular.module('whoamiAdminApp')
  .filter('questiontypename', ['questiontype', function (questiontype) {
    return function (input) {
        if(!input) {
            return '';
        }

        var name = input;
        var length = questiontype.length;
        for(var i = 0; i < length; i++) {
            if(input.localeCompare(questiontype[i].name) === 0) {
                name = questiontype[i].description;
                break;
            }
        }
        return name;
    };
  }]);

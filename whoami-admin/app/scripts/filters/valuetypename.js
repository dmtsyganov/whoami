'use strict';

myApp
  .filter('valuetypename', ['valuetype', function (valuetype) {
    return function (input) {
        if(!input) {
            return '';
        }

        var name = input;
        var length = valuetype.length;
        for(var i = 0; i < length; i++) {
            if(input.localeCompare(valuetype[i].name) === 0) {
                name = valuetype[i].description;
                break;
            }
        }
        return name;
    };
  }]);

myApp
    .filter('valueeffectname', [function () {
        return function (input) {

            if(typeof input === 'undefined') {
                return '';
            }

            var name = '-';

            if(input > 0) {
               name = '+';
            }

            return name;
        };
    }]);

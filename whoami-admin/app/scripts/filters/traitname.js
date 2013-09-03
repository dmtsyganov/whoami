'use strict';

myApp
  .filter('traitname', ['alltraits', function (alltraits) {
    return function (input) {
        if(!input) {
            return '';
        }

        var name = input;
        var length = alltraits.length;
        for(var i = 0; i < length; i++) {
            if(input.localeCompare(alltraits[i].name) === 0) {
                name = alltraits[i].description;
                break;
            }
        }
        return name;
    };
  }]);

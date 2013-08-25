'use strict';

angular.module('whoamiAdminApp')
  .factory('templates', ['$resource', function($resource) {

        return $resource('/whoami-rs/rest/templates/');
/*
    var interviewTemplates = [
        {id: 0, name:"Interview 1", description:"This is the interview number One"},
        {id: 1, name:"Interview 2", description:"This is the interview number Two"},
        {id: 2, name:"Interview 3", description:"This is the interview number Three"}
    ];

  return interviewTemplates;
*/

}]);

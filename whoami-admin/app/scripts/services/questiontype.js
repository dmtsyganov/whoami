'use strict';

angular.module('whoamiAdminApp')
  .constant('questiontype',
        [
            {name:'DIRECT', description: 'прямой'},
            {name:'INDIRECT', description: 'косвенный'},
            {name:'INFORMATION', description: 'информационный'}
        ]
    );
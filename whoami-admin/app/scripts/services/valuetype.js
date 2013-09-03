'use strict';

angular.module('whoamiAdminApp')
  .constant('valuetype',
        [
            {name:'YES_NO', description: 'да/нет'},
            {name:'SCORE', description: 'оценка'},
            {name:'TEXT', description: 'текст'}
        ]
    );
'use strict';

myApp
    .factory('CurrentInterview', [function () {
        // This is the service that holds current interview that can be shared among controllers
        return {
            // current user
            userId: null,
            // current interview template
            templateId: null,
            // current interview
            interview: null,
            // initialize
            setCurrentInterview: function (userId, templateId, interview) {
                this.userId = userId;
                this.templateId = templateId;
                this.interview = interview;
            },
            // reset the state
            clear: function () {
                this.userId = null;
                this.templateId = null;
                this.interview = null;
            }
        }
    }]);

'use strict';

myApp
    .controller('UserCtrl', ['$scope', 'CurrentUser', 'User', 'templates', 'interviews', 'Interview',
        function ($scope, CurrentUser, User, templates, interviews, Interview ) {

        // TODO: use user from resolve
        $scope.user = CurrentUser;
        $scope.password_confirmation = CurrentUser.password;
        $scope.templates = templates;
        $scope.interviews = interviews;

        // update user profile
        $scope.update = function() {
            User.get({id: $scope.user.id, login: null, password: null}, function(user){
                user.password = $scope.user.password;
                if(!user.profile) {
                    user.profile = {};
                }
                user.profile.fullName = $scope.user.profile.fullName;
                user.profile.email = $scope.user.profile.email;
                user.profile.age = $scope.user.profile.age;
                user.profile.dateUpdated = new Date();

                user.$save({id: $scope.user.id, login: null, password: null}, function(user) {
                    console.log("User profile saved");
                }, function(response) {
                    console.log("Error update failed: " + response.toString());
/*
                    $scope.alerts.push({
                        type: 'error',
                        msg: "Update failed!"
                    });
*/
                });
            });
        };

        // work with the interviews
        $scope.isNew = function(index) {
            var templateId = $scope.templates[index].id;
            console.log("is new " + templateId.toString());

            for(var i = 0; i < $scope.interviews.length; i++) {
                if($scope.interviews[i].templateIdString === templateId) {
                    return false;
                }
            }

            return true;
        };

        // Create new interview and start it
        $scope.startInterview = function(index) {
            var templateId = $scope.templates[index].id;
            var userId = $scope.user.id;
            console.log("start new for user " + userId + " and template " + templateId.toString());

            var newInterview = new Interview({
                userId: userId,
                templateId: templateId,
                startDate: new Date(),
                endDate: new Date()
            });

            newInterview.$save({userId: userId, templateId: templateId}, function(interview) {
                console.log("New interview created");

            }, function(response) {
                console.log("Error creating new interview ", response.toString());

                /*
                 $scope.alerts.push({
                 type: 'error',
                 msg: "Update failed!"
                 });
                 */
            });

        };

        $scope.continueInterview = function(index) {
            var id = $scope.templates[index].id;
            console.log("continue " + id.toString());

        };

        $scope.viewResults = function(index) {
            var id = $scope.templates[index].id;
            console.log("view " + id.toString());

        }

  }]);


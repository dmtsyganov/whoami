'use strict';

myApp
    .controller('UserCtrl', ['$scope', 'CurrentUser', 'User', 'templates',
        function ($scope, CurrentUser, User, templates) {

        // TODO: use user from resolve
        $scope.user = CurrentUser;
        $scope.password_confirmation = CurrentUser.password;
        $scope.templates = templates;

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
  }]);


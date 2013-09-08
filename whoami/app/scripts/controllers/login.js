'use strict';

myApp
  .controller('LoginCtrl', ['$scope', '$location', 'User', function ($scope, $location, User) {

        // alerts
        $scope.alerts = [];

        $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
        };

        $scope.login = function(username, password) {
            console.log("User: " + username + " password " + password);

            //var user = LoginUser(username, password);

            User.get({id: null, login: username, password: password}, function(user) {
                console.log("Logged user " + user.toString());
                $location.path('/user/' + user.id);
            }, function(response) {
                if(response.status === 403) {
                    // forbidden
                    $scope.alerts.push({
                        type: 'error',
                        msg: "Вы ввели неправильное имя или пароль!"
                    });
                } else if(response.status === 404) {
                    // not found
                    $scope.alerts.push({
                        type: 'error',
                        msg: "Вы ввели неправильное имя пользователя!"
                    });
                } else {
                    console.log("Response error: " + response.toString());
                    $scope.alerts.push({
                        type: 'error',
                        msg: "Не удалось найти запись пользователя!"
                    });
                }
            });
        };

        $scope.create = function() {
            var newUser = new User ({
                login: $scope.newusername,
                password: $scope.new_password,
                role: 'USER',
                profile: {
                    fullName: $scope.fullName,
                    email: $scope.email,
                    age: 0,
                    dateCreated: new Date(),
                    dateUpdated: new Date()
                }
            });

            newUser.$save({id: null, login: null, password: null}, function(user) {
                $location.path('/user/' + user.id);
            }, function(response) {
                if(response.status === 409) {
                    // forbidden
                    $scope.alerts.push({
                        type: 'error',
                        msg: "Пользователь с данным именем уже зарегистрирован!"
                    });
                } else {
                    console.log("Response error: " + response.toString());
                    $scope.alerts.push({
                        type: 'error',
                        msg: "Не удалось создать запись пользователя!"
                    });
                }
            });
            console.log("New User");
        };

        $scope.cancel = function() {
            console.log("Cancel");
            $location.path("/");
        };
  }]);

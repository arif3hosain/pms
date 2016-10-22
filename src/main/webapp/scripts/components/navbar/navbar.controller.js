'use strict';

angular.module('pmsApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };

        var copyAccount = function (account) {
                console.log(account);
                    return {
                        activated: account.activated,
                        email: account.email,
                        firstName: account.firstName,
                        langKey: account.langKey,
                        lastName: account.lastName,
                        login: account.login
                    }
                }
    });

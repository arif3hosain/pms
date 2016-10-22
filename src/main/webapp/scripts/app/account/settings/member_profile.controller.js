'use strict';

angular.module('pmsApp')
    .controller('MemberProfileController', function ($scope, Principal, Auth) {
        $scope.success = null;
        $scope.error = null;
        Principal.identity().then(function(account) {
            $scope.settingsAccount = copyAccount(account);

        });

        $scope.edit=false;
        $scope.edit_profile=function(){
        $scope.edit=true;
        };

        $scope.save = function () {
        $scope.edit=false;
            Auth.updateAccount($scope.settingsAccount).then(function() {
            console.log($scope.settingsAccount);
            console.log($scope.settingsAccount.mobile);
                $scope.error = null;
                $scope.success = 'OK';
                $scope.mobile=null;
                Principal.identity(true).then(function(account) {
                    $scope.settingsAccount = copyAccount(account);

                });
            }).catch(function() {
                $scope.success = null;
                $scope.error = 'ERROR';
            });
        };
        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {

            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login,
                mobile:account.mobile,
                address1:account.address1,
                address2:account.address2,
                dob:account.dob,
                gender:account.gender,
                country:account.country,
            }
        }
    });

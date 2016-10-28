'use strict';

angular.module('pmsApp')
    .controller('AddMemberController', function ($scope, $timeout, Auth) {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});
        $scope.comId;
//        GetCompanyByUserId.query({id:3},function(result,headers){
//             $scope.comId;=result.id;
//             console.log('company id'+3)
//        });
        $scope.register = function () {
        $scope.registerAccount.comId=3;
            if ($scope.registerAccount.password !== $scope.confirmPassword) {

                $scope.doNotMatch = 'ERROR';
            } else {
                console.log('..........');
                            console.log($scope.registerAccount);
                $scope.registerAccount.langKey =  'en' ;
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;

                Auth.createAccount($scope.registerAccount).then(function () {
                    $scope.success = 'OK';
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };
    });

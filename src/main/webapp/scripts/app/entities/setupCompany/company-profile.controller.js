'use strict';

angular.module('pmsApp').controller('CompanyProfileController',
    ['$scope', '$stateParams',  '$q', 'DataUtils',  'SetupCompany', 'User', 'Country','GetCompanyByUserId','Auth', 'Principal',
        function($scope, $stateParams,  $q, DataUtils,  SetupCompany, User, Country,GetCompanyByUserId,Auth, Principal) {

//        $scope.setupCompany = entity;
        $scope.users = User.query();
        $scope.countrys = Country.query({size:500});
        $scope.load = function(id) {
            SetupCompany.get({id : id}, function(result) {
                $scope.setupCompany = result;
            });
        };

         $scope.setupCompany;
         Principal.identity().then(function(account) {
         User.get({login: account.login}, function(result, headers){
         console.log(result.id);
         GetCompanyByUserId.query({id:result.id },function(result,headers){
                     $scope.setupCompany=result;
                     console.log(result);
                });
         });

        });




        var onSaveSuccess = function (result) {
            $scope.$emit('pmsApp:setupCompanyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.setupCompany.id != null) {
                SetupCompany.update($scope.setupCompany, onSaveSuccess, onSaveError);
            } else {
                SetupCompany.save($scope.setupCompany, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setCompanyLogo = function ($file, setupCompany) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        setupCompany.companyLogo = base64Data;
                        setupCompany.companyLogoContentType = $file.type;
                    });
                };
            }
        };
        $scope.datePickerForCreatedDate = {};

        $scope.datePickerForCreatedDate.status = {
            opened: false
        };

        $scope.datePickerForCreatedDateOpen = function($event) {
            $scope.datePickerForCreatedDate.status.opened = true;
        };
        $scope.datePickerForUpdatedDate = {};

        $scope.datePickerForUpdatedDate.status = {
            opened: false
        };

        $scope.datePickerForUpdatedDateOpen = function($event) {
            $scope.datePickerForUpdatedDate.status.opened = true;
        };
}]);

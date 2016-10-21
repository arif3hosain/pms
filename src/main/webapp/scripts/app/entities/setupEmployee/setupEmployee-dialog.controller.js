'use strict';

angular.module('pmsApp').controller('SetupEmployeeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'SetupEmployee', 'User', 'SetupCompany',
        function($scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, SetupEmployee, User, SetupCompany) {

        $scope.setupEmployee = entity;
        $scope.users = User.query();
        $scope.setupcompanys = SetupCompany.query();
        $scope.load = function(id) {
            SetupEmployee.get({id : id}, function(result) {
                $scope.setupEmployee = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pmsApp:setupEmployeeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.setupEmployee.id != null) {
                SetupEmployee.update($scope.setupEmployee, onSaveSuccess, onSaveError);
            } else {
                SetupEmployee.save($scope.setupEmployee, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
        $scope.datePickerForJoiningDate = {};

        $scope.datePickerForJoiningDate.status = {
            opened: false
        };

        $scope.datePickerForJoiningDateOpen = function($event) {
            $scope.datePickerForJoiningDate.status.opened = true;
        };

        $scope.setProfilePicture = function ($file, setupEmployee) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        setupEmployee.profilePicture = base64Data;
                        setupEmployee.profilePictureContentType = $file.type;
                    });
                };
            }
        };
        $scope.datePickerForDateOfBirth = {};

        $scope.datePickerForDateOfBirth.status = {
            opened: false
        };

        $scope.datePickerForDateOfBirthOpen = function($event) {
            $scope.datePickerForDateOfBirth.status.opened = true;
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

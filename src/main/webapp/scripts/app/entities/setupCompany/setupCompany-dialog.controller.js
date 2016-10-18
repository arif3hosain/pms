'use strict';

angular.module('pmsApp').controller('SetupCompanyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SetupCompany',
        function($scope, $stateParams, $uibModalInstance, entity, SetupCompany) {

        $scope.setupCompany = entity;
        $scope.load = function(id) {
            SetupCompany.get({id : id}, function(result) {
                $scope.setupCompany = result;
            });
        };

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

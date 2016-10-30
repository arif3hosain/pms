'use strict';

angular.module('pmsApp').controller('SetupClientDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SetupClient', 'SetupCompany', 'Country',
        function($scope, $stateParams, $uibModalInstance, entity, SetupClient, SetupCompany, Country) {

        $scope.setupClient = entity;
        $scope.setupcompanys = SetupCompany.query();
        $scope.countrys = Country.query();
        $scope.load = function(id) {
            SetupClient.get({id : id}, function(result) {
                $scope.setupClient = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pmsApp:setupClientUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.setupClient.id != null) {
                SetupClient.update($scope.setupClient, onSaveSuccess, onSaveError);
            } else {
                SetupClient.save($scope.setupClient, onSaveSuccess, onSaveError);
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

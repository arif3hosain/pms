'use strict';

angular.module('pmsApp').controller('SetupCurrencyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SetupCurrency', 'Country',
        function($scope, $stateParams, $uibModalInstance, entity, SetupCurrency, Country) {

        $scope.setupCurrency = entity;
        $scope.countrys = Country.query();
        $scope.load = function(id) {
            SetupCurrency.get({id : id}, function(result) {
                $scope.setupCurrency = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pmsApp:setupCurrencyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.setupCurrency.id != null) {
                SetupCurrency.update($scope.setupCurrency, onSaveSuccess, onSaveError);
            } else {
                SetupCurrency.save($scope.setupCurrency, onSaveSuccess, onSaveError);
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

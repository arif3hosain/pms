'use strict';

angular.module('pmsApp').controller('UmsRoleMstDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'UmsRoleMst',
        function($scope, $stateParams, $uibModalInstance, entity, UmsRoleMst) {

        $scope.umsRoleMst = entity;
        $scope.load = function(id) {
            UmsRoleMst.get({id : id}, function(result) {
                $scope.umsRoleMst = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pmsApp:umsRoleMstUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.umsRoleMst.id != null) {
                UmsRoleMst.update($scope.umsRoleMst, onSaveSuccess, onSaveError);
            } else {
                UmsRoleMst.save($scope.umsRoleMst, onSaveSuccess, onSaveError);
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

'use strict';

angular.module('pmsApp').controller('UmsMenuMainDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'UmsMenuMain',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, UmsMenuMain) {

        $scope.umsMenuMain = entity;
        $scope.load = function(id) {
            UmsMenuMain.get({id : id}, function(result) {
                $scope.umsMenuMain = result;
            });
        };
        $scope.menus=UmsMenuMain.query({size:100});
        console.log($scope.menus);


        var onSaveSuccess = function (result) {
            $scope.$emit('pmsApp:umsMenuMainUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        $scope.changedValue=function(data){
            console.log(data);
        };
        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.umsMenuMain.id != null) {
                UmsMenuMain.update($scope.umsMenuMain, onSaveSuccess, onSaveError);
            } else {
                UmsMenuMain.save($scope.umsMenuMain, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setPrevIconLevel = function ($file, umsMenuMain) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        umsMenuMain.prevIconLevel = base64Data;
                        umsMenuMain.prevIconLevelContentType = $file.type;
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

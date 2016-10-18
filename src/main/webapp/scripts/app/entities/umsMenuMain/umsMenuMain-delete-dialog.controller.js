'use strict';

angular.module('pmsApp')
	.controller('UmsMenuMainDeleteController', function($scope, $uibModalInstance, entity, UmsMenuMain) {

        $scope.umsMenuMain = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            UmsMenuMain.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });

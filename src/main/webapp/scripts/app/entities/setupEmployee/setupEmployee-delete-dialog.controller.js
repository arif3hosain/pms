'use strict';

angular.module('pmsApp')
	.controller('SetupEmployeeDeleteController', function($scope, $uibModalInstance, entity, SetupEmployee) {

        $scope.setupEmployee = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SetupEmployee.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });

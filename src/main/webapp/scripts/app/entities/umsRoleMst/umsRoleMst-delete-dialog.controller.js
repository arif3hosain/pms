'use strict';

angular.module('pmsApp')
	.controller('UmsRoleMstDeleteController', function($scope, $uibModalInstance, entity, UmsRoleMst) {

        $scope.umsRoleMst = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            UmsRoleMst.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });

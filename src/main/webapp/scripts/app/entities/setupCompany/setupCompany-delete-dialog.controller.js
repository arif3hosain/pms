'use strict';

angular.module('pmsApp')
	.controller('SetupCompanyDeleteController', function($scope, $uibModalInstance, entity, SetupCompany) {

        $scope.setupCompany = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SetupCompany.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });

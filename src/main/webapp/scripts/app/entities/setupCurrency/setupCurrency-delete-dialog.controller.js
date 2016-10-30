'use strict';

angular.module('pmsApp')
	.controller('SetupCurrencyDeleteController', function($scope, $uibModalInstance, entity, SetupCurrency) {

        $scope.setupCurrency = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SetupCurrency.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });

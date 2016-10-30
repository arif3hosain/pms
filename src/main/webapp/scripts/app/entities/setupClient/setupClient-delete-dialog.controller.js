'use strict';

angular.module('pmsApp')
	.controller('SetupClientDeleteController', function($scope, $uibModalInstance, entity, SetupClient) {

        $scope.setupClient = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SetupClient.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });

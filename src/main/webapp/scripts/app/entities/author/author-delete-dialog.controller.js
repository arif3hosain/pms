'use strict';

angular.module('pmsApp')
	.controller('AuthorDeleteController', function($scope, $uibModalInstance, entity, Author) {

        $scope.author = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Author.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });

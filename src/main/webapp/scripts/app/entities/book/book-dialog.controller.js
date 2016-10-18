'use strict';

angular.module('pmsApp').controller('BookDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Book', 'Author',
        function($scope, $stateParams, $uibModalInstance, entity, Book, Author) {

        $scope.book = entity;
        $scope.authors = Author.query();
        $scope.load = function(id) {
            Book.get({id : id}, function(result) {
                $scope.book = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pmsApp:bookUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.book.id != null) {
                Book.update($scope.book, onSaveSuccess, onSaveError);
            } else {
                Book.save($scope.book, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);

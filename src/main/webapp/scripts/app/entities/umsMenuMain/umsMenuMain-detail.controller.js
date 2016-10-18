'use strict';

angular.module('pmsApp')
    .controller('UmsMenuMainDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, UmsMenuMain) {
        $scope.umsMenuMain = entity;
        $scope.load = function (id) {
            UmsMenuMain.get({id: id}, function(result) {
                $scope.umsMenuMain = result;
            });
        };
        var unsubscribe = $rootScope.$on('pmsApp:umsMenuMainUpdate', function(event, result) {
            $scope.umsMenuMain = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });

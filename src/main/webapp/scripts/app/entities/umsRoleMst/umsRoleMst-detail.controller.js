'use strict';

angular.module('pmsApp')
    .controller('UmsRoleMstDetailController', function ($scope, $rootScope, $stateParams, entity, UmsRoleMst) {
        $scope.umsRoleMst = entity;
        $scope.load = function (id) {
            UmsRoleMst.get({id: id}, function(result) {
                $scope.umsRoleMst = result;
            });
        };
        var unsubscribe = $rootScope.$on('pmsApp:umsRoleMstUpdate', function(event, result) {
            $scope.umsRoleMst = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });

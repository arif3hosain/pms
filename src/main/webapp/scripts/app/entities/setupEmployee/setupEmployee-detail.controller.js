'use strict';

angular.module('pmsApp')
    .controller('SetupEmployeeDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, SetupEmployee, User, SetupCompany) {
        $scope.setupEmployee = entity;
        $scope.load = function (id) {
            SetupEmployee.get({id: id}, function(result) {
                $scope.setupEmployee = result;
            });
        };
        var unsubscribe = $rootScope.$on('pmsApp:setupEmployeeUpdate', function(event, result) {
            $scope.setupEmployee = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });

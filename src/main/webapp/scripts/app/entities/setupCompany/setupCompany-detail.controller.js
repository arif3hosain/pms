'use strict';

angular.module('pmsApp')
    .controller('SetupCompanyDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, SetupCompany, User) {
        $scope.setupCompany = entity;
        $scope.load = function (id) {
            SetupCompany.get({id: id}, function(result) {
                $scope.setupCompany = result;
            });
        };
        var unsubscribe = $rootScope.$on('pmsApp:setupCompanyUpdate', function(event, result) {
            $scope.setupCompany = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });

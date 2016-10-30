'use strict';

angular.module('pmsApp')
    .controller('SetupClientDetailController', function ($scope, $rootScope, $stateParams, entity, SetupClient, SetupCompany, Country) {
        $scope.setupClient = entity;
        $scope.load = function (id) {
            SetupClient.get({id: id}, function(result) {
                $scope.setupClient = result;
            });
        };
        var unsubscribe = $rootScope.$on('pmsApp:setupClientUpdate', function(event, result) {
            $scope.setupClient = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });

'use strict';

angular.module('pmsApp')
    .controller('SetupCurrencyDetailController', function ($scope, $rootScope, $stateParams, entity, SetupCurrency, Country) {
        $scope.setupCurrency = entity;
        $scope.load = function (id) {
            SetupCurrency.get({id: id}, function(result) {
                $scope.setupCurrency = result;
            });
        };
        var unsubscribe = $rootScope.$on('pmsApp:setupCurrencyUpdate', function(event, result) {
            $scope.setupCurrency = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });

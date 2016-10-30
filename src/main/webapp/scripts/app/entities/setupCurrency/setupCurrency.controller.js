'use strict';

angular.module('pmsApp')
    .controller('SetupCurrencyController', function ($scope, $state, SetupCurrency, SetupCurrencySearch, ParseLinks) {

        $scope.setupCurrencys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SetupCurrency.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.setupCurrencys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SetupCurrencySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.setupCurrencys = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.setupCurrency = {
                curCode: null,
                currencyName: null,
                currSymbol: null,
                symbol: null,
                status: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                id: null
            };
        };
    });

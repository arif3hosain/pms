'use strict';

angular.module('pmsApp')
    .controller('SetupClientController', function ($scope, $state, SetupClient, SetupClientSearch, ParseLinks) {

        $scope.setupClients = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SetupClient.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.setupClients = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SetupClientSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.setupClients = result;
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
            $scope.setupClient = {
                name: null,
                website: null,
                industryName: null,
                clientType: null,
                custVatChalCode: null,
                custVatRegNo: null,
                status: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                id: null
            };
        };
    });

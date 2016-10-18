'use strict';

angular.module('pmsApp')
    .controller('UmsMenuMainController', function ($scope, $state, DataUtils, UmsMenuMain, UmsMenuMainSearch, ParseLinks) {

        $scope.umsMenuMains = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            UmsMenuMain.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.umsMenuMains = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            UmsMenuMainSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.umsMenuMains = result;
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
            $scope.umsMenuMain = {
                menuName: null,
                menuType: null,
                RunFileName: null,
                MenuSl: null,
                ParentId: null,
                homeDir: null,
                remarks: null,
                menuLevel: null,
                prevIconLevel: null,
                prevIconLevelContentType: null,
                status: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    });

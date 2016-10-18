'use strict';

angular.module('pmsApp')
    .controller('SetupCompanyController', function ($scope, $state, SetupCompany, SetupCompanySearch, ParseLinks) {

        $scope.setupCompanys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SetupCompany.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.setupCompanys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SetupCompanySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.setupCompanys = result;
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
            $scope.setupCompany = {
                companyCode: null,
                name: null,
                add1: null,
                add2: null,
                phone1: null,
                phone2: null,
                fax: null,
                email: null,
                vatno1: null,
                web: null,
                tin: null,
                csymbol: null,
                secuse: null,
                bcsymbol: null,
                cfname: null,
                status: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                id: null
            };
        };
    });

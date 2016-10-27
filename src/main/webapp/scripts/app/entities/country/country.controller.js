'use strict';

angular.module('pmsApp')
    .controller('CountryController', function ($scope, $state, Country, CountrySearch, ParseLinks) {

        $scope.countrys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Country.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.countrys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CountrySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.countrys = result;
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
            $scope.country = {
                countryCode: null,
                isoCode2: null,
                name: null,
                continent: null,
                region: null,
                surfaceArea: null,
                capital: null,
                headOfState: null,
                callingCode: null,
                id: null
            };
        };
    });

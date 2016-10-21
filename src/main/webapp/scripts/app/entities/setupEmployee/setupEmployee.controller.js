'use strict';

angular.module('pmsApp')
    .controller('SetupEmployeeController', function ($scope, $state, DataUtils, SetupEmployee, SetupEmployeeSearch, ParseLinks) {

        $scope.setupEmployees = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SetupEmployee.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.setupEmployees = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SetupEmployeeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.setupEmployees = result;
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
            $scope.setupEmployee = {
                empCode: null,
                employeeTitle: null,
                employeeName: null,
                joiningDate: null,
                stdWorkingHour: null,
                overTime: null,
                tinNo: null,
                deptId: null,
                applicationId: null,
                profilePicture: null,
                profilePictureContentType: null,
                superiorId: null,
                degCode: null,
                employeeType: null,
                bloodGroup: null,
                phone: null,
                address: null,
                hourlyRate: null,
                fixedSalary: null,
                dateOfBirth: null,
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

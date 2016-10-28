'use strict';

angular.module('pmsApp').controller('MemberListController',
    ['$scope', '$stateParams', '$q', 'DataUtils', 'SetupCompany', 'User', 'Country','GetUsersByCompanyId',
        function($scope, $stateParams,$q, DataUtils,  SetupCompany, User, Country,GetUsersByCompanyId) {

//        $scope.setupCompany = entity;
//        $scope.users = User.query();
//        $scope.countrys = Country.query();

//        $scope.load = function(id) {
//            SetupCompany.get({id : id}, function(result) {
//                $scope.setupCompany = result;
//            });
//        };

$scope.users;
GetUsersByCompanyId.query({comId: 3}, function(result, headers){
console.log(result);
$scope.users=result;
});



}]);

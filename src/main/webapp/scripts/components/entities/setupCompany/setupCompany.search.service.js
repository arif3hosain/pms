'use strict';

angular.module('pmsApp')
    .factory('SetupCompanySearch', function ($resource) {
        return $resource('api/_search/setupCompanys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('GetCompanyByUserId', function ($resource) {
        return $resource('api/setupCompanys/user/:id', {}, {
            'query': { method: 'GET', isArray: false}
        });
    });

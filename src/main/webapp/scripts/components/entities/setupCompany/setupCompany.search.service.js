'use strict';

angular.module('pmsApp')
    .factory('SetupCompanySearch', function ($resource) {
        return $resource('api/_search/setupCompanys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

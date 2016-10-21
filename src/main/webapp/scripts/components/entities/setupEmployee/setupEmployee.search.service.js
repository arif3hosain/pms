'use strict';

angular.module('pmsApp')
    .factory('SetupEmployeeSearch', function ($resource) {
        return $resource('api/_search/setupEmployees/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

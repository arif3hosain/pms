'use strict';

angular.module('pmsApp')
    .factory('UmsRoleMstSearch', function ($resource) {
        return $resource('api/_search/umsRoleMsts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

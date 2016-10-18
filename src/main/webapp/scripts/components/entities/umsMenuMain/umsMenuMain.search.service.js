'use strict';

angular.module('pmsApp')
    .factory('UmsMenuMainSearch', function ($resource) {
        return $resource('api/_search/umsMenuMains/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

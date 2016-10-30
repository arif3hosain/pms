'use strict';

angular.module('pmsApp')
    .factory('SetupCurrencySearch', function ($resource) {
        return $resource('api/_search/setupCurrencys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

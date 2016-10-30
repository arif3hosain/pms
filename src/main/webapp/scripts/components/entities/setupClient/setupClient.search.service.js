'use strict';

angular.module('pmsApp')
    .factory('SetupClientSearch', function ($resource) {
        return $resource('api/_search/setupClients/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

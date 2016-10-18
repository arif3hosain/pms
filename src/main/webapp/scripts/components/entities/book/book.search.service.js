'use strict';

angular.module('pmsApp')
    .factory('BookSearch', function ($resource) {
        return $resource('api/_search/books/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

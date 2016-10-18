'use strict';

angular.module('pmsApp')
    .factory('AuthorSearch', function ($resource) {
        return $resource('api/_search/authors/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

'use strict';

angular.module('pmsApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });



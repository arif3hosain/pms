'use strict';

angular.module('pmsApp')
    .factory('Author', function ($resource, DateUtils) {
        return $resource('api/authors/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateOfBirth = DateUtils.convertLocaleDateFromServer(data.dateOfBirth);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateOfBirth = DateUtils.convertLocaleDateToServer(data.dateOfBirth);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateOfBirth = DateUtils.convertLocaleDateToServer(data.dateOfBirth);
                    return angular.toJson(data);
                }
            }
        });
    });

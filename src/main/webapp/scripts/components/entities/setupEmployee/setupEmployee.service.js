'use strict';

angular.module('pmsApp')
    .factory('SetupEmployee', function ($resource, DateUtils) {
        return $resource('api/setupEmployees/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.joiningDate = DateUtils.convertLocaleDateFromServer(data.joiningDate);
                    data.dateOfBirth = DateUtils.convertLocaleDateFromServer(data.dateOfBirth);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.dateOfBirth = DateUtils.convertLocaleDateToServer(data.dateOfBirth);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.dateOfBirth = DateUtils.convertLocaleDateToServer(data.dateOfBirth);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    });

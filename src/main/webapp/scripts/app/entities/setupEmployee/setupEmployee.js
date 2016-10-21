'use strict';

angular.module('pmsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('setupEmployee', {
                parent: 'entity',
                url: '/setupEmployees',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SetupEmployees'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/setupEmployee/setupEmployees.html',
                        controller: 'SetupEmployeeController'
                    }
                },
                resolve: {
                }
            })
            .state('setupEmployee.detail', {
                parent: 'entity',
                url: '/setupEmployee/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SetupEmployee'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/setupEmployee/setupEmployee-detail.html',
                        controller: 'SetupEmployeeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SetupEmployee', function($stateParams, SetupEmployee) {
                        return SetupEmployee.get({id : $stateParams.id});
                    }]
                }
            })
            .state('setupEmployee.new', {
                parent: 'setupEmployee',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupEmployee/setupEmployee-dialog.html',
                        controller: 'SetupEmployeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    empCode: null,
                                    employeeTitle: null,
                                    employeeName: null,
                                    joiningDate: null,
                                    stdWorkingHour: null,
                                    overTime: null,
                                    tinNo: null,
                                    deptId: null,
                                    applicationId: null,
                                    profilePicture: null,
                                    profilePictureContentType: null,
                                    superiorId: null,
                                    degCode: null,
                                    employeeType: null,
                                    bloodGroup: null,
                                    phone: null,
                                    address: null,
                                    hourlyRate: null,
                                    fixedSalary: null,
                                    dateOfBirth: null,
                                    status: null,
                                    createdDate: null,
                                    updatedDate: null,
                                    createdBy: null,
                                    updatedBy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('setupEmployee', null, { reload: true });
                    }, function() {
                        $state.go('setupEmployee');
                    })
                }]
            })
            .state('setupEmployee.edit', {
                parent: 'setupEmployee',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupEmployee/setupEmployee-dialog.html',
                        controller: 'SetupEmployeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SetupEmployee', function(SetupEmployee) {
                                return SetupEmployee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setupEmployee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('setupEmployee.delete', {
                parent: 'setupEmployee',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupEmployee/setupEmployee-delete-dialog.html',
                        controller: 'SetupEmployeeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SetupEmployee', function(SetupEmployee) {
                                return SetupEmployee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setupEmployee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

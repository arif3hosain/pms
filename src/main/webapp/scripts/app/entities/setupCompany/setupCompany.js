'use strict';

angular.module('pmsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('setupCompany', {
                parent: 'entity',
                url: '/setupCompanys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SetupCompanys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/setupCompany/setupCompanys.html',
                        controller: 'SetupCompanyController'
                    }
                },
                resolve: {
                }
            })
            .state('setupCompany.detail', {
                parent: 'entity',
                url: '/setupCompany/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SetupCompany'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/setupCompany/setupCompany-detail.html',
                        controller: 'SetupCompanyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SetupCompany', function($stateParams, SetupCompany) {
                        return SetupCompany.get({id : $stateParams.id});
                    }]
                }
            })
            .state('setupCompany.new', {
                parent: 'setupCompany',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupCompany/setupCompany-dialog.html',
                        controller: 'SetupCompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    companyCode: null,
                                    name: null,
                                    add1: null,
                                    add2: null,
                                    phone1: null,
                                    phone2: null,
                                    fax: null,
                                    email: null,
                                    vatno1: null,
                                    web: null,
                                    tin: null,
                                    csymbol: null,
                                    secuse: null,
                                    bcsymbol: null,
                                    cfname: null,
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
                        $state.go('setupCompany', null, { reload: true });
                    }, function() {
                        $state.go('setupCompany');
                    })
                }]
            })
            .state('setupCompany.edit', {
                parent: 'setupCompany',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupCompany/setupCompany-dialog.html',
                        controller: 'SetupCompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SetupCompany', function(SetupCompany) {
                                return SetupCompany.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setupCompany', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('setupCompany.delete', {
                parent: 'setupCompany',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupCompany/setupCompany-delete-dialog.html',
                        controller: 'SetupCompanyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SetupCompany', function(SetupCompany) {
                                return SetupCompany.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setupCompany', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

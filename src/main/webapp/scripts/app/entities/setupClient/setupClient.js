'use strict';

angular.module('pmsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('setupClient', {
                parent: 'entity',
                url: '/setupClients',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SetupClients'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/setupClient/setupClients.html',
                        controller: 'SetupClientController'
                    }
                },
                resolve: {
                }
            })
            .state('setupClient.detail', {
                parent: 'entity',
                url: '/setupClient/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SetupClient'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/setupClient/setupClient-detail.html',
                        controller: 'SetupClientDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SetupClient', function($stateParams, SetupClient) {
                        return SetupClient.get({id : $stateParams.id});
                    }]
                }
            })
            .state('setupClient.new', {
                parent: 'setupClient',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupClient/setupClient-dialog.html',
                        controller: 'SetupClientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    website: null,
                                    industryName: null,
                                    clientType: null,
                                    custVatChalCode: null,
                                    custVatRegNo: null,
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
                        $state.go('setupClient', null, { reload: true });
                    }, function() {
                        $state.go('setupClient');
                    })
                }]
            })
            .state('setupClient.edit', {
                parent: 'setupClient',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupClient/setupClient-dialog.html',
                        controller: 'SetupClientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SetupClient', function(SetupClient) {
                                return SetupClient.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setupClient', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('setupClient.delete', {
                parent: 'setupClient',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupClient/setupClient-delete-dialog.html',
                        controller: 'SetupClientDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SetupClient', function(SetupClient) {
                                return SetupClient.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setupClient', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

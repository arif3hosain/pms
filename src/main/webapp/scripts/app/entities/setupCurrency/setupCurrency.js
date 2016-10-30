'use strict';

angular.module('pmsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('setupCurrency', {
                parent: 'entity',
                url: '/setupCurrencys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SetupCurrencys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/setupCurrency/setupCurrencys.html',
                        controller: 'SetupCurrencyController'
                    }
                },
                resolve: {
                }
            })
            .state('setupCurrency.detail', {
                parent: 'entity',
                url: '/setupCurrency/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SetupCurrency'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/setupCurrency/setupCurrency-detail.html',
                        controller: 'SetupCurrencyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SetupCurrency', function($stateParams, SetupCurrency) {
                        return SetupCurrency.get({id : $stateParams.id});
                    }]
                }
            })
            .state('setupCurrency.new', {
                parent: 'setupCurrency',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupCurrency/setupCurrency-dialog.html',
                        controller: 'SetupCurrencyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    curCode: null,
                                    currencyName: null,
                                    currSymbol: null,
                                    symbol: null,
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
                        $state.go('setupCurrency', null, { reload: true });
                    }, function() {
                        $state.go('setupCurrency');
                    })
                }]
            })
            .state('setupCurrency.edit', {
                parent: 'setupCurrency',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupCurrency/setupCurrency-dialog.html',
                        controller: 'SetupCurrencyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SetupCurrency', function(SetupCurrency) {
                                return SetupCurrency.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setupCurrency', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('setupCurrency.delete', {
                parent: 'setupCurrency',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/setupCurrency/setupCurrency-delete-dialog.html',
                        controller: 'SetupCurrencyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SetupCurrency', function(SetupCurrency) {
                                return SetupCurrency.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setupCurrency', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

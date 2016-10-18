'use strict';

angular.module('pmsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umsRoleMst', {
                parent: 'entity',
                url: '/umsRoleMsts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'UmsRoleMsts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umsRoleMst/umsRoleMsts.html',
                        controller: 'UmsRoleMstController'
                    }
                },
                resolve: {
                }
            })
            .state('umsRoleMst.detail', {
                parent: 'entity',
                url: '/umsRoleMst/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'UmsRoleMst'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umsRoleMst/umsRoleMst-detail.html',
                        controller: 'UmsRoleMstDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'UmsRoleMst', function($stateParams, UmsRoleMst) {
                        return UmsRoleMst.get({id : $stateParams.id});
                    }]
                }
            })
            .state('umsRoleMst.new', {
                parent: 'umsRoleMst',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/umsRoleMst/umsRoleMst-dialog.html',
                        controller: 'UmsRoleMstDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    roleName: null,
                                    roleDesc: null,
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
                        $state.go('umsRoleMst', null, { reload: true });
                    }, function() {
                        $state.go('umsRoleMst');
                    })
                }]
            })
            .state('umsRoleMst.edit', {
                parent: 'umsRoleMst',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/umsRoleMst/umsRoleMst-dialog.html',
                        controller: 'UmsRoleMstDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UmsRoleMst', function(UmsRoleMst) {
                                return UmsRoleMst.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umsRoleMst', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('umsRoleMst.delete', {
                parent: 'umsRoleMst',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/umsRoleMst/umsRoleMst-delete-dialog.html',
                        controller: 'UmsRoleMstDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['UmsRoleMst', function(UmsRoleMst) {
                                return UmsRoleMst.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umsRoleMst', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

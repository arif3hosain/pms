'use strict';

angular.module('pmsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('umsMenuMain', {
                parent: 'entity',
                url: '/umsMenuMains',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'UmsMenuMains'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umsMenuMain/umsMenuMains.html',
                        controller: 'UmsMenuMainController'
                    }
                },
                resolve: {
                }
            })
            .state('umsMenuMain.detail', {
                parent: 'entity',
                url: '/umsMenuMain/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'UmsMenuMain'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/umsMenuMain/umsMenuMain-detail.html',
                        controller: 'UmsMenuMainDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'UmsMenuMain', function($stateParams, UmsMenuMain) {
                        return UmsMenuMain.get({id : $stateParams.id});
                    }]
                }
            })
            .state('umsMenuMain.new', {
                parent: 'umsMenuMain',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/umsMenuMain/umsMenuMain-dialog.html',
                        controller: 'UmsMenuMainDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    menuName: null,
                                    menuType: null,
                                    RunFileName: null,
                                    MenuSl: null,
                                    ParentId: null,
                                    homeDir: null,
                                    remarks: null,
                                    menuLevel: null,
                                    prevIconLevel: null,
                                    prevIconLevelContentType: null,
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
                        $state.go('umsMenuMain', null, { reload: true });
                    }, function() {
                        $state.go('umsMenuMain');
                    })
                }]
            })
            .state('umsMenuMain.edit', {
                parent: 'umsMenuMain',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/umsMenuMain/umsMenuMain-dialog.html',
                        controller: 'UmsMenuMainDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UmsMenuMain', function(UmsMenuMain) {
                                return UmsMenuMain.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umsMenuMain', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('umsMenuMain.delete', {
                parent: 'umsMenuMain',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/umsMenuMain/umsMenuMain-delete-dialog.html',
                        controller: 'UmsMenuMainDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['UmsMenuMain', function(UmsMenuMain) {
                                return UmsMenuMain.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('umsMenuMain', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

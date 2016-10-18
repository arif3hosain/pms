'use strict';

describe('Controller Tests', function() {

    describe('UmsRoleMst Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUmsRoleMst;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUmsRoleMst = jasmine.createSpy('MockUmsRoleMst');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UmsRoleMst': MockUmsRoleMst
            };
            createController = function() {
                $injector.get('$controller')("UmsRoleMstDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pmsApp:umsRoleMstUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

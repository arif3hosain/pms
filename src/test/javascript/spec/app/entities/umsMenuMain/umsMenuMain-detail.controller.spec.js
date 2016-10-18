'use strict';

describe('Controller Tests', function() {

    describe('UmsMenuMain Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUmsMenuMain;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUmsMenuMain = jasmine.createSpy('MockUmsMenuMain');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UmsMenuMain': MockUmsMenuMain
            };
            createController = function() {
                $injector.get('$controller')("UmsMenuMainDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pmsApp:umsMenuMainUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

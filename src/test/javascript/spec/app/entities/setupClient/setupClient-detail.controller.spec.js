'use strict';

describe('Controller Tests', function() {

    describe('SetupClient Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSetupClient, MockSetupCompany, MockCountry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSetupClient = jasmine.createSpy('MockSetupClient');
            MockSetupCompany = jasmine.createSpy('MockSetupCompany');
            MockCountry = jasmine.createSpy('MockCountry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SetupClient': MockSetupClient,
                'SetupCompany': MockSetupCompany,
                'Country': MockCountry
            };
            createController = function() {
                $injector.get('$controller')("SetupClientDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pmsApp:setupClientUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

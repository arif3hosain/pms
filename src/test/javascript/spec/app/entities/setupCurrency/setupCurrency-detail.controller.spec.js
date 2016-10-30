'use strict';

describe('Controller Tests', function() {

    describe('SetupCurrency Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSetupCurrency, MockCountry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSetupCurrency = jasmine.createSpy('MockSetupCurrency');
            MockCountry = jasmine.createSpy('MockCountry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SetupCurrency': MockSetupCurrency,
                'Country': MockCountry
            };
            createController = function() {
                $injector.get('$controller')("SetupCurrencyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pmsApp:setupCurrencyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

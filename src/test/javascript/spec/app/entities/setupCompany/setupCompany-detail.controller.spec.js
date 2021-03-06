'use strict';

describe('Controller Tests', function() {

    describe('SetupCompany Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSetupCompany, MockUser, MockCountry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSetupCompany = jasmine.createSpy('MockSetupCompany');
            MockUser = jasmine.createSpy('MockUser');
            MockCountry = jasmine.createSpy('MockCountry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SetupCompany': MockSetupCompany,
                'User': MockUser,
                'Country': MockCountry
            };
            createController = function() {
                $injector.get('$controller')("SetupCompanyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pmsApp:setupCompanyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

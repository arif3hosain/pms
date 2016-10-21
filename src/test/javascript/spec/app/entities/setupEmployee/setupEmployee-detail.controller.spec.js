'use strict';

describe('Controller Tests', function() {

    describe('SetupEmployee Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSetupEmployee, MockUser, MockSetupCompany;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSetupEmployee = jasmine.createSpy('MockSetupEmployee');
            MockUser = jasmine.createSpy('MockUser');
            MockSetupCompany = jasmine.createSpy('MockSetupCompany');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SetupEmployee': MockSetupEmployee,
                'User': MockUser,
                'SetupCompany': MockSetupCompany
            };
            createController = function() {
                $injector.get('$controller')("SetupEmployeeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pmsApp:setupEmployeeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

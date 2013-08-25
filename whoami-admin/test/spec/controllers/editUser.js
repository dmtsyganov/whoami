'use strict';

describe('Controller: EditUserCtrl', function () {

  // load the controller's module
  beforeEach(module('whoamiAdminApp'));

  var EdituserCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    EdituserCtrl = $controller('EditUserCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});

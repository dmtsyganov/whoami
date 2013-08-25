'use strict';

describe('Controller: ViewUserCtrl', function () {

  // load the controller's module
  beforeEach(module('whoamiAdminApp'));

  var ViewuserCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ViewuserCtrl = $controller('ViewUserCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});

'use strict';

describe('Controller: EditInterviewCtrl', function () {

  // load the controller's module
  beforeEach(module('whoamiAdminApp'));

  var EditinterviewCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    EditinterviewCtrl = $controller('EditInterviewCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});

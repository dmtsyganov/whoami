'use strict';

describe('Controller: ViewInterviewCtrl', function () {

  // load the controller's module
  beforeEach(module('whoamiAdminApp'));

  var ViewinterviewCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ViewinterviewCtrl = $controller('ViewInterviewCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});

'use strict';

describe('Controller: ResultChartCtrl', function () {

  // load the controller's module
  beforeEach(module('whoamiApp'));

  var ResultChartCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ResultChartCtrl = $controller('ResultChartCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});

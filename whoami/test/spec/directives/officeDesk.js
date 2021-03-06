'use strict';

describe('Directive: officeDesk', function () {

  // load the directive's module
  beforeEach(module('whoamiApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<office-desk></office-desk>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the officeDesk directive');
  }));
});

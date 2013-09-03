'use strict';

describe('Filter: valuetypename', function () {

  // load the filter's module
  beforeEach(module('whoamiAdminApp'));

  // initialize a new instance of the filter before each test
  var valuetypename;
  beforeEach(inject(function($filter) {
    valuetypename = $filter('valuetypename');
  }));

  it('should return the input prefixed with "valuetypename filter:"', function () {
    var text = 'angularjs';
    expect(valuetypename(text)).toBe('valuetypename filter: ' + text);
  });

});

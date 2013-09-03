'use strict';

describe('Filter: activename', function () {

  // load the filter's module
  beforeEach(module('whoamiAdminApp'));

  // initialize a new instance of the filter before each test
  var activename;
  beforeEach(inject(function($filter) {
    activename = $filter('activename');
  }));

  it('should return the input prefixed with "activename filter:"', function () {
    var text = 'angularjs';
    expect(activename(text)).toBe('activename filter: ' + text);
  });

});

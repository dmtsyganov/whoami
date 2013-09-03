'use strict';

describe('Filter: traitname', function () {

  // load the filter's module
  beforeEach(module('whoamiAdminApp'));

  // initialize a new instance of the filter before each test
  var traitname;
  beforeEach(inject(function($filter) {
    traitname = $filter('traitname');
  }));

  it('should return the input prefixed with "traitname filter:"', function () {
    var text = 'angularjs';
    expect(traitname(text)).toBe('traitname filter: ' + text);
  });

});

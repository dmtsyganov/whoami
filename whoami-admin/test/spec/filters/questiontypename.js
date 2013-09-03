'use strict';

describe('Filter: questiontypename', function () {

  // load the filter's module
  beforeEach(module('whoamiAdminApp'));

  // initialize a new instance of the filter before each test
  var questiontypename;
  beforeEach(inject(function($filter) {
    questiontypename = $filter('questiontypename');
  }));

  it('should return the input prefixed with "questiontypename filter:"', function () {
    var text = 'angularjs';
    expect(questiontypename(text)).toBe('questiontypename filter: ' + text);
  });

});

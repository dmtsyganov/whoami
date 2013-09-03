'use strict';

describe('Service: questiontype', function () {

  // load the service's module
  beforeEach(module('whoamiAdminApp'));

  // instantiate service
  var questiontype;
  beforeEach(inject(function(_questiontype_) {
    questiontype = _questiontype_;
  }));

  it('should do something', function () {
    expect(!!questiontype).toBe(true);
  });

});

'use strict';

describe('Service: CurrentInterview', function () {

  // load the service's module
  beforeEach(module('whoamiApp'));

  // instantiate service
  var CurrentInterview;
  beforeEach(inject(function(_CurrentInterview_) {
    CurrentInterview = _CurrentInterview_;
  }));

  it('should do something', function () {
    expect(!!CurrentInterview).toBe(true);
  });

});

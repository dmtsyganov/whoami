'use strict';

describe('Service: interview', function () {

  // load the service's module
  beforeEach(module('whoamiApp'));

  // instantiate service
  var interview;
  beforeEach(inject(function(_interview_) {
    interview = _interview_;
  }));

  it('should do something', function () {
    expect(!!interview).toBe(true);
  });

});

'use strict';

describe('Service: alltraits', function () {

  // load the service's module
  beforeEach(module('whoamiAdminApp'));

  // instantiate service
  var alltraits;
  beforeEach(inject(function(_alltraits_) {
    alltraits = _alltraits_;
  }));

  it('should do something', function () {
    expect(!!alltraits).toBe(true);
  });

});

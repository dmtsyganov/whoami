'use strict';

describe('Service: valuetype', function () {

  // load the service's module
  beforeEach(module('whoamiAdminApp'));

  // instantiate service
  var valuetype;
  beforeEach(inject(function(_valuetype_) {
    valuetype = _valuetype_;
  }));

  it('should do something', function () {
    expect(!!valuetype).toBe(true);
  });

});

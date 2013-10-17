'use strict';

describe('Service: Glossary', function () {

  // load the service's module
  beforeEach(module('whoamiAdminApp'));

  // instantiate service
  var Glossary;
  beforeEach(inject(function(_Glossary_) {
    Glossary = _Glossary_;
  }));

  it('should do something', function () {
    expect(!!Glossary).toBe(true);
  });

});

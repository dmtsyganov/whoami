package org.dnt.whoami;

import com.sun.jersey.api.core.PackagesResourceConfig;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/4/13 1:42 PM
 */
public class WhoamiApplication extends PackagesResourceConfig {

    public WhoamiApplication() {
        super("org.dnt.whoami.rest");
    }

    public WhoamiApplication(String... packages) {
        super(packages);
    }

    public WhoamiApplication(Map<String, Object> props) {
        super(props);
    }

    @Override
    public Set<Object> getSingletons() {
        // setup to use Jackson Jaxb provider
        HashSet<Object> singletons = new HashSet<Object>();
        singletons.add(new org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider());
        return singletons;
    }

}


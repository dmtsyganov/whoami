package org.dnt.whoami;

import org.dnt.whoami.dao.*;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * The main service application class
 *
 * @author dima
 * @since 8/4/13 1:42 PM
 */
public class WhoamiApplication extends ResourceConfig {
    static final Logger log = LoggerFactory.getLogger(WhoamiApplication.class);

    @Inject
    public WhoamiApplication(ServiceLocator serviceLocator) {
        log.info("Starting Who Am I Application");

        packages("org.dnt.whoami.rest");

        register(JacksonFeature.class);

        DynamicConfiguration dc = Injections.getConfiguration(serviceLocator);

        // singleton instance binding
        Injections.addBinding(
                Injections.newBinder(DaoClient.Instance.getInterviewDao())
                        .to(InterviewDao.class),
                dc);

        Injections.addBinding(
                Injections.newBinder(DaoClient.Instance.getInterviewTemplateDao())
                        .to(InterviewTemplateDao.class),
                dc);

        Injections.addBinding(
                Injections.newBinder(DaoClient.Instance.getUserDao())
                        .to(UserDao.class),
                dc);

        Injections.addBinding(
                Injections.newBinder(DaoClient.Instance.getGlossaryDao())
                        .to(GlossaryDao.class),
                dc);

        // commits changes
        dc.commit();
    }
}


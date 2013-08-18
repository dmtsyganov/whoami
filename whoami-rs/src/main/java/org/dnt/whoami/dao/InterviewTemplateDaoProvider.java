package org.dnt.whoami.dao;

import com.sun.jersey.spi.inject.SingletonTypeInjectableProvider;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/18/13 11:49 PM
 */
@Provider
public class InterviewTemplateDaoProvider extends SingletonTypeInjectableProvider<Context, InterviewTemplateDao> {
    public InterviewTemplateDaoProvider() {
        super(InterviewTemplateDao.class, DaoClient.Instance.getInterviewTemplateDao());
    }
}

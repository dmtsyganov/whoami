package org.dnt.whoami.dao;

import com.sun.jersey.spi.inject.SingletonTypeInjectableProvider;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/18/13 10:45 PM
 */
@Provider
public class InterviewDaoProvider extends SingletonTypeInjectableProvider<Context, InterviewDao>{
    public InterviewDaoProvider() {
        super(InterviewDao.class, DaoClient.Instance.getInterviewDao());
    }
}

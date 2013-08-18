package org.dnt.whoami.dao;

import com.sun.jersey.spi.inject.SingletonTypeInjectableProvider;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/18/13 11:52 PM
 */
@Provider
public class QuestionDaoProvider extends SingletonTypeInjectableProvider<Context, QuestionDao> {
    public QuestionDaoProvider() {
        super(QuestionDao.class, DaoClient.Instance.getQuestionDao());
    }
}

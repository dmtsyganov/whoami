package org.dnt.whoami.dao;

import com.sun.jersey.spi.inject.SingletonTypeInjectableProvider;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/18/13 11:54 PM
 */
@Provider
public class UserDaoProvider extends SingletonTypeInjectableProvider<Context, UserDao> {
    public UserDaoProvider() {
        super(UserDao.class, DaoClient.Instance.getUserDao());
    }
}

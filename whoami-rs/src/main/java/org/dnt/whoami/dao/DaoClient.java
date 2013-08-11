package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoClient;
import org.dnt.whoami.model.*;

import java.net.UnknownHostException;

/**
 * TODO: add class description
 * @author dima
 * @since  5/25/13 3:56 PM
 */
public enum DaoClient {
    Instance;

    private MongoClient client;
    private Datastore ds;

    private UserDao userDao;
//    private InterviewTemplateDao userProfileDao;

    synchronized public void connect(String host, int port, String datastoreName) throws UnknownHostException {
        client = new MongoClient(host, port);
        Morphia morphia = new Morphia();
        // initialize morphia with model classes
        morphia.map(UserRecord.class);
        morphia.map(UserProfile.class);
        morphia.map(Question.class);
        morphia.map(Interview.class);
        morphia.map(InterviewTemplate.class);

        ds = morphia.createDatastore(client, datastoreName);
        ds.ensureIndexes(); //creates all defined with @Indexed
    }

    // return model dao objects
    synchronized public UserDao getUserDao() {
        if(userDao == null) {
            userDao = new UserDao(ds);
        }
        return userDao;
    }

/*
    synchronized public InterviewTemplateDao getUserProfileDao() {
        if(userProfileDao == null) {
            userProfileDao = new InterviewTemplateDao(ds);
        }
        return userProfileDao;
    }
*/
}


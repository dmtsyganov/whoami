package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoClient;
import org.dnt.whoami.model.Interview;
import org.dnt.whoami.model.InterviewTemplate;
import org.dnt.whoami.model.Question;
import org.dnt.whoami.model.UserRecord;

import java.net.UnknownHostException;

/**
 * TODO: add class description
 * @author dima
 * @since  5/25/13 3:56 PM
 */
public enum DaoClient {
    Instance;

    public final static String defaultHost = "localhost";
    public final static int defaultPort = 27017;
    public final static String defaultDs = "mydb";

    private MongoClient client;
    private Datastore ds;
    private UserDao userDao;

    synchronized public void connect() throws UnknownHostException {
        if(client == null)
            client = new MongoClient(defaultHost, defaultPort);
    }

    synchronized public void connect(String host, int port) throws UnknownHostException {
        client = new MongoClient(host, port);
        Morphia morphia = new Morphia();
        // initialize morphia with model classes
        morphia.map(UserRecord.class);
        morphia.map(Question.class);
        morphia.map(Interview.class);
        morphia.map(InterviewTemplate.class);

        ds = morphia.createDatastore(client, defaultDs);
    }

    // return model dao objects
    public UserDao getUserDao() {
        if(userDao == null) {
            userDao = new UserDao(ds);
        }
        return userDao;
    }
}

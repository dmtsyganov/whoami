package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoClient;
import org.dnt.whoami.model.DictionaryRecord;
import org.dnt.whoami.model.Interview;
import org.dnt.whoami.model.InterviewTemplate;
import org.dnt.whoami.model.UserRecord;

import javax.inject.Singleton;
import java.net.UnknownHostException;

/**
 * The dao client singleton, initiates Morphia and DAO interface objects
 *
 * @author dima
 * @since 5/25/13 3:56 PM
 */
@Singleton
public enum DaoClient {
    Instance;

    private Datastore ds;

    private UserDao userDao;
    private DictionaryDao dictionaryDao;
    private InterviewTemplateDao interviewTemplateDao;
    private InterviewDao interviewDao;

    synchronized public void connect(String host, int port, String datastoreName) throws UnknownHostException {
        MongoClient client = new MongoClient(host, port);
        Morphia morphia = new Morphia();
        // initialize morphia with model classes
        morphia.map(UserRecord.class);
        morphia.map(InterviewTemplate.class);
        morphia.map(Interview.class);
        morphia.map(DictionaryRecord.class);

        ds = morphia.createDatastore(client, datastoreName);
        ds.ensureIndexes(); //creates all defined with @Indexed
    }

    // return model dao objects
    synchronized public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao(ds);
        }
        return userDao;
    }

    synchronized public DictionaryDao getDictionaryDao() {
        if (dictionaryDao == null) {
            dictionaryDao = new DictionaryDao(ds);
        }
        return dictionaryDao;
    }

    synchronized public InterviewTemplateDao getInterviewTemplateDao() {
        if (interviewTemplateDao == null) {
            interviewTemplateDao = new InterviewTemplateDao(ds);
        }
        return interviewTemplateDao;
    }

    synchronized public InterviewDao getInterviewDao() {
        if (interviewDao == null) {
            interviewDao = new InterviewDao(ds);
        }
        return interviewDao;
    }
}


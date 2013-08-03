package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateResults;
import org.dnt.whoami.model.UserRecord;

import java.util.Collections;
import java.util.List;

/**
 * TODO: add class description
 * @author dima
 * @since  5/25/13 3:56 PM
 */
public class UserDao implements DaoCrud<UserRecord>{

    private final Datastore ds;

    public UserDao(Datastore ds) {
        this.ds = ds;
    }

    @Override
    public void create(UserRecord object) {
        Key<UserRecord> key = ds.save(object);
    }

    @Override
    public List<UserRecord> read(UserRecord objectTemplate) {
        if (objectTemplate == null) {
            // return all records
            return ds.find(UserRecord.class).asList();
        } else if (objectTemplate.getId() != null) {
            // return record by unique user id
            return Collections.singletonList(ds.get(objectTemplate));
        } else if (objectTemplate.getLogin() != null) {
            // return record by unique login name
            return ds.find(UserRecord.class).field("login").equal(objectTemplate.getLogin()).asList();
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public boolean update(UserRecord object) {
        Query<UserRecord> query = ds.createQuery(UserRecord.class).field(Mapper.ID_KEY).equal(object.getObjectId());
        // UpdateOperations<UserRecord> update = ds.createUpdateOperations(UserRecord.class);
        UpdateResults<UserRecord> result = ds.updateFirst(query, object, false);
        return result.getHadError();
    }

    @Override
    public UserRecord delete(UserRecord objectTemplate) {
        return ds.findAndDelete(ds.createQuery(UserRecord.class).filter("login", objectTemplate.getLogin()));
    }
}

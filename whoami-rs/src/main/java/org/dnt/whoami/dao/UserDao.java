package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateResults;
import org.dnt.whoami.model.UserRecord;

import java.util.Collections;
import java.util.List;

/**
 * The User dao interface class
 *
 * @author dima
 * @since 5/25/13 3:56 PM
 */
public class UserDao extends AbstractDaoCrud<UserRecord> {

    public UserDao(Datastore ds) {
        super(ds);
    }

    @Override
    public List<UserRecord> find(UserRecord objectTemplate) {

        if (objectTemplate == null) {
            // return all records
            Query<UserRecord> q = ds.find(UserRecord.class);

            if (q != null) {
                return q.asList();
            }
        } else if (objectTemplate.getLogin() != null) {
            // return record by unique login name
            Query<UserRecord> q = ds.find(UserRecord.class).field("login").equal(objectTemplate.getLogin());
            if (q != null) {
                return q.asList();
            }
        }

        return Collections.emptyList();
    }

    @Override
    public boolean update(UserRecord object) {
        Query<UserRecord> query = ds.createQuery(UserRecord.class).field(Mapper.ID_KEY).equal(object.getObjectId());
        // UpdateOperations<UserRecord> update = ds.createUpdateOperations(UserRecord.class);
        UpdateResults<UserRecord> result = ds.updateFirst(query, object, false);
        return !result.getHadError();
    }

    @Override
    public UserRecord delete(UserRecord objectTemplate) {
        return ds.findAndDelete(ds.createQuery(UserRecord.class).field(Mapper.ID_KEY).equal(objectTemplate.getObjectId()));
    }
}

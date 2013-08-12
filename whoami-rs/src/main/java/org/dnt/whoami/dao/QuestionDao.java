package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateResults;
import org.bson.types.ObjectId;
import org.dnt.whoami.model.Question;

import java.util.Collections;
import java.util.List;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 5:17 PM
 */
public class QuestionDao implements DaoCrud<Question> {

    private final Datastore ds;

    public QuestionDao(Datastore ds) {
        this.ds = ds;
    }

    @Override
    public ObjectId create(Question object) {
        if (ds.save(object) != null) {
            return object.getObjectId();
        }
        return null;
    }

    @Override
    public List<Question> read(Question objectTemplate) {
        if (objectTemplate == null) {
            // return all records
            Query<Question> q = ds.find(Question.class);
            if(q != null) {
                return q.asList();
            }
        } else if (objectTemplate.getId() != null) {
            // return record by unique user id
            Question record = ds.get(objectTemplate);
            if(record != null) {
                return Collections.singletonList(record);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public boolean update(Question object) {
        Query<Question> query = ds.createQuery(Question.class).field(Mapper.ID_KEY).equal(object.getObjectId());
        // UpdateOperations<UserRecord> update = ds.createUpdateOperations(UserRecord.class);
        UpdateResults<Question> result = ds.updateFirst(query, object, false);
        return !result.getHadError();
    }

    @Override
    public Question delete(Question objectTemplate) {
        return ds.findAndDelete(ds.createQuery(Question.class).field(Mapper.ID_KEY).equal(objectTemplate.getObjectId()));
    }
}

package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateResults;
import org.bson.types.ObjectId;
import org.dnt.whoami.model.InterviewTemplate;

import java.util.Collections;
import java.util.List;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 4:40 AM
 */
public class InterviewTemplateDao implements DaoCrud<InterviewTemplate> {

    private final Datastore ds;

    public InterviewTemplateDao(Datastore ds) {
        this.ds = ds;
    }

    @Override
    public ObjectId create(InterviewTemplate interviewTemplate) {
        if (ds.save(interviewTemplate) != null) {
            return interviewTemplate.getObjectId();
        }

        return null;
    }

    @Override
    public List<InterviewTemplate> read(InterviewTemplate objectTemplate) {
        if (objectTemplate == null) {
            // return all records
            return ds.find(InterviewTemplate.class).asList();
        } else if (objectTemplate.getId() != null) {
            // return record by unique user id
            InterviewTemplate record = ds.get(objectTemplate);
            if(record == null)
                return Collections.emptyList();
            else
                return Collections.singletonList(record);
        }

        return Collections.emptyList();
    }

    @Override
    public boolean update(InterviewTemplate object) {
        Query<InterviewTemplate> query = ds.createQuery(InterviewTemplate.class).field(Mapper.ID_KEY).equal(object.getObjectId());
        // UpdateOperations<UserRecord> update = ds.createUpdateOperations(UserRecord.class);
        UpdateResults<InterviewTemplate> result = ds.updateFirst(query, object, false);
        return !result.getHadError();
    }

    @Override
    public InterviewTemplate delete(InterviewTemplate objectTemplate) {
        return ds.findAndDelete(ds.createQuery(InterviewTemplate.class).field(Mapper.ID_KEY).equal(objectTemplate.getObjectId()));
    }
}

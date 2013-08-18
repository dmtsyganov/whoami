package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateResults;
import org.bson.types.ObjectId;
import org.dnt.whoami.model.Interview;

import java.util.Collections;
import java.util.List;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/18/13 7:26 AM
 */
public class InterviewDao extends AbstractDaoCrud<Interview> {

    public InterviewDao(Datastore ds) {
        super(ds);
    }

    @Override
    public List<Interview> find(Interview interviewTemplate) {
        if (interviewTemplate == null) {
            // return all records
            Query<Interview> q = ds.find(Interview.class);
            if(q != null) {
                return q.asList();
            }
        }

        return Collections.emptyList();
    }

    @Override
    public boolean update(Interview interview) {
        Query<Interview> query = ds.createQuery(Interview.class).field(Mapper.ID_KEY).equal(interview.getObjectId());
        // UpdateOperations<UserRecord> update = ds.createUpdateOperations(UserRecord.class);
        UpdateResults<Interview> result = ds.updateFirst(query, interview, false);
        return !result.getHadError();
    }

    @Override
    public Interview delete(Interview interview) {
        return ds.findAndDelete(ds.createQuery(Interview.class).field(Mapper.ID_KEY).equal(interview.getObjectId()));
    }

    // interview lookup
    public List<Interview> findInterview(ObjectId userId) {
        Query<Interview> q = ds.createQuery(Interview.class).field("userId").equal(userId);
        return q.asList();
    }

    public Interview findInterview(ObjectId userId, ObjectId templateId) {
        Query<Interview> q = ds.createQuery(Interview.class);
        q.and(q.criteria("userId").equal(userId),q.criteria("templateId").equal(templateId));
        return q.get();
    }

}

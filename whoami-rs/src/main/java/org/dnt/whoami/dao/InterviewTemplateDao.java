package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateResults;
import org.dnt.whoami.model.InterviewTemplate;

import java.util.Collections;
import java.util.List;

/**
 * The Interview Template dao interface class
 *
 * @author dima
 * @since 8/11/13 4:40 AM
 */
public class InterviewTemplateDao extends AbstractDaoCrud<InterviewTemplate> {

    public InterviewTemplateDao(Datastore ds) {
        super(ds);
    }

    @Override
    public List<InterviewTemplate> find(InterviewTemplate objectTemplate) {
        if (objectTemplate == null) {
            // return all records
            Query<InterviewTemplate> q = ds.find(InterviewTemplate.class);
            if(q != null) {
                return q.asList();
            }
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

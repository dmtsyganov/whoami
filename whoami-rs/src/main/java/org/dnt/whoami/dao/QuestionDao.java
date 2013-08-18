package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateResults;
import org.dnt.whoami.model.Question;

import java.util.Collections;
import java.util.List;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 5:17 PM
 */
public class QuestionDao extends AbstractDaoCrud<Question> {

    public QuestionDao(Datastore ds) {
        super(ds);
    }

    @Override
    public List<Question> find(Question objectTemplate) {
        if (objectTemplate == null) {
            // return all records
            Query<Question> q = ds.find(Question.class);
            if(q != null) {
                return q.asList();
            }
        }

        return Collections.emptyList();
    }

    @Override
    public boolean update(Question object) {
        Query<Question> query = ds.createQuery(Question.class).field(Mapper.ID_KEY).equal(object.getObjectId());
        UpdateResults<Question> result = ds.updateFirst(query, object, false);
        return !result.getHadError();
    }

    @Override
    public Question delete(Question objectTemplate) {
        return ds.findAndDelete(ds.createQuery(Question.class).field(Mapper.ID_KEY).equal(objectTemplate.getObjectId()));
    }
}

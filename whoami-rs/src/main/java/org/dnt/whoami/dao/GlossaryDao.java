package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateResults;
import org.dnt.whoami.model.GlossaryRecord;

import java.util.Collections;
import java.util.List;

/**
 * The Glossary dao interface class
 *
 * @author dima
 * @since 10/9/13 11:45 PM
 */
public class GlossaryDao extends AbstractDaoCrud<GlossaryRecord> {

    public GlossaryDao(Datastore ds) {
        super(ds);
    }

    @Override
    public List<GlossaryRecord> find(GlossaryRecord objectTemplate) {
        if (objectTemplate == null) {
            // return all records
            Query<GlossaryRecord> q = ds.find(GlossaryRecord.class);

            if (q != null) {
                return q.asList();
            }
        } else if (objectTemplate.getTerm() != null) {
            // return record by unique term
            Query<GlossaryRecord> q = ds.find(GlossaryRecord.class).field("term").equal(objectTemplate.getTerm());
            if (q != null) {
                return q.asList();
            }
        }

        return Collections.emptyList();
    }

    @Override
    public boolean update(GlossaryRecord object) {
        Query<GlossaryRecord> query = ds.createQuery(GlossaryRecord.class).field(Mapper.ID_KEY).equal(object.getObjectId());
        UpdateResults<GlossaryRecord> result = ds.updateFirst(query, object, false);
        return !result.getHadError();
    }

    @Override
    public GlossaryRecord delete(GlossaryRecord objectTemplate) {
        return ds.findAndDelete(ds.createQuery(GlossaryRecord.class).field(Mapper.ID_KEY).equal(objectTemplate.getObjectId()));
    }
}

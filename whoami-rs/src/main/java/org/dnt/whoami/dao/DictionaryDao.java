package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateResults;
import org.dnt.whoami.model.DictionaryRecord;

import java.util.Collections;
import java.util.List;

/**
 * The Dictionary dao interface class
 *
 * @author dima
 * @since 10/9/13 11:45 PM
 */
public class DictionaryDao extends AbstractDaoCrud<DictionaryRecord> {

    public DictionaryDao(Datastore ds) {
        super(ds);
    }

    @Override
    public List<DictionaryRecord> find(DictionaryRecord objectTemplate) {
        if (objectTemplate == null) {
            // return all records
            Query<DictionaryRecord> q = ds.find(DictionaryRecord.class);

            if (q != null) {
                return q.asList();
            }
        } else if (objectTemplate.getEntry() != null) {
            // return record by unique entry
            Query<DictionaryRecord> q = ds.find(DictionaryRecord.class).field("entry").equal(objectTemplate.getEntry());
            if (q != null) {
                return q.asList();
            }
        }

        return Collections.emptyList();
    }

    @Override
    public boolean update(DictionaryRecord object) {
        Query<DictionaryRecord> query = ds.createQuery(DictionaryRecord.class).field(Mapper.ID_KEY).equal(object.getObjectId());
        UpdateResults<DictionaryRecord> result = ds.updateFirst(query, object, false);
        return !result.getHadError();
    }

    @Override
    public DictionaryRecord delete(DictionaryRecord objectTemplate) {
        return ds.findAndDelete(ds.createQuery(DictionaryRecord.class).field(Mapper.ID_KEY).equal(objectTemplate.getObjectId()));
    }
}

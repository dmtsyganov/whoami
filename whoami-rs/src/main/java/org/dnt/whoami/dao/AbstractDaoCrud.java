package org.dnt.whoami.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;

/**
 * Abstract Crud class, implements basic crud methods
 *
 * @author dima
 * @since 8/19/13 12:20 AM
 */
public abstract class AbstractDaoCrud<T> implements DaoCrud<T>{

    protected final Datastore ds;

    protected AbstractDaoCrud(Datastore ds) {
        this.ds = ds;
    }

    @Override
    public Key<T> create(T object) {
        return ds.save(object);
    }

    @Override
    public T read(T objectTemplate) {
        return ds.get(objectTemplate);
    }
}

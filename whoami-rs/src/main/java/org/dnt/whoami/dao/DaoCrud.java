package org.dnt.whoami.dao;

import com.google.code.morphia.Key;

import java.util.List;

/**
 * This is basic CRUD interface to access and manipulate data in the data store.
 * It provides Create, Read, Update, Delete methods and Find to perform queries.
 * @author dima
 * @since  5/25/13 3:56 PM
 */
public interface DaoCrud<T> {

    /**
     * Creates new record, assigns object id
     * @param object new object to save
     * @return saved object Key
     */
    public Key<T> create(T object);

    /**
     * Reads one record from the database tha matches the template. Template must have ObjectId.
     * @param objectTemplate
     * @return object from the database with all fields filled
     */
    public T read(final T objectTemplate);

    /**
     * Finds records in the database that matches template. If template is null, all objects from the database returned.
     * @param objectTemplate
     * @return List of matched objects
     */
    public List<T> find(final T objectTemplate);

    /**
     * Updates existing record in the database with values from passed object
     * @param object
     * @return
     */
    public boolean update(final T object);

    /**
     * Deletes object from the database that matches template parameters
     * @param objectTemplate
     * @return deleted object
     */
    public T delete(final T objectTemplate);
}

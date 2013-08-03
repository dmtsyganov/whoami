package org.dnt.whoami.dao;

import java.util.List;

/**
 * TODO: add class description
 * @author dima
 * @since  5/25/13 3:56 PM
 */
public interface DaoCrud<T> {

    void create(T object);

    public List<T> read(final T objectTemplate);

    public boolean update(final T object);

    public T delete(final T objectTemplate);
}

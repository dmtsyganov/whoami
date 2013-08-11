package org.dnt.whoami.test;

import org.dnt.whoami.dao.DaoClient;
import org.junit.BeforeClass;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 2:41 AM
 */
public abstract class TestBase {

    @BeforeClass
    public static void setUp() {
        try {
            DaoClient.Instance.connect("localhost", 27017, "testdb");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

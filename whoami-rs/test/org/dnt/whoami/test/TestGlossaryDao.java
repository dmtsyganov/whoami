package org.dnt.whoami.test;

import com.mongodb.MongoException;
import junit.framework.Assert;
import org.dnt.whoami.dao.DaoClient;
import org.dnt.whoami.dao.GlossaryDao;
import org.dnt.whoami.model.GlossaryRecord;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Test Glossary Dao class
 *
 * @author dima
 * @since 10/12/13 2:32 PM
 */
public class TestGlossaryDao extends TestBase {
    private static GlossaryDao glossaryDao;

    @BeforeClass
    public static void setDao() {
        glossaryDao = DaoClient.Instance.getGlossaryDao();

        // clear all glossary terms
        List<GlossaryRecord> records = glossaryDao.find(null);
        for (GlossaryRecord r : records) {
            glossaryDao.delete(r);
        }
    }

    @Test
    public void testCreateGlossaryRecord() {
        GlossaryRecord t1 = new GlossaryRecord("A Term", " A term definition");
        glossaryDao.create(t1);
        Assert.assertNotNull("Must have object id", t1.getObjectId());

        GlossaryRecord t2 = new GlossaryRecord("B Term", " B term definition");
        glossaryDao.create(t2);
        Assert.assertNotNull("Must have object id", t2.getObjectId());

        GlossaryRecord t3 = new GlossaryRecord("C Term", " C term definition");
        glossaryDao.create(t3);
        Assert.assertNotNull("Must have object id", t3.getObjectId());

        List<GlossaryRecord> records = glossaryDao.find(null);
        Assert.assertEquals("Must have three records", 3, records.size());

        // delete one record
        glossaryDao.delete(t2);
        records = glossaryDao.find(null);
        Assert.assertEquals("Must have two records", 2, records.size());

        // look up by term
        GlossaryRecord template = new GlossaryRecord();
        template.setTerm("A Term");
        records = glossaryDao.find(template);
        Assert.assertEquals("Must have one records", 1, records.size());
        Assert.assertEquals("Must have Term A record", "A Term", records.get(0).getTerm());

        try {
            GlossaryRecord tDup = new GlossaryRecord("A Term", " A term definition");
            glossaryDao.create(tDup);
            Assert.fail("Duplicate term created");
        } catch (MongoException.DuplicateKey e) {
            // must throw exception
        }

    }
}

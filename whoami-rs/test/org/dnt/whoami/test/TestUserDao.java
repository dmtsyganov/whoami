package org.dnt.whoami.test;

import junit.framework.Assert;
import org.bson.types.ObjectId;
import org.dnt.whoami.dao.DaoClient;
import org.dnt.whoami.dao.UserDao;
import org.dnt.whoami.model.UserProfile;
import org.dnt.whoami.model.UserRecord;
import org.dnt.whoami.model.UserRole;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 2:47 AM
 */
public class TestUserDao extends TestBase {

    private static UserDao dao;

    @BeforeClass
    public static void setDao() {
        dao = DaoClient.Instance.getUserDao();
        List<UserRecord> records = dao.read(null);
        for(UserRecord u: records) {
            dao.delete(u);
        }
    }

    @Test
    public void testCRUD() {
        UserRecord userRecord = new UserRecord("admin", "secret", UserRole.ADMINISTRATOR);
        ObjectId id = dao.create(userRecord);

        UserRecord template = new UserRecord();
        template.setId(id.toString());

        List<UserRecord> records = dao.read(template);
        Assert.assertEquals("Must contain one record", 1, records.size());

        UserRecord template2 = new UserRecord();
        template2.setLogin("admin");
        List<UserRecord> records2 = dao.read(template2);
        Assert.assertEquals("Must contain one record", 1, records2.size());
        Assert.assertEquals("Must be the same object", records.get(0).getId(), records2.get(0).getId());

        UserRecord update = records2.get(0);
        update.setPassword("new_secret");
        update.setRole(UserRole.USER);
        Assert.assertTrue("Update user record failed", dao.update(update));

        List<UserRecord> records3 = dao.read(template);
        Assert.assertEquals("Must contain one record", 1, records3.size());
        Assert.assertEquals("Must have new password", "new_secret", records3.get(0).getPassword());
        Assert.assertEquals("Must have new role", UserRole.USER, records3.get(0).getRole());

        UserRecord deleted = dao.delete(template);
        Assert.assertNotNull("Deleted record is null", deleted);

        List<UserRecord> records4 = dao.read(template);
        Assert.assertEquals("Must contain NO record", 0, records4.size());
    }

    @Test
    public void addTestUsers() {
        UserRecord userAdmin = new UserRecord("admin", "secret", UserRole.ADMINISTRATOR);
        ObjectId id1 = dao.create(userAdmin);
        UserRecord userUser = new UserRecord("user", "secret", UserRole.USER);
        ObjectId id2 = dao.create(userUser);
        UserRecord userEditor = new UserRecord("editor", "secret", UserRole.EDITOR);
        ObjectId id3 = dao.create(userEditor);

        List<UserRecord> records = dao.read(null);
        Assert.assertEquals("Must contain three records", 3, records.size());

    }

    @Test
    public void testUserWithProfile() {

        UserRecord user = new UserRecord("profile", "secret", UserRole.USER);
        ObjectId id = dao.create(user);

        UserRecord template = new UserRecord();
        template.setId(id.toString());
        List<UserRecord> records = dao.read(template);
        Assert.assertEquals("Must contain this user records", 1, records.size());

        Assert.assertNull("Must NOT contain the profile", records.get(0).getProfile());

        UserProfile profile = new UserProfile("Profile User", "profile@sample.com", 33, Calendar.getInstance().getTime(), null);
        records.get(0).setProfile(profile);
        dao.update(records.get(0));

        Assert.assertNotNull("Must contain profile", records.get(0).getProfile());

        profile.setEmail("new_profile@email.com");
        profile.setDateUpdated(Calendar.getInstance().getTime());

        records.get(0).setProfile(profile);
        Assert.assertTrue("Profile updated", dao.update(records.get(0)));

        records.get(0).setProfile(null);
        Assert.assertTrue("Profile updated", dao.update(records.get(0)));

        Assert.assertNull("Must NOT contain the profile again", records.get(0).getProfile());

    }
}

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
        List<UserRecord> records = dao.find(null);
        for(UserRecord u: records) {
            dao.delete(u);
        }
    }

    @Test
    public void testCRUD() {
        UserRecord userRecord = new UserRecord("admin", "secret", UserRole.ADMINISTRATOR);
        dao.create(userRecord);
        ObjectId id = userRecord.getObjectId();

        UserRecord template = new UserRecord();
        template.setId(id.toString());

        UserRecord record = dao.read(template);
        Assert.assertNotNull("Must contain one record", record);

        UserRecord template2 = new UserRecord();
        template2.setLogin("admin");
        List<UserRecord> records2 = dao.find(template2);
        Assert.assertEquals("Must contain one record", 1, records2.size());
        Assert.assertEquals("Must be the same object", record.getId(), records2.get(0).getId());

        UserRecord update = records2.get(0);
        update.setPassword("new_secret");
        update.setRole(UserRole.USER);
        Assert.assertTrue("Update user record failed", dao.update(update));

        UserRecord records3 = dao.read(template);
        Assert.assertNotNull("Must contain one record", records3);
        Assert.assertEquals("Must have new password", "new_secret", records3.getPassword());
        Assert.assertEquals("Must have new role", UserRole.USER, records3.getRole());

        UserRecord deleted = dao.delete(template);
        Assert.assertNotNull("Deleted record is null", deleted);

        UserRecord records4 = dao.read(template);
        Assert.assertNull("Must contain NO record", records4);
    }

    @Test
    public void addTestUsers() {
        UserRecord userAdmin = new UserRecord("admin", "secret", UserRole.ADMINISTRATOR);
        dao.create(userAdmin);
        UserRecord userUser = new UserRecord("user", "secret", UserRole.USER);
        dao.create(userUser);
        UserRecord userEditor = new UserRecord("editor", "secret", UserRole.EDITOR);
        dao.create(userEditor);

        List<UserRecord> records = dao.find(null);
        Assert.assertEquals("Must contain three records", 3, records.size());

    }

    @Test
    public void testUserWithProfile() {

        UserRecord user = new UserRecord("profile", "secret", UserRole.USER);
        dao.create(user);
        ObjectId id = user.getObjectId();

        UserRecord template = new UserRecord();
        template.setId(id.toString());
        UserRecord records = dao.read(template);
        Assert.assertNotNull("Must contain this user records", records);

        Assert.assertNull("Must NOT contain the profile", records.getProfile());

        UserProfile profile = new UserProfile("Profile User", "profile@sample.com", 33, Calendar.getInstance().getTime(), null);
        records.setProfile(profile);
        dao.update(records);

        Assert.assertNotNull("Must contain profile", records.getProfile());

        profile.setEmail("new_profile@email.com");
        profile.setDateUpdated(Calendar.getInstance().getTime());

        records.setProfile(profile);
        Assert.assertTrue("Profile updated", dao.update(records));

        records.setProfile(null);
        Assert.assertTrue("Profile updated", dao.update(records));
        Assert.assertNull("Must NOT contain the profile again", records.getProfile());
    }
}

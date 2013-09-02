package org.dnt.whoami.test;

import junit.framework.Assert;
import org.bson.types.ObjectId;
import org.dnt.whoami.dao.*;
import org.dnt.whoami.model.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test interview resource
 *
 * @author dima
 * @since 8/18/13 1:51 PM
 */
public class TestInterviewDao extends TestBase {

    private static InterviewTemplateDao templateDao;
    private static InterviewDao interviewDao;
    private static UserDao userDao;

    @BeforeClass
    public static void setDao() {
        templateDao = DaoClient.Instance.getInterviewTemplateDao();
        interviewDao = DaoClient.Instance.getInterviewDao();
        userDao = DaoClient.Instance.getUserDao();

        // clear all interviews
        List<Interview> interviews = interviewDao.find(null);
        for(Interview i: interviews) {
            interviewDao.delete(i);
        }

        // clear all users
        List<UserRecord> records = userDao.find(null);
        for(UserRecord u: records) {
            userDao.delete(u);
        }

        // clear all interviews
        List<InterviewTemplate> templates = templateDao.find(null);
        for(InterviewTemplate t: templates) {
            templateDao.delete(t);
        }
    }

    @Test
    public void testCreateInterviewFromTemplate() {
        // create user
        UserRecord userUser = new UserRecord("interviewUser", "secret", UserRole.USER);
        userDao.create(userUser);
        ObjectId uId = userUser.getObjectId();
        Assert.assertNotNull("User created", uId);
        Assert.assertNotNull("Must have id", userUser.getObjectId());

        // create interview template
        InterviewTemplate template = new InterviewTemplate("Interview One", "First Interview", true, null);
        templateDao.create(template);
        ObjectId tId = template.getObjectId();
        Assert.assertNotNull("Interview template created", template);
        Assert.assertNotNull("Must have id", template.getObjectId());

        List<Question> questions = new ArrayList<Question>();
        questions.add(new Question("Question One", PersonalityTrait.CONNOTATIVE,
                Question.Type.DIRECT, Question.ValueType.SCORE));
        questions.add(new Question("Question Two", PersonalityTrait.ENERGETIC,
                Question.Type.DIRECT, Question.ValueType.YES_NO));
        questions.add(new Question("Question Three", PersonalityTrait.SELF_ACTUALIZATION,
                Question.Type.INDIRECT, Question.ValueType.SCORE));

        template.setQuestions(questions);
        Assert.assertTrue("Updated with questions", templateDao.update(template));

        // get user
        UserRecord userRecord = userDao.read(new UserRecord(uId));
        Assert.assertNotNull("Must be user", userRecord);
        Assert.assertEquals("Must have same id", uId.toString(), userRecord.getId());

        // get interview template
        InterviewTemplate interview1 = templateDao.read(new InterviewTemplate(tId));
        Assert.assertEquals("Must have same id", tId.toString(), interview1.getId());
        Assert.assertEquals("Must have three questions", 3, interview1.getQuestions().size());

        // get interview questions
        List<Question> interviewQuestions = interview1.getQuestions();

        // create interview
        Interview interview = new Interview();

        // set interview parameters
        interview.setUserId(userRecord.getObjectId());
        interview.setTemplateId(interview1.getObjectId());

        List<Answer> answers = new ArrayList<Answer>(interviewQuestions.size());
        for(Question q: interviewQuestions) {
            answers.add(new Answer(q.getTrait(), q.getType(), q.getValueType(), "7"));
        }
        interview.setAnswers(answers);
        Assert.assertNull("Does not have id yet", interview.getObjectId());

        // save interview
        interviewDao.create(interview);
        Assert.assertNotNull("Must have id now", interview.getObjectId());

        Interview inter = interviewDao.read(new Interview(interview.getObjectId()));
        Assert.assertEquals("Must have same id", interview.getObjectId().toString(), inter.getId());
        Assert.assertEquals("Must have three answers", 3, inter.getAnswers().size());
    }
}

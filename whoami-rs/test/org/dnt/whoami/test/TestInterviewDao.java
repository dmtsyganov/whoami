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
 * TODO: add class description here
 *
 * @author dima
 * @since 8/18/13 1:51 PM
 */
public class TestInterviewDao extends TestBase {

    private static InterviewTemplateDao templateDao;
    private static QuestionDao questionDao;
    private static InterviewDao interviewDao;
    private static UserDao userDao;

    @BeforeClass
    public static void setDao() {
        templateDao = DaoClient.Instance.getInterviewTemplateDao();
        questionDao = DaoClient.Instance.getQuestionDao();
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

        // clear all interview questions
        List<Question> questions = questionDao.find(null);
        for(Question q: questions) {
            questionDao.delete(q);
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
        InterviewTemplate template = new InterviewTemplate("Interview One", "First Interview", null);
        templateDao.create(template);
        ObjectId tId = template.getObjectId();
        Assert.assertNotNull("Interview template created", template);
        Assert.assertNotNull("Must have id", template.getObjectId());

        List<Question> questions = new ArrayList<Question>();
        questions.add(new Question("Question One", PersonalityTrait.ONE, Question.QuestionType.SCORE));
        questions.add(new Question("Question Two", PersonalityTrait.TWO, Question.QuestionType.YES_NO));
        questions.add(new Question("Question Three", PersonalityTrait.THREE, Question.QuestionType.SCORE));

        List<ObjectId> questionIds = new ArrayList<ObjectId>(questions.size());
        for(Question q: questions) {
            questionDao.create(q);
            Assert.assertNotNull("Must have id", q.getObjectId());
            questionIds.add(q.getObjectId());
        }

        template.setQuestions(questionIds);
        Assert.assertTrue("Updated with questions", templateDao.update(template));

        // get user
        UserRecord userTemplate = new UserRecord();
        userTemplate.setId(uId.toString());
        UserRecord userRecord = userDao.read(userTemplate);
        Assert.assertNotNull("Must be user", userRecord);
        Assert.assertEquals("Must have same id", uId.toString(), userRecord.getId());

        // get interview template
        InterviewTemplate interviewTemplateTemplate = new InterviewTemplate();
        interviewTemplateTemplate.setId(tId.toString());
        InterviewTemplate interview1 = templateDao.read(interviewTemplateTemplate);
        Assert.assertEquals("Must have same id", tId.toString(), interview1.getId());
        Assert.assertEquals("Must have three questions", 3, interview1.getQuestions().size());

        // get interview questions
        List<Question> interviewQuestions = new ArrayList<Question>(interview1.getQuestions().size());
        for(ObjectId qId: interview1.getQuestions()) {
            Question questionTemplate = new Question();
            questionTemplate.setObjectId(qId);
            interviewQuestions.add(questionDao.read(questionTemplate));
        }

        // create interview
        Interview interview = new Interview();

        // set interview parameters
        interview.setUserId(userRecord.getObjectId());
        interview.setTemplateId(interview1.getObjectId());

        List<Answer> answers = new ArrayList<Answer>(interviewQuestions.size());
        for(Question q: interviewQuestions) {
            answers.add(new Answer(q.getObjectId(), "Answer to " + q.getText()));
        }
        interview.setAnswers(answers);
        Assert.assertNull("Does not have id yet", interview.getObjectId());

        // save interview
        interviewDao.create(interview);
        Assert.assertNotNull("Must have id now", interview.getObjectId());

        Interview interview2 = new Interview();
        interview2.setId(interview.getObjectId().toString());
        Interview inter = interviewDao.read(interview2);
        Assert.assertEquals("Must have same id", interview.getObjectId().toString(), inter.getId());
        Assert.assertEquals("Must have three answers", 3, inter.getAnswers().size());
    }
}

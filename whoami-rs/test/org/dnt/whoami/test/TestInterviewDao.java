package org.dnt.whoami.test;

import junit.framework.Assert;
import org.bson.types.ObjectId;
import org.dnt.whoami.dao.*;
import org.dnt.whoami.model.*;
import org.dnt.whoami.utils.Calculator;
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
        InterviewTemplate template = new InterviewTemplate("Interview One", "First Interview", true, 1, null);
        templateDao.create(template);
        ObjectId tId = template.getObjectId();
        Assert.assertNotNull("Interview template created", template);
        Assert.assertNotNull("Must have id", template.getObjectId());

        List<Question> questions = new ArrayList<Question>();
        questions.add(new Question("Question One", PersonalityTrait.CONNOTATIVE,
                Question.Type.DIRECT, Question.ValueType.SCORE, 1));
        questions.add(new Question("Question Two", PersonalityTrait.ENERGETIC,
                Question.Type.INDIRECT, Question.ValueType.YES_NO, 1));
        questions.add(new Question("Question Three", PersonalityTrait.SELF_ACTUALIZATION,
                Question.Type.INDIRECT, Question.ValueType.YES_NO, 1));
        questions.add(new Question("Question Three", PersonalityTrait.INTELLECTUAL,
                Question.Type.INDIRECT, Question.ValueType.YES_NO, 1));
        questions.add(new Question("Question Three", PersonalityTrait.INTELLECTUAL,
                Question.Type.INDIRECT, Question.ValueType.YES_NO, 1));

        template.setQuestions(questions);
        Assert.assertTrue("Updated with questions", templateDao.update(template));

        // get user
        UserRecord userRecord = userDao.read(new UserRecord(uId));
        Assert.assertNotNull("Must be user", userRecord);
        Assert.assertEquals("Must have same id", uId.toString(), userRecord.getId());

        // get interview template
        InterviewTemplate interview1 = templateDao.read(new InterviewTemplate(tId));
        Assert.assertEquals("Must have same id", tId.toString(), interview1.getId());
        Assert.assertEquals("Must have five questions", 5, interview1.getQuestions().size());

        // get interview questions
        List<Question> interviewQuestions = interview1.getQuestions();

        // create interview
        Interview interview = new Interview();

        // set interview parameters
        interview.setUserId(userRecord.getObjectId());
        interview.setTemplateId(interview1.getObjectId());

        List<Answer> answers = new ArrayList<Answer>(interviewQuestions.size());
        String value;
        for(Question q: interviewQuestions) {
            if (q.getType() == Question.Type.DIRECT) {
                value = "7";
                answers.add(new Answer(q.getTrait(), q.getType(), q.getValueType(), value));
            } else if(q.getType() == Question.Type.INDIRECT) {
                if(q.getValueEffect() > 0) {
                    value = "1";
                } else {
                    value = "0";
                }
                answers.add(new Answer(q.getTrait(), q.getType(), q.getValueType(), value));
            }
        }
        interview.setAnswers(answers);
        Assert.assertNull("Does not have id yet", interview.getObjectId());

        // save interview
        interviewDao.create(interview);
        Assert.assertNotNull("Must have id now", interview.getObjectId());

        Interview inter = interviewDao.read(new Interview(interview.getObjectId()));
        Assert.assertEquals("Must have same id", interview.getObjectId().toString(), inter.getId());
        Assert.assertEquals("Must have five answers", 5, inter.getAnswers().size());

        // get interview results and validate
        InterviewResult result = Calculator.calcInterviewResult(inter);
        Assert.assertTrue("Interview is complete", result.isComplete());
        Assert.assertEquals("Has one direct trait", 1, result.getDirectScores().size());
        Assert.assertEquals("Direct score is 7", 7, result.getDirectScores().get(0).getScore());
        Assert.assertEquals("Has three indirect trait", 3, result.getIndirectScores().size());

        for(TraitScore score: result.getIndirectScores()) {
            if(score.getTrait() == PersonalityTrait.INTELLECTUAL) {
                Assert.assertEquals("Indirect score for Intellectual is 2", 2, score.getScore());
            } else if(score.getTrait() == PersonalityTrait.SELF_ACTUALIZATION) {
                Assert.assertEquals("Indirect score for Self Actualization is 1", 1, score.getScore());
            } else if(score.getTrait() == PersonalityTrait.ENERGETIC) {
                Assert.assertEquals("Indirect score for Energetic is 1", 1, score.getScore());
            } else {
                Assert.fail("Unexpected trait!");
            }
        }
    }
}

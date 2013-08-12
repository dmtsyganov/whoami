package org.dnt.whoami.test;

import junit.framework.Assert;
import org.bson.types.ObjectId;
import org.dnt.whoami.dao.DaoClient;
import org.dnt.whoami.dao.InterviewTemplateDao;
import org.dnt.whoami.dao.QuestionDao;
import org.dnt.whoami.model.InterviewTemplate;
import org.dnt.whoami.model.PersonalityTrait;
import org.dnt.whoami.model.Question;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 6:46 PM
 */
public class TestInterviewTemplateDao extends TestBase  {

    private static InterviewTemplateDao templateDao;
    private static QuestionDao questionDao;

    @BeforeClass
    public static void setDao() {
        templateDao = DaoClient.Instance.getInterviewTemplateDao();
        questionDao = DaoClient.Instance.getQuestionDao();

        // clear all interviews
        List<InterviewTemplate> templates = templateDao.read(null);
        for(InterviewTemplate t: templates) {
            templateDao.delete(t);
        }

        // clear all interview questions
        List<Question> questions = questionDao.read(null);
        for(Question q: questions) {
            questionDao.delete(q);
        }
    }

    @Test
    public void testInterviewTemplateWithQuestions() {

        InterviewTemplate template = new InterviewTemplate("Interview One", "First Interview", null);
        ObjectId id = templateDao.create(template);
        Assert.assertNotNull("Interview template created", template);
        Assert.assertNotNull("Must have id", template.getObjectId());

        List<Question> questions = new ArrayList<Question>();
        questions.add(new Question("Question One", PersonalityTrait.ONE));
        questions.add(new Question("Question Two", PersonalityTrait.TWO));
        questions.add(new Question("Question Three", PersonalityTrait.THREE));

        List<ObjectId> questionIds = new ArrayList<ObjectId>(questions.size());
        for(Question q: questions) {
            questionDao.create(q);
            Assert.assertNotNull("Must have id", q.getObjectId());
            questionIds.add(q.getObjectId());
        }

        template.setQuestions(questionIds);
        Assert.assertTrue("Updated with questions", templateDao.update(template));
    }
}

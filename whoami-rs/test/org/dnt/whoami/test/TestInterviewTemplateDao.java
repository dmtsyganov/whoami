package org.dnt.whoami.test;

import junit.framework.Assert;
import org.bson.types.ObjectId;
import org.dnt.whoami.dao.DaoClient;
import org.dnt.whoami.dao.InterviewTemplateDao;
import org.dnt.whoami.model.InterviewTemplate;
import org.dnt.whoami.model.PersonalityTrait;
import org.dnt.whoami.model.Question;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for InterviewTemplate dao
 *
 * @author dima
 * @since 8/11/13 6:46 PM
 */
public class TestInterviewTemplateDao extends TestBase  {

    private static InterviewTemplateDao templateDao;

    @BeforeClass
    public static void setDao() {
        templateDao = DaoClient.Instance.getInterviewTemplateDao();

        // clear all interviews
        List<InterviewTemplate> templates = templateDao.find(null);
        for(InterviewTemplate t: templates) {
            templateDao.delete(t);
        }
    }

    @Test
    public void testInterviewTemplateWithQuestions() {

        InterviewTemplate template = new InterviewTemplate("Interview One", "First Interview", true, null);
        templateDao.create(template);
        ObjectId id = template.getObjectId();
        Assert.assertNotNull("Interview template created", template);
        Assert.assertNotNull("Must have id", template.getObjectId());

        List<Question> questions = new ArrayList<Question>();
        questions.add(new Question("Question One", PersonalityTrait.CONNOTATIVE,
                Question.Type.DIRECT, Question.ValueType.SCORE));
        questions.add(new Question("Question Two", PersonalityTrait.DOMINANT,
                Question.Type.DIRECT, Question.ValueType.YES_NO));
        questions.add(new Question("Question Three", PersonalityTrait.INTELLECTUAL,
                Question.Type.INDIRECT, Question.ValueType.SCORE));

        template.setQuestions(questions);
        Assert.assertTrue("Updated with questions", templateDao.update(template));
    }
}

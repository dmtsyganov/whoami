package org.dnt.whoami.rest;

import com.mongodb.MongoException;
import org.bson.types.ObjectId;
import org.dnt.whoami.dao.DaoClient;
import org.dnt.whoami.dao.InterviewTemplateDao;
import org.dnt.whoami.dao.QuestionDao;
import org.dnt.whoami.model.InterviewTemplate;
import org.dnt.whoami.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 4:53 AM
 */
@Path("/templates")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class InterviewTemplateResource {

    private final Logger logger = LoggerFactory.getLogger(InterviewTemplateResource.class);

    @Context
    UriInfo uriInfo;

    @GET
    public Response getTemplates() {
        InterviewTemplateDao interviewTemplateDao = DaoClient.Instance.getInterviewTemplateDao();

        List<InterviewTemplate> templates = interviewTemplateDao.read(null);
        GenericEntity<List<InterviewTemplate>> entity = new GenericEntity<List<InterviewTemplate>>(templates) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("/{id}")
    public Response getTemplate(@PathParam("id") String id) {
        InterviewTemplateDao interviewTemplateDao = DaoClient.Instance.getInterviewTemplateDao();
        InterviewTemplate template = new InterviewTemplate();
        template.setId(id);
        List<InterviewTemplate> templates = interviewTemplateDao.read(template);

        if (templates.size() > 0) {
            if (templates.size() > 1)
                logger.error("Multiple templates match template id {}", id);

            return Response.ok(templates.get(0)).build();
        }

        logger.debug("Interview template is not found for id {}", id);
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response setTemplate(InterviewTemplate template) {
        InterviewTemplateDao interviewTemplateDao = DaoClient.Instance.getInterviewTemplateDao();
        ObjectId objectId;

        try {
            objectId = interviewTemplateDao.create(template);
        } catch (MongoException.DuplicateKey e) {
            logger.error("Duplicate interview id {}", template.getId());
            return Response.status(Response.Status.CONFLICT).build();
        }

        if (objectId == null) {
            logger.error("Unable to create interview template {}", template);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String id = objectId.toString();
        logger.debug("Interview template created with id {}", id);
        // build new resource uri
        StringBuilder uriString = new StringBuilder(uriInfo.getBaseUri().toString());
        uriString.append(uriInfo.getPath());
        uriString.append("/");
        uriString.append(id);
        URI uri = UriBuilder.fromUri(uriString.toString()).build();
        return Response.created(uri).entity(id).build();
    }

    @PUT
    public Response updateTemplate(InterviewTemplate template) {
        InterviewTemplateDao interviewTemplateDao = DaoClient.Instance.getInterviewTemplateDao();

        if (!interviewTemplateDao.update(template)) {
            logger.error("Unable to update interview template {}", template);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger.debug("Interview template updated {}", template);
        return Response.noContent().build();
    }

    @DELETE
    public Response deleteTemplate(InterviewTemplate template) {
        InterviewTemplateDao interviewTemplateDao = DaoClient.Instance.getInterviewTemplateDao();
        InterviewTemplate deleted = interviewTemplateDao.delete(template);

        if (deleted == null) {
            logger.error("Unable to delete interview template {}", template);

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

    // Questions

    @GET
    @Path("/{id}/questions")
    public Response getInterviewQuestions(@PathParam("id") String id) {
        InterviewTemplateDao interviewTemplateDao = DaoClient.Instance.getInterviewTemplateDao();
        InterviewTemplate template = new InterviewTemplate();
        template.setId(id);
        List<InterviewTemplate> templates = interviewTemplateDao.read(template);

        if (templates.size() > 0) {
            if (templates.size() > 1)
                logger.error("Multiple records match interview template id {}", id);

            List<ObjectId> questionIds = templates.get(0).getQuestions();
            if(questionIds != null) {
                QuestionDao questionDao = DaoClient.Instance.getQuestionDao();
                List<Question> questions = new ArrayList<Question>(questionIds.size());
                Question q = new Question();
                for(ObjectId oId: questionIds) {
                    q.setObjectId(oId);
                    List<Question> found = questionDao.read(q);
                    if(found != null && !found.isEmpty()) {
                        questions.add(found.get(0));
                    } else {
                        logger.error("Question with id {} is referenced but not found", q.getId());
                    }
                }

                GenericEntity<List<Question>> entity = new GenericEntity<List<Question>>(questions) {
                };
                return Response.ok(entity).build();
            }
        }

        logger.debug("Interview template with id {} has no questions", id);
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/{id}/questions")
    public Response setInterviewQuestions(@PathParam("id") String id, List<Question> questions) {
        InterviewTemplateDao interviewTemplateDao = DaoClient.Instance.getInterviewTemplateDao();
        InterviewTemplate template = new InterviewTemplate();
        template.setId(id);
        List<InterviewTemplate> records = interviewTemplateDao.read(template);

        if (records.size() == 0) {
            logger.debug("Interview template not found for id {}", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (records.size() > 1) {
            logger.error("Multiple records match user id {}", id);
        }

        // now make sure that questions exist (have id)
        QuestionDao questionDao = DaoClient.Instance.getQuestionDao();
        List<ObjectId> questionIds = new ArrayList<ObjectId>(questions.size());
        for(Question q: questions) {
            if(q.getObjectId() == null) {
                questionDao.create(q);
            } else {
                questionDao.update(q);
            }
            questionIds.add(q.getObjectId());
        }

        // set new question
        records.get(0).setQuestions(questionIds);

        if (!interviewTemplateDao.update(records.get(0))) {
            logger.error("Unable to update interview template {} with new questions", records.get(0));
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger.debug("Questions updated for interview template with id {}", id);
        // build new resource uri
        StringBuilder uriString = new StringBuilder(uriInfo.getBaseUri().toString());
        uriString.append(uriInfo.getPath());
        uriString.append("/");
        uriString.append(id);
        uriString.append("/questions");
        URI uri = UriBuilder.fromUri(uriString.toString()).build();
        return Response.created(uri).entity(id).build();
    }



}

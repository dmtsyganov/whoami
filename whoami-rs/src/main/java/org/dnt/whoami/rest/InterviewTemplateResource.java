package org.dnt.whoami.rest;

import com.google.code.morphia.Key;
import com.mongodb.MongoException;
import org.bson.types.ObjectId;
import org.dnt.whoami.dao.InterviewTemplateDao;
import org.dnt.whoami.dao.QuestionDao;
import org.dnt.whoami.model.InterviewTemplate;
import org.dnt.whoami.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Interview Templates resource class
 *
 * @author dima
 * @since 8/11/13 4:53 AM
 */
@Path("/templates")
@Consumes("application/json; charset=utf-8")
@Produces("application/json; charset=utf-8")
public class InterviewTemplateResource {

    private final Logger logger = LoggerFactory.getLogger(InterviewTemplateResource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    InterviewTemplateDao interviewTemplateDao;

    @Inject
    QuestionDao questionDao;

    @GET
    public Response getTemplates() {
        List<InterviewTemplate> templates = interviewTemplateDao.find(null);
        GenericEntity<List<InterviewTemplate>> entity = new GenericEntity<List<InterviewTemplate>>(templates) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("/{id}")
    public Response getTemplate(@PathParam("id") String id) {

        InterviewTemplate interviewTemplate = interviewTemplateDao.read(new InterviewTemplate(id));

        if (interviewTemplate != null) {
            return Response.ok(interviewTemplate).build();
        }

        logger.debug("Interview template is not found for id {}", id);
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response setTemplate(InterviewTemplate template) {
        if(template.getObjectId() == null) {
            Key<InterviewTemplate> key;
            try {
                key = interviewTemplateDao.create(template);
            } catch (MongoException.DuplicateKey e) {
                logger.error("Duplicate interview id {}", template.getId());
                return Response.status(Response.Status.CONFLICT).build();
            }

            if (key == null) {
                logger.error("Unable to create interview template {}", template);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            String id = template.getId();
            logger.debug("Interview template created with id {}", id);
            // build new resource uri
            StringBuilder uriString = new StringBuilder(uriInfo.getBaseUri().toString());
            uriString.append(uriInfo.getPath());
            uriString.append("/");
            uriString.append(id);
            URI uri = UriBuilder.fromUri(uriString.toString()).build();
            return Response.created(uri).entity(id).build();
        } else {
            if (!interviewTemplateDao.update(template)) {
                logger.error("Unable to update interview template {}", template);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            logger.debug("Interview template updated {}", template);
            return Response.noContent().build();
        }
    }

    @DELETE
    public Response deleteTemplate(InterviewTemplate template) {
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

        InterviewTemplate interviewTemplate = interviewTemplateDao.read(new InterviewTemplate(id));

        if (interviewTemplate != null) {
            List<ObjectId> questionIds = interviewTemplate.getQuestions();
            if(questionIds != null) {
                List<Question> questions = new ArrayList<Question>(questionIds.size());
                Question q = new Question();
                for(ObjectId oId: questionIds) {
                    q.setObjectId(oId);
                    Question found = questionDao.read(q);
                    if(found != null) {
                        questions.add(found);
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

        InterviewTemplate interviewTemplate = interviewTemplateDao.read(new InterviewTemplate(id));

        if (interviewTemplate == null) {
            logger.debug("Interview template not found for id {}", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // now make sure that questions exist (have id)
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
        interviewTemplate.setQuestions(questionIds);

        if (!interviewTemplateDao.update(interviewTemplate)) {
            logger.error("Unable to update interview template {} with new questions", interviewTemplate);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger.debug("Questions updated for interview template with id {}", id);
        return Response.noContent().build();
    }
}

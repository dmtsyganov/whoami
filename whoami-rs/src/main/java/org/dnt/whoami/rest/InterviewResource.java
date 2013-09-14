package org.dnt.whoami.rest;

import com.google.code.morphia.Key;
import org.bson.types.ObjectId;
import org.dnt.whoami.dao.InterviewDao;
import org.dnt.whoami.dao.InterviewTemplateDao;
import org.dnt.whoami.dao.UserDao;
import org.dnt.whoami.model.*;
import org.dnt.whoami.utils.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Interview resource class
 *
 * @author dima
 * @since 5/24/13 12:28 AM
 */
@Path("/interviews")
@Consumes({"application/json; charset=utf-8"})
@Produces({"application/json; charset=utf-8"})
public class InterviewResource {

    private final Logger logger = LoggerFactory.getLogger(InterviewTemplateResource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    InterviewDao interviewDao;

    @Inject
    UserDao userDao;

    @Inject
    InterviewTemplateDao interviewTemplateDao;

    /**
     * Returns all interviews
     *
     * @return Response
     */
    @GET
    public Response getInterviews() {
        List<Interview> interviews = interviewDao.find(null);
        GenericEntity<List<Interview>> entity = new GenericEntity<List<Interview>>(interviews) {
        };

        return Response.ok(entity).build();
    }

    /**
     * Returns all interviews for a user
     *
     * @param userId user id
     * @return Response
     */
    @GET
    @Path("/{userId}")
    public Response getInterviewsForUser(@PathParam("userId") String userId) {

        if (!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        UserRecord record = userDao.read(new UserRecord(userId));

        if (record == null) {
            logger.debug("User record not found for id {}", userId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Interview> interviews = interviewDao.findInterview(userId);
        GenericEntity<List<Interview>> entity = new GenericEntity<List<Interview>>(interviews) {
        };

        return Response.ok(entity).build();
    }

    /**
     * Returns interview for a user and an interview template
     *
     * @param userId     user id
     * @param templateId interview template id
     * @return Response
     */
    @GET
    @Path("/{userId}/{templateId}")
    public Response getInterview(@PathParam("userId") String userId,
                                 @PathParam("templateId") String templateId) {

        if (!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (!ObjectId.isValid(templateId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Interview interview = interviewDao.findInterview(userId, templateId);
        if (interview != null)
            return Response.ok(interview).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Creates or updates interview for a user and an interview template
     *
     * @param userId     user object id
     * @param templateId template object id
     * @param interview  updated interview or null if must be created
     * @return Response
     */
    @POST
    @Path("/{userId}/{templateId}")
    public Response setInterview(@PathParam("userId") String userId,
                                 @PathParam("templateId") String templateId,
                                 Interview interview) {

        if (!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (!ObjectId.isValid(templateId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        UserRecord user = userDao.read(new UserRecord(userId));
        if (user == null) {
            logger.debug("User record not found for id {}", userId);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        InterviewTemplate template = interviewTemplateDao.read(new InterviewTemplate(templateId));
        if (template == null) {
            logger.debug("Interview template not found for id {}", templateId);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // get interview questions
        List<Question> interviewQuestions = template.getQuestions();

        if (interview.getObjectId() == null) {
            // create new interview
            interview.setUserId(user.getId());
            interview.setTemplateId(template.getId());
            interview.setName(template.getName());
            interview.setDescription(template.getDescription());

            List<Answer> answers = new ArrayList<Answer>(interviewQuestions.size());
            for (Question q : interviewQuestions) {
                answers.add(new Answer(
                        q.getText(),
                        q.getTrait(),
                        q.getType(),
                        q.getValueType(),
                        q.getValueEffect())); // add answer object w/o answer value
            }

            interview.setAnswers(answers);

            Key<Interview> key = interviewDao.create(interview);

            if (key == null) {
                logger.error("Unable to create interview {}", interview);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            String id = interview.getId();
            logger.debug("Interview created with id {}", id);
            // build new resource uri
            StringBuilder uriString = new StringBuilder(uriInfo.getBaseUri().toString());
            uriString.append(uriInfo.getPath());
            uriString.append("/");
            uriString.append(id);
            URI uri = UriBuilder.fromUri(uriString.toString()).build();
            return Response.created(uri).entity(interview).build();

        } else {
            // update interview
            if (!userId.equals(interview.getUserId())) {
                logger.error("User ids do not match: {} vs {}", userId, interview.getUserId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            if (!templateId.equals(interview.getTemplateId())) {
                logger.error("Template ids do not match: {} vs {}", templateId, interview.getTemplateId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            if (!interviewDao.update(interview)) {
                logger.error("Unable to update interview {}", interview);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            logger.debug("Interview updated id {}", interview.getObjectId().toString());

            return Response.noContent().build();
        }
    }

    /**
     * Update the interview
     *
     * @param interview interview object
     * @return  {@link Response}
     */
    @POST
    @Path("/{Id}")
    public Response updateInterview(Interview interview) {

        if (!interviewDao.update(interview)) {
            logger.error("Unable to update interview {}", interview);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger.debug("Interview updated id {}", interview.getObjectId().toString());

        return Response.noContent().build();
    }

    @GET
    @Path("/{userId}/results")
    public Response getInterviewsResultsForUser(@PathParam("userId") String userId) {

        // get interviews first
        if (!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        UserRecord record = userDao.read(new UserRecord(userId));

        if (record == null) {
            logger.debug("User record not found for id {}", userId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        List<Interview> interviews = interviewDao.findInterview(userId);

        List<InterviewResult> results = new ArrayList<InterviewResult>(interviews.size());

        for (Interview interview : interviews) {
            results.add(Calculator.calcInterviewResult(interview));
        }

        GenericEntity<List<InterviewResult>> entity = new GenericEntity<List<InterviewResult>>(results) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("/{userId}/{templateId}/result")
    public Response getInterviewResult(@PathParam("userId") String userId,
                                       @PathParam("templateId") String templateId) {

        if (!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (!ObjectId.isValid(templateId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Interview interview = interviewDao.findInterview(userId, templateId);
        if (interview == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        InterviewResult result = Calculator.calcInterviewResult(interview);

        return Response.ok(result).build();
    }
}

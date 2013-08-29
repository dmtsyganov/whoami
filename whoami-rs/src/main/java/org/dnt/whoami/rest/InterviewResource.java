package org.dnt.whoami.rest;

import com.google.code.morphia.Key;
import org.bson.types.ObjectId;
import org.dnt.whoami.dao.InterviewDao;
import org.dnt.whoami.dao.InterviewTemplateDao;
import org.dnt.whoami.dao.QuestionDao;
import org.dnt.whoami.dao.UserDao;
import org.dnt.whoami.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interview resource class
 * @author dima
 * @since  5/24/13 12:28 AM
 */
@Path("/interviews")
@Consumes("application/json; charset=utf-8")
@Produces("application/json; charset=utf-8")
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

    @Inject
    QuestionDao questionDao;

    /**
     * Returns all interviews
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
     * @param userId user id
     * @return Response
     */
    @GET
    @Path("/{userId}")
    public Response getInterviewsForUser(@PathParam("userId") String userId) {

        if(!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        UserRecord record = userDao.read(new UserRecord(userId));

        if (record == null) {
            logger.debug("User record not found for id {}", userId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        ObjectId id = new ObjectId(userId);
        List<Interview> interviews = interviewDao.findInterview(id);
        GenericEntity<List<Interview>> entity = new GenericEntity<List<Interview>>(interviews) {
        };

        return Response.ok(entity).build();
    }

    /**
     * Returns interview for a user and an interview template
     * @param userId user id
     * @param templateId interview template id
     * @return Response
     */
    @GET
    @Path("/{userId}/{templateId}")
    public Response getInterview(@PathParam("userId") String userId,
                                 @PathParam("templateId") String templateId) {

        if(!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(!ObjectId.isValid(templateId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ObjectId uId = new ObjectId(userId);
        ObjectId tId = new ObjectId(templateId);

        Interview interview = interviewDao.findInterview(uId, tId);
        if(interview != null)
            return Response.ok(interview).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Creates or updates interview for a user and an interview template
     * @param userId user object id
     * @param templateId template object id
     * @param interview updated interview or null if must be created
     * @return Response
     */
    @POST
    @Path("/{userId}/{templateId}")
    public Response setInterview(@PathParam("userId") String userId,
                                 @PathParam("templateId") String templateId,
                                 Interview interview) {

        if(!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        UserRecord user = userDao.read(new UserRecord(userId));
        if (user == null) {
            logger.debug("User record not found for id {}", userId);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(!ObjectId.isValid(templateId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        InterviewTemplate template = interviewTemplateDao.read(new InterviewTemplate(templateId));
        if (template == null) {
            logger.debug("Interview template not found for id {}", templateId);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // get interview questions
        List<Question> interviewQuestions = new ArrayList<Question>(template.getQuestions().size());
        Question questionTemplate = new Question();
        for(ObjectId qId: template.getQuestions()) {
            questionTemplate.setObjectId(qId);
            Question theQuestion = questionDao.read(questionTemplate);
            if(theQuestion != null) {
                interviewQuestions.add(theQuestion);
            } else {
                logger.error("Question is not found with id {}", qId);
            }
        }

        if(interview.getObjectId() == null) {
            // create new interview
            interview.setUserId(user.getObjectId());
            interview.setTemplateId(template.getObjectId());

            List<Answer> answers = new ArrayList<Answer>(interviewQuestions.size());
            for(Question q: interviewQuestions) {
                answers.add(new Answer(q.getObjectId(), q.getTrait(), q.getType(), q.getValueType())); // add answer object w/o answer value
            }

            interview.setAnswers(answers);

            Key<Interview> key = interviewDao.create(interview);

            if(key == null) {
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
            return Response.created(uri).entity(id).build();

        } else {
            // update interview
            if(!userId.equals(interview.getUserId().toString())) {
                logger.error("User ids do not match: {} vs {}", userId, interview.getUserId().toString());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            if(!templateId.equals(interview.getTemplateId().toString())) {
                logger.error("Template ids do not match: {} vs {}", templateId, interview.getTemplateId().toString());
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

    @GET
    @Path("/{userId}/results")
    public Response getInterviewsResultsForUser(@PathParam("userId") String userId) {

        // get interviews first
        if(!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        UserRecord record = userDao.read(new UserRecord(userId));

        if (record == null) {
            logger.debug("User record not found for id {}", userId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        ObjectId id = new ObjectId(userId);
        List<Interview> interviews = interviewDao.findInterview(id);

        List<InterviewResult> results = new ArrayList<InterviewResult>(interviews.size());

        for(Interview interview: interviews) {
            results.add(getInterviewResult(interview));
        }

        GenericEntity<List<InterviewResult>> entity = new GenericEntity<List<InterviewResult>>(results) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("/{userId}/{templateId}/result")
    public Response getInterviewResult(@PathParam("userId") String userId,
                                      @PathParam("templateId") String templateId) {

        if(!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(!ObjectId.isValid(templateId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ObjectId uId = new ObjectId(userId);
        ObjectId tId = new ObjectId(templateId);

        Interview interview = interviewDao.findInterview(uId, tId);
        if(interview == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        InterviewResult result = getInterviewResult(interview);

        return Response.ok(result).build();
    }

    // Utility
    private InterviewResult getInterviewResult(Interview interview) {

        Map<PersonalityTrait, TraitScore> scores = new HashMap<PersonalityTrait, TraitScore>();
        boolean isComplete = true;
        for(Answer answer: interview.getAnswers()) {

            TraitScore score = scores.get(answer.getTrait());

            if(answer.getValue() == null) {
                isComplete = false;
            } else {
                if(score == null) {
                    score = new TraitScore(answer.getTrait(), getScore(answer));
                    scores.put(answer.getTrait(), score);
                } else {
                    score.setScore(score.getScore() + getScore(answer));
                }
            }
        }

        //TODO: divide scores by max # trait questions / max score (eg 14 / 7 = 2 - divide by 2 etc.)
        List<TraitScore> totalScores = new ArrayList<TraitScore>();
        for(TraitScore ts : scores.values()) {
            totalScores.add(ts);
        }

        return new InterviewResult(interview.getUserId().toString(),
                interview.getTemplateId().toString(),
                interview.getId(),
                totalScores,
                isComplete);
    }

    private Integer getScore(Answer answer) {
        Integer score = 0;

        try {
            switch(answer.getValueType()) {
                case SCORE:
                    score = Integer.valueOf(answer.getValue()); // from 0 up to max score (7)
                    break;
                case YES_NO:
                    score = Integer.valueOf(answer.getValue()); // 1 - yes, 0 - no
                    break;
            }
        } catch(NumberFormatException e) {
            logger.error("Unable to parse the value: {}", e);
        }

        return score;
    }
}

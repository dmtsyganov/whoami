package org.dnt.whoami.rest;

import org.bson.types.ObjectId;
import org.dnt.whoami.dao.InterviewDao;
import org.dnt.whoami.model.Interview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Interview resource class
 * @author dima
 * @since  5/24/13 12:28 AM
 */
@Path("/interviews")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class InterviewResource {

    private final Logger logger = LoggerFactory.getLogger(InterviewTemplateResource.class);

    @Context
    UriInfo uriInfo;

    @Context
    InterviewDao interviewDao;

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
     * @param userId
     * @return Response
     */
    @GET
    @Path("/{userId}")
    public Response getInterviewsForUser(@PathParam("userId") String userId) {

        if(!ObjectId.isValid(userId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ObjectId id = new ObjectId(userId);
        List<Interview> interviews = interviewDao.findInterview(id);
        GenericEntity<List<Interview>> entity = new GenericEntity<List<Interview>>(interviews) {
        };

        return Response.ok(entity).build();
    }

    /**
     * Returns interview for a user and an interview template
     * @param userId
     * @param templateId
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
        return Response.ok().build();
    }
}

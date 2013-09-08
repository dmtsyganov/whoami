package org.dnt.whoami.rest;

import com.google.code.morphia.Key;
import com.mongodb.MongoException;
import org.bson.types.ObjectId;
import org.dnt.whoami.dao.InterviewTemplateDao;
import org.dnt.whoami.model.InterviewTemplate;
import org.dnt.whoami.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Interview Templates resource class
 *
 * @author dima
 * @since 8/11/13 4:53 AM
 */
@Path("/templates")
@Consumes({"application/json; charset=utf-8"})
@Produces({"application/json; charset=utf-8"})
public class InterviewTemplateResource {

    private final Logger logger = LoggerFactory.getLogger(InterviewTemplateResource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    InterviewTemplateDao interviewTemplateDao;

    @GET
    public Response getTemplates(@DefaultValue("false") @QueryParam("active") boolean active) {
        List<InterviewTemplate> templates = interviewTemplateDao.find(null);

        if(active == true) {
            // remove inactive
            Iterator<InterviewTemplate> it = templates.listIterator();
            while(it.hasNext()) {
                if(!it.next().isActive()) {
                    it.remove();
                }
            }
        }

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
            return Response.created(uri).entity(template).build();
        } else {
            if (!interviewTemplateDao.update(template)) {
                logger.error("Unable to update interview template {}", template);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            logger.debug("Interview template updated {}", template);
            return Response.noContent().build();
        }
    }

    @POST
    @Path("/{id}")
    public Response updateTemplate(@PathParam("id") String id, InterviewTemplate template) {
        if(template.getObjectId() == null) {
            logger.error("Unable to update interview template {}", template);
            return Response.status(Response.Status.BAD_REQUEST).build();
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
//    @Consumes({"application/xml"})
    @Path("/{id}")
    public Response deleteTemplate(@PathParam("id") String id, InterviewTemplate template) {
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
            List<Question> questions = interviewTemplate.getQuestions();
            if(questions != null) {
                GenericEntity<List<Question>> entity = new GenericEntity<List<Question>>(questions) {
                };
                return Response.ok(entity).build();
            } else {
                return Response.ok(new GenericEntity<List<Question>>(Collections.<Question>emptyList()){}).build();
            }
        }

        logger.debug("Interview template with id {} has no questions", id);
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

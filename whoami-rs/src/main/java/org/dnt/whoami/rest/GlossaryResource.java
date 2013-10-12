package org.dnt.whoami.rest;

import com.google.code.morphia.Key;
import com.mongodb.MongoException;
import org.dnt.whoami.dao.GlossaryDao;
import org.dnt.whoami.model.GlossaryRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * The Glossary resource class
 *
 * @author dima
 * @since 5/24/13 12:28 AM
 */
@Path("/glossary")
@Consumes({"application/json; charset=utf-8"})
@Produces({"application/json; charset=utf-8"})
public class GlossaryResource {

    private final Logger logger = LoggerFactory.getLogger(GlossaryResource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    GlossaryDao glossaryDao;

    @GET
    public Response getEntries() {
        List<GlossaryRecord> records = glossaryDao.find(null);
        GenericEntity<List<GlossaryRecord>> entity = new GenericEntity<List<GlossaryRecord>>(records) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("/{term}")
    public Response getTerm(@PathParam("term") String term) {
        GlossaryRecord template = new GlossaryRecord();
        template.setTerm(term);
        GlossaryRecord record = glossaryDao.read(template);

        if (record != null) {
            return Response.ok(record).build();
        }

        logger.debug("Glossary record not found for term {}", term);
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    public Response setTerm(GlossaryRecord record) {

        if(record.getObjectId() == null) {
            // create new glossary record
            Key<GlossaryRecord> key;
            try {
                key = glossaryDao.create(record);
            } catch (MongoException.DuplicateKey e) {
                logger.error("Duplicate term {}", record.getTerm());
                return Response.status(Response.Status.CONFLICT).build();
            }

            if (key == null) {
                logger.error("Unable to create term {}", record);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            String id = record.getId();
            logger.debug("Term definition created with id {}", id);
            // build new resource uri
            StringBuilder uriString = new StringBuilder(uriInfo.getBaseUri().toString());
            uriString.append(uriInfo.getPath());
            uriString.append("/");
            uriString.append(id);
            URI uri = UriBuilder.fromUri(uriString.toString()).build();
            return Response.created(uri).entity(record).build();
        } else {
            // update glossary record
            if (!glossaryDao.update(record)) {
                logger.error("Unable to update glossary record {}", record);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            logger.debug("Glossary term updated with id {}", record.getId());
            return Response.noContent().build();
        }
    }

    @POST
    @Path("/{id}")
    public Response updateTerm(GlossaryRecord record) {
        if(record.getObjectId() == null) {
            logger.error("Unable to update term {}", record);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            if (!glossaryDao.update(record)) {
                logger.error("Unable to update term {}", record);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            logger.debug("Glossary term updated with id {}", record.getId());
            return Response.noContent().build();
        }
    }

    @DELETE
    public Response deleteTerm(GlossaryRecord record) {
        GlossaryRecord deleted = glossaryDao.delete(record);
        if (deleted == null) {
            logger.error("Unable to delete term {}", record);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

}

package org.dnt.whoami.rest;

import com.google.code.morphia.Key;
import com.mongodb.MongoException;
import org.dnt.whoami.dao.DictionaryDao;
import org.dnt.whoami.model.DictionaryRecord;
import org.dnt.whoami.model.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * The Dictionary resource class
 *
 * @author dima
 * @since 5/24/13 12:28 AM
 */
@Path("/dictionary")
@Consumes({"application/json; charset=utf-8"})
@Produces({"application/json; charset=utf-8"})
public class DictionaryResource {

    private final Logger logger = LoggerFactory.getLogger(DictionaryResource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    DictionaryDao dictionaryDao;

    @GET
    public Response getEntries() {
        List<DictionaryRecord> records = dictionaryDao.find(null);
        GenericEntity<List<DictionaryRecord>> entity = new GenericEntity<List<DictionaryRecord>>(records) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("/{entry}")
    public Response getEntry(@PathParam("entry") String entry) {
        DictionaryRecord template = new DictionaryRecord();
        template.setEntry(entry);
        DictionaryRecord record = dictionaryDao.read(template);

        if (record != null) {
            return Response.ok(record).build();
        }

        logger.debug("Dictionary record not found for entry {}", entry);
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    public Response setEntry(DictionaryRecord record) {

        if(record.getObjectId() == null) {
            // create new dictionary record
            Key<DictionaryRecord> key;
            try {
                key = dictionaryDao.create(record);
            } catch (MongoException.DuplicateKey e) {
                logger.error("Duplicate entry {}", record.getEntry());
                return Response.status(Response.Status.CONFLICT).build();
            }

            if (key == null) {
                logger.error("Unable to create entry {}", record);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            String id = record.getId();
            logger.debug("Entry created with id {}", id);
            // build new resource uri
            StringBuilder uriString = new StringBuilder(uriInfo.getBaseUri().toString());
            uriString.append(uriInfo.getPath());
            uriString.append("/");
            uriString.append(id);
            URI uri = UriBuilder.fromUri(uriString.toString()).build();
            return Response.created(uri).entity(record).build();
        } else {
            // update dictionary record
            if (!dictionaryDao.update(record)) {
                logger.error("Unable to update dictionary record {}", record);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            logger.debug("Dictionary entry updated with id {}", record.getId());
            return Response.noContent().build();
        }
    }

    @POST
    @Path("/{id}")
    public Response updateEntry(DictionaryRecord record) {
        if(record.getObjectId() == null) {
            logger.error("Unable to update entry {}", record);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            if (!dictionaryDao.update(record)) {
                logger.error("Unable to update entry {}", record);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            logger.debug("Dictionary entry updated with id {}", record.getId());
            return Response.noContent().build();
        }
    }

    @DELETE
    public Response deleteEntry(DictionaryRecord record) {
        DictionaryRecord deleted = dictionaryDao.delete(record);
        if (deleted == null) {
            logger.error("Unable to delete entry {}", record);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

}

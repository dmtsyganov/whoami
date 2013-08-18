package org.dnt.whoami.rest;

import com.google.code.morphia.Key;
import com.mongodb.MongoException;
import org.dnt.whoami.dao.UserDao;
import org.dnt.whoami.model.UserProfile;
import org.dnt.whoami.model.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * User Resource class
 *
 * @author dima
 * @since 5/24/13 12:28 AM
 */
@Path("/users")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserResource {

    private final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Context
    UriInfo uriInfo;

    @Context
    UserDao userDao;

    @GET
    public Response getUsers() {
        List<UserRecord> records = userDao.find(null);
        GenericEntity<List<UserRecord>> entity = new GenericEntity<List<UserRecord>>(records) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") String id) {
        UserRecord template = new UserRecord();
        template.setId(id);
        UserRecord record = userDao.read(template);

        if (record != null) {
            return Response.ok(record).build();
        }

        logger.debug("User record not found for id {}", id);
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/query")
    public Response getUserByLogin(@QueryParam("login") String login) {
        UserRecord template = new UserRecord();
        template.setLogin(login);

        List<UserRecord> records = userDao.find(template);

        if (records.size() > 0) {
            if (records.size() > 1)
                logger.error("Multiple records match user login {}", login);

            return Response.ok(records.get(0)).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response setUser(UserRecord user) {
        Key<UserRecord> key;
        try {
            key = userDao.create(user);
        } catch (MongoException.DuplicateKey e) {
            logger.error("Duplicate login name {}", user.getLogin());
            return Response.status(Response.Status.CONFLICT).build();
        }

        if (key == null) {
            logger.error("Unable to create user {}", user);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String id = user.getId();
        logger.debug("User created with id {}", id);
        // build new resource uri
        StringBuilder uriString = new StringBuilder(uriInfo.getBaseUri().toString());
        uriString.append(uriInfo.getPath());
        uriString.append("/");
        uriString.append(id);
        URI uri = UriBuilder.fromUri(uriString.toString()).build();
        return Response.created(uri).entity(id).build();
    }

    @PUT
    public Response updateUser(UserRecord user) {
        if (!userDao.update(user)) {
            logger.error("Unable to update user {}", user);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger.debug("User updated {}", user);
        return Response.noContent().build();
    }

    @DELETE
    public Response deleteUser(UserRecord user) {
        UserRecord deleted = userDao.delete(user);
        if (deleted == null) {
            logger.error("Unable to delete user {}", user);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

    // User Profile

    @GET
    @Path("/{id}/profile")
    public Response getUserProfile(@PathParam("id") String id) {
        UserRecord template = new UserRecord();
        template.setId(id);
        UserRecord record = userDao.read(template);

        if (record != null) {
            return Response.ok(record.getProfile()).build();
        }

        logger.debug("User profile not found for id {}", id);
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/{id}/profile")
    public Response setUserProfile(@PathParam("id") String id, UserProfile profile) {
        UserRecord template = new UserRecord();
        template.setId(id);
        UserRecord record = userDao.read(template);

        if (record == null) {
            logger.debug("User profile not found for id {}", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        record.setProfile(profile);

        if (!userDao.update(record)) {
            logger.error("Unable to update user {}", record);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        logger.debug("Profile created for user with id {}", id);
        // build new resource uri
        StringBuilder uriString = new StringBuilder(uriInfo.getBaseUri().toString());
        uriString.append(uriInfo.getPath());
        uriString.append("/");
        uriString.append(id);
        uriString.append("/profile");
        URI uri = UriBuilder.fromUri(uriString.toString()).build();
        return Response.created(uri).entity(id).build();
    }

    @PUT
    @Path("/{id}/profile")
    public Response updateUserProfile(@PathParam("id") String id, UserProfile profile) {
        UserRecord template = new UserRecord();
        template.setId(id);
        UserRecord record = userDao.read(template);

        if (record == null) {
            logger.debug("User profile not found for id {}", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        record.setProfile(profile);

        if (!userDao.update(record)) {
            logger.error("Unable to update user {}", record);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger.debug("User profile updated {}", profile);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/profile")
    public Response deleteUserProfile(@PathParam("id") String id) {
        UserRecord template = new UserRecord();
        template.setId(id);
        UserRecord record = userDao.read(template);

        if (record == null) {
            logger.debug("User profile not found for id {}", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        record.setProfile(null);

        if (!userDao.update(record)) {
            logger.error("Unable to delete user profile {}", record);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }
}

package org.dnt.whoami.rest;

import com.sun.jersey.api.Responses;
import org.dnt.whoami.dao.DaoClient;
import org.dnt.whoami.dao.UserDao;
import org.dnt.whoami.model.UserRecord;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collections;
import java.util.List;

/**
 * TODO: add class description
 * @author dima
 * @since  5/24/13 12:28 AM
 */
@Path("/u")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserResource {

    @GET
    public List<UserRecord> getUsers() {
        UserDao userDao = DaoClient.Instance.getUserDao();
        return userDao.read(null);
    }

    @GET
    @Path("/id/{login}")
    public String getUserId(@PathParam("login") String login) {
        UserDao userDao = DaoClient.Instance.getUserDao();
        UserRecord template = new UserRecord();
        template.setLogin(login);
        List<UserRecord> records = userDao.read(template);

        if(records.size() > 0) { // todo: check if only one
            return records.get(0).getId();
        }

        throw new WebApplicationException(Response.status(Responses.NOT_FOUND).
                entity("User with login <b>" + login + "</b> not found").type("text/html").build());
    }

    @GET
    @Path("/{id}")
    public UserRecord getUser(@PathParam("id") String id) {
        UserDao userDao = DaoClient.Instance.getUserDao();
        UserRecord template = new UserRecord();
        template.setId(id);
        List<UserRecord> records = userDao.read(template);

        if(records.size() > 0) { // todo: check if only one
            return records.get(0);
        }

        throw new WebApplicationException(Response.status(Responses.NOT_FOUND).
                entity("User with id <b>" + id + "</b> not found").type("text/html").build());
    }

    @GET
    @Path("/login/{login}")
    public UserRecord getUserByLogin(@PathParam("login") String login) {
        UserDao userDao = DaoClient.Instance.getUserDao();
        UserRecord template = new UserRecord();
        template.setLogin(login);
        List<UserRecord> records = userDao.read(template);

        if(records.size() > 0) { // todo: check if only one
            return records.get(0);
        }

        throw new WebApplicationException(Response.status(Responses.NOT_FOUND).
                entity("User with login <b>" + login + "</b> not found").type("text/html").build());
    }

    @POST
    public UserRecord setUser(UserRecord user) {
        UserDao userDao = DaoClient.Instance.getUserDao();
        List<UserRecord> records = Collections.EMPTY_LIST;
        if(user.getId() != null) {
            // update user
            if(userDao.update(user)) {
                records = userDao.read(user);
            }
        } else {
            // create user
            records = userDao.read(user);
            if(records.isEmpty()) {
                userDao.create(user);
                records = userDao.read(user);
            } else {
                throw new WebApplicationException(Response.status(Responses.CONFLICT).
                        entity("User with this login <b>" + user.getLogin() + "</b> exists").type("text/html").build());
            }
        }

        return records.isEmpty() ? null : records.get(0);
    }
}

package org.dnt.whoami.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * TODO: add class description
 * @author dima
 * @since  5/24/13 12:28 AM
 */
@Path("/i")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class InterviewResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getWelcome() {
        StringBuilder builder = new StringBuilder();
        builder.append("Welcome to <b>").append("Interview").append("</b>");
        return builder.toString();
    }
}

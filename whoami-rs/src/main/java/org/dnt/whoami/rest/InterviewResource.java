package org.dnt.whoami.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * TODO: add class description
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

}

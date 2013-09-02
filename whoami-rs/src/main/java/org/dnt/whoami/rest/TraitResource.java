package org.dnt.whoami.rest;

import org.dnt.whoami.model.PersonalityTrait;
import org.dnt.whoami.model.Trait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 3:57 PM
 */
@Path("/traits")
@Consumes({"application/json; charset=utf-8"})
@Produces({"application/json; charset=utf-8"})
public class TraitResource {

    private final Logger logger = LoggerFactory.getLogger(TraitResource.class);

    @GET
    public Response getTraits() {

        EnumSet<PersonalityTrait> traitsEnum = EnumSet.allOf(PersonalityTrait.class);

        List<Trait> traits = new ArrayList<Trait>(traitsEnum.size());

        for(PersonalityTrait t: traitsEnum) {
            traits.add(new Trait(t.getCategory(),
                    t.getCategoryName(),
                    t.name(),
                    t.getDescription(),
                    t.getMinScore(),
                    t.getMaxScore()));
        }

        GenericEntity<List<Trait>> entity = new GenericEntity<List<Trait>>(traits) {
        };

        return Response.ok(entity).build();

    }
}

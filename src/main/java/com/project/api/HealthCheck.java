package com.project.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("health-check")
public class HealthCheck {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response check() {
        return Response.ok("API is alive <br> @GET /api/person - List All <br/> @POST /api/person - Create <br/> @PUT /api/person/id - Update <br/> @DELETE By Id /api/person?id={id} - Delete <br/> @DELETE all /api/person - Delete").build();
    }
}

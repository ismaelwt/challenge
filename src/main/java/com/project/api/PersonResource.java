package com.project.api;


import com.google.gson.Gson;
import com.project.model.Person;
import com.project.service.PersonService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("person")
public class PersonResource {

    @Inject
    private PersonService personService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Person> persons = personService.findAll();

        if (persons.isEmpty()) {
            String message = "não há pessoas cadastradas";
            return Response.ok(new Gson().toJson(message)).build();
        }

        return Response.ok(new Gson().toJson(persons)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson(String json) {

        String jsonMessage = null;

        if (json != null && !json.isEmpty()) {
            Person person = new Gson().fromJson(json, Person.class);

            List<String> verify = personService.verify(person);

            if (verify != null && verify.isEmpty()) {
                Person createdPerson = personService.createPerson(person);
                jsonMessage = new Gson().toJson(createdPerson);

            } else {

                return Response.status(Response.Status.BAD_REQUEST).entity(new Gson().toJson(verify)).build();
            }
        }

        return Response.ok(201).entity(new Gson().toJson(jsonMessage)).build();

    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{identifier}")
    public Response updatePerson(@PathParam("identifier") String identifier, String json) {

        Integer rowsAffected = 0;

        if (identifier != null && json != null && !json.isEmpty()) {

            Person p = new Gson().fromJson(json, Person.class);

            rowsAffected = personService.updatePerson(identifier, p);

        }

        return Response.ok(200).entity(new Gson().toJson("linhas afetadas :" + rowsAffected)).build();

    }

    @DELETE
    @Path("/{identifier}")
    public Response deletePerson(@PathParam("identifier") String id) {

        Integer rowsAffected = 0;

        if (id != null && !id.isEmpty()) {

            rowsAffected = personService.deletePerson(id);
        }

        return Response.status(200).entity(new Gson().toJson(rowsAffected)).build();
    }

}

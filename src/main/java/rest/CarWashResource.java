package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import dtos.carwash.AssistantDTO;
import dtos.carwash.WashingAssistantsDTO;
import entities.WashingAssistants;
import errorhandling.API_Exception;

import java.io.IOException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import facades.Facade;
import utils.EMF_Creator;


@Path("carwash")
public class CarWashResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static final Facade facade = Facade.getFacade(EMF);
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user","admin"})
    @Path("verify")
    public Response VerifyConnection() throws IOException, API_Exception {
        //get username from token
        String username = securityContext.getUserPrincipal().getName();
        int number = facade.getUsers();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",username);
        jsonObject.addProperty("number",number);
        return Response.ok().entity(gson.toJson(jsonObject)).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user","admin"})
    @Path("assistants")
    public Response getWashingAssistants() {
        List<WashingAssistants> list = facade.getWashingAssistants();
        WashingAssistantsDTO dto = new WashingAssistantsDTO(list);
        return Response.ok().entity(gson.toJson(dto.getWashingAssistants())).build();
    }

}

package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.*;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import errorhandling.API_Exception;
import utils.EMF_Creator;
import utils.StartDataSet;

@Path("info")
public class InfoResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("test")
    @RolesAllowed({"user","admin"})
    public Response testErrorEndPoint() throws API_Exception {
        List<String> strings = new ArrayList<>();
        try{
            for (int i = 0; i < 10; i++){
                System.out.println(strings.get(i));
            }
        } catch (Exception e){
            throw new API_Exception("FEJL SOM MENINGEN");
        }
        return Response.ok().entity("{\"msg\": \"Hello to User: " + "test" + "\"}").build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET
    @Path("reset")
    public String resetDataSet() {
        StartDataSet setup = new StartDataSet();
        String[] arguments = new String[] {""};
        setup.main(arguments);
        return "Reset";
    }
}
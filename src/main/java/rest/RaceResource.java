/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.carRace.CarDTO;
import dtos.carRace.RaceDTO;
import errorhandling.API_Exception;
import facades.RaceFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 *
 * @author peter
 */
@Path("race")
public class RaceResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final RaceFacade FACADE =  RaceFacade.getRaceFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Welcome to RacePage\"}";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("show")
    public Response showRaces() throws API_Exception {
        List<RaceDTO> raceDTOs = RaceDTO.getDTOs(FACADE.getAllRacesFromEntity());
        return Response.ok().entity(GSON.toJson(raceDTOs)).build();
    }
    
    
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response showCars(@PathParam("id") long id) {
    
        RaceDTO raceDTO = new RaceDTO(FACADE.getRaceById(id));
        List<CarDTO> carDTOs = CarDTO.getDTOs(FACADE.getCarsByRace(id));
        raceDTO.setCars(carDTOs);
        return Response.ok().entity(GSON.toJson(raceDTO)).build();
        
    }
    
    @Path("addrace")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response addRace(String jsonString){
        
        RaceDTO rDTO = GSON.fromJson(jsonString, RaceDTO.class);;
        FACADE.createRace(rDTO.CreateRace());
        
        return Response.ok(rDTO).status(201).build();
    }
    
}


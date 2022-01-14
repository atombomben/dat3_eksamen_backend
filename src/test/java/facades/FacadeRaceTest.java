/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Race;
import errorhandling.API_Exception;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import static junit.framework.Assert.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.StartDataSet;

/**
 *
 * @author peter
 */
public class FacadeRaceTest {
 
    private static EntityManagerFactory emf;
    private static RaceFacade facade;

    public FacadeRaceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RaceFacade.getRaceFacade(emf);
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        StartDataSet.setupInitialData(emf);
    }

    
    @Test
    public void testGetRaces() throws API_Exception {
        List<Race> races = facade.getAllRacesFromEntity();
        assertEquals(3,races.size());
    }
    
    @Test
    public void testCreateRace() throws API_Exception {
        Race race = new Race("Test","Test","Test","Test");
        facade.createRace(race);
        List<Race> races = facade.getAllRacesFromEntity();
        assertEquals(4, races.size());
    }
    
    
    @Test
    public void testGetPersonById() throws API_Exception{
        List<Race> races = facade.getAllRacesFromEntity();
        Race race = facade.getRaceById(races.get(1).getId());
        assertEquals(races.get(1).getId(),race.getId());
    }
    
    @Test
    public void testEditRace() throws API_Exception {
        List<Race> races = facade.getAllRacesFromEntity();
        Race race = facade.getRaceById(races.get(1).getId());
        race.setName("testEDITED");
        facade.editRace(race);
        Race edittedRaceTest = facade.getRaceById(race.getId());
        assertEquals("testEDITED", edittedRaceTest.getName());
    }

    @Test
    public void testRemoveRace() throws API_Exception{
        
        List<Race> races = facade.getAllRacesFromEntity();
        Race race = facade.getRaceById(races.get(1).getId());
        facade.removeRace(race.getId());
        assertEquals(2,facade.getAllRacesFromEntity().size());
        
    }
    
    
}

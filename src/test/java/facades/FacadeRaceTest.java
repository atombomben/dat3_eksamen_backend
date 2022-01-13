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
    public void testGetPersonByAddress() throws API_Exception {
        
        List<Race> races = facade.getAllRacesFromEntity();
        assertEquals(2,races.size());
       
    }
    
}

package facades;

import entities.Car;
import errorhandling.API_Exception;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.StartDataSet;

/**
 *
 * @author peter
 */
public class FacadeCarTest extends TestCase {
    
    private static EntityManagerFactory emf;
    private static CarFacade facade;

    public FacadeCarTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CarFacade.getCarFacade(emf);
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        StartDataSet.setupInitialData(emf);
    }

    @Test
    public void testGetCars() throws API_Exception {
        List<Car> cars = facade.getAllCarsFromEntity();
        assertEquals(10,cars.size());
    }
}

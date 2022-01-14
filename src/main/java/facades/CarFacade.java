/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Car;
import errorhandling.API_Exception;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author peter
 */
public class CarFacade {
    
    private static EntityManagerFactory emf;
    private static CarFacade instance;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private CarFacade() {
    }

    public static CarFacade getCarFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }
    
    public List<Car> getAllCarsFromEntity() throws API_Exception {
        
        EntityManager em = emf.createEntityManager();
        try {
        
        TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c", Car.class);
        return query.getResultList();
        
        } catch (Exception e) {
            throw new API_Exception(e.getMessage());
        }

}
    
    
}

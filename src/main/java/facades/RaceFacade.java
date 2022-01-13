/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Car;
import entities.Race;
import errorhandling.API_Exception;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author peter
 */
public class RaceFacade {
    
    private static EntityManagerFactory emf;
    private static RaceFacade instance;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private RaceFacade() {
    }

    public static RaceFacade getRaceFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RaceFacade();
        }
        return instance;
    }
    
     public Race createRace(Race race) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(race);  
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return race;
    }
    
    
    public List<Race> getAllRacesFromEntity() throws API_Exception {
        
        EntityManager em = emf.createEntityManager();
        try {
        
        TypedQuery<Race> query = em.createQuery("SELECT r FROM Race r", Race.class);
        return query.getResultList();
        
        } catch (Exception e) {
            throw new API_Exception(e.getMessage());
        }
    }

    
    public Race getRaceById (long id) {
        
          EntityManager em = emf.createEntityManager();
        try {
        TypedQuery<Race> q1 = em.createQuery("SELECT r FROM Race r WHERE r.id = " + id, Race.class);
        List<Race> races = q1.getResultList();
        for (Race r : races) {
            return r;
        }
        
        return null;
        
    } finally {
            em.close();
        }
    }


    
   public Race editRace(Race race) {
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.merge(race);
            em.getTransaction().commit();
            return race;
        } finally {
            em.close();
        }
    }
    
    public void removeRace(long id) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        Race race;
        try {
            em.getTransaction().begin();
            race = em.find(Race.class, id);
            if (race == null) {
                throw new WebApplicationException("Id does not exist");
            }
            em.remove(race);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
      }
    
    
    public List<Car> getCarsByRace(long id) throws WebApplicationException {
        
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> q1 = em.createQuery("SELECT c FROM Car c INNER JOIN c.races r WHERE r.id = :id", Car.class);
            q1.setParameter("id", id);
            List<Car> cars = q1.getResultList();
            return cars;
        } finally {
            em.close();
        }
    }
    
}

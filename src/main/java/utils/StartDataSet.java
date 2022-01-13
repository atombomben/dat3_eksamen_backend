package utils;

import entities.Car;
import entities.Race;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class StartDataSet {

    public static User user,admin,both;
    public static Role userRole,adminRole;
    public static Race r1, r2;
    public static Car c1, c2, c3;

    public static void main(String[] args) {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        setupInitialData(emf);
    }

    //Entity managerFactory is deciding whether the data is to test or prod database.
    //Is called both from rest and test cases
    public static void setupInitialData(EntityManagerFactory _emf){
        EntityManager em = _emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.createNamedQuery("Race.deleteAllRows").executeUpdate();
            


            user = new User("user", "testUser");
            admin = new User("admin", "testAdmin");
            both = new User("user_admin", "testBoth");

            userRole = new Role("user");
            adminRole = new Role("admin");
            
            Race r1 = new Race("Grand tour", "13-01-2022", "22:00:00", "Danmark");
            Race r2 = new Race("Grand tour", "14-01-2022", "20:00:00", "Danmark");
            
            Car c1 = new Car("Den hurtigste", "Mercedes", "V1", "2010");
            Car c2 = new Car("Den midlige", "Ferrari", "V2", "2005");
            Car c3 = new Car("Den langsomme", "Lamborghini", "V3", "2000");
            
            r1.addCar(c1);
            r1.addCar(c2);
            r1.addCar(c3);
            
            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);

            em.persist(userRole);
            em.persist(adminRole);

            em.persist(user);
            em.persist(admin);
            em.persist(both);
            
            em.persist(r1);
            em.persist(r2);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
}

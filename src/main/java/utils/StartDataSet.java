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
    public static Race r1, r2, r3;
    public static Car c1, c2, c3, c4, c5, c6, c7, c8, c9, c10;

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
            
            Race r1 = new Race("Monaco Grand Prix", "Thursday, 26-05-2022", "12:00:00", "Monaco, France");
            Race r2 = new Race("Indianapolis 500", "Sunday, 29-05-2022", "14:00:00", "Indiana, USA");
            Race r3 = new Race("24 Hours of Le Mans", "Saturday, 11-06-2022", "00:00:00", "Le Mans, France");
            
            Car c1 = new Car("Alfetta", "Alfa Romeo", "GTV6", "1975");
            Car c2 = new Car("The Flying Mantuan", "Alfa Romeo", "8C 2300", "1931");
            Car c3 = new Car("Alesaggio Maggiorato", "Alfa Romeo", "GTA", "1966");
            Car c4 = new Car("GT", "Ferrari", "488 GTE", "2020");
            Car c5 = new Car("GT", "Ferrari", "488 GT3 EVO", "2020");
            Car c6 = new Car("THE FXX", "Ferrari", "FXX-K", "2017");
            Car c7 = new Car("Zyrus", "Lamborghini", "LP1200", "2021");
            Car c8 = new Car("Huracan", "Lamborghini", "GT3 Evo", "2022");
            Car c9 = new Car("Lms", "Audi", "R8", "2019");
            Car c10 = new Car("Pfaff", "Audi", "AL", "2020");
            
            
            r1.addCar(c10);
            r1.addCar(c8);
            r1.addCar(c7);
            r1.addCar(c5);
            
            r2.addCar(c2);
            r2.addCar(c1);
            r2.addCar(c3);
            
            r3.addCar(c9);
            r3.addCar(c6);
            r3.addCar(c4);
            
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
            em.persist(r3);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
}

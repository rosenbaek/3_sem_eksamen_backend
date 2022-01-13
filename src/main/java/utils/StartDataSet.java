package utils;

import entities.Bookings;
import entities.Car;
import entities.Role;
import entities.User;
import entities.WashingAssistants;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class StartDataSet {

    public static User user,admin,both;
    public static Role userRole,adminRole;
    public static Car c1,c2,c3;
    public static Bookings b1,b2,b3;
    public static WashingAssistants w1,w2,w3;

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
            em.createNamedQuery("Bookings.deleteAllRows").executeUpdate();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("WashingAssistants.deleteAllRows").executeUpdate();

            user = new User("user", "testUser");
            admin = new User("admin", "testAdmin");
            both = new User("user_admin", "testBoth");
          
            c1 = new Car("reg1", "audi", "a4", 2003);
            c2 = new Car("reg2", "bmw", "m1", 2007);
            c3 = new Car("reg3", "vw", "polo", 2013);
            
            b1 = new Bookings(new Date(),10);
            b2 = new Bookings(new Date(),12);
            b3 = new Bookings(new Date(),13);
            
            w1 = new WashingAssistants("name1", "danish", 0.5f, 10.0f);
            w2 = new WashingAssistants("name2", "english", 1.5f, 20.0f);
            w3 = new WashingAssistants("name3", "norwegian", 2.5f, 30.0f);
            
            b1.addWashingAssistant(w1);
            b2.addWashingAssistant(w2);
            b3.addWashingAssistant(w1);
            b3.addWashingAssistant(w3);
            
            c1.addBooking(b1);
            c2.addBooking(b2);
            c3.addBooking(b3);

            userRole = new Role("user");
            adminRole = new Role("admin");

            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            
            user.addCar(c1);
            both.addCar(c2);
            both.addCar(c3);
            
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            
            em.persist(w1);
            em.persist(w2);
            em.persist(w3);
            
            em.persist(b1);
            em.persist(b2);
            em.persist(b3);
            
            em.persist(userRole);
            em.persist(adminRole);

            em.persist(user);
            em.persist(admin);
            em.persist(both);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}

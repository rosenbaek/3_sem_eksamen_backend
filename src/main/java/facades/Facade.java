package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Bookings;
import entities.Car;
import entities.User;
import entities.WashingAssistants;
import errorhandling.API_Exception;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;


public class Facade {
    private static EntityManagerFactory emf;
    private static Facade instance;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Facade() {
    }

    public static Facade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Facade();
        }
        return instance;
    }

    public List<WashingAssistants> getWashingAssistants(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<WashingAssistants> query = em.createQuery ("select w from WashingAssistants w",entities.WashingAssistants.class);
            List<WashingAssistants> washingAssistants = query.getResultList();
            return washingAssistants;
        } finally {
            em.close();
        }
    }

    public User getUserData(String username){
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, username);
        } finally {
            em.close();
        }
    }

    public Car addBooking(String carRegistration, Bookings booking) throws API_Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Car updatedCar = em.find(Car.class, carRegistration);
            for (WashingAssistants assistant : booking.getWashingAssistantsList()) {
                if (assistant.getId() != null) {
                    assistant = em.find(WashingAssistants.class, assistant.getId());
                } else {
                    throw new API_Exception("");
                }
            }
            updatedCar.addBooking(booking);
            em.persist(booking);
            updatedCar = em.merge(updatedCar);
            em.getTransaction().commit();
            return updatedCar;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            em.close();
        }
        return null;
    }

    public Car editBooking(String carRegistration, Bookings booking) throws API_Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Car updatedCar = em.find(Car.class, carRegistration);
            for (WashingAssistants assistant : booking.getWashingAssistantsList()) {
                if (assistant.getId() != null) {
                    assistant = em.find(WashingAssistants.class, assistant.getId());
                } else {
                    throw new API_Exception("");
                }
            }
            updatedCar.addBooking(booking);
            em.merge(booking);
            updatedCar = em.merge(updatedCar);
            em.getTransaction().commit();
            return updatedCar;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            em.close();
        }
        return null;
    }

    public WashingAssistants addWashingAssistant(WashingAssistants washingAssistant){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
                em.persist(washingAssistant);
                em.flush(); //To ensure that the washingAssistant has an id before returned
            em.getTransaction().commit();
            return washingAssistant;
        }finally {
            em.close();
        }
    }

    public List<Car> getAllCars() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> query = em.createQuery ("select c from Car c",entities.Car.class);
            List<Car> cars = query.getResultList();
            return cars;
        } finally {
            em.close();
        }
    }

    public int getUsers(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return users.size();
        } finally {
            em.close();
        }
    }
}

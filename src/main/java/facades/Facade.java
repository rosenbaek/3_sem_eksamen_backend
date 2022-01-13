package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.User;
import entities.WashingAssistants;

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

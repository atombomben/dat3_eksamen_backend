package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class ExamFacade {
    private static EntityManagerFactory emf;
    private static ExamFacade instance;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private ExamFacade() {
    }

    public static ExamFacade getExamFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ExamFacade();
        }
        return instance;
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

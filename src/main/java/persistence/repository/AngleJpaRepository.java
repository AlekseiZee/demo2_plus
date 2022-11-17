package persistence.repository;

import com.example.demo2.PersistenceManager;

import jakarta.persistence.EntityManager;
import persistence.entity.Angle;

public class AngleJpaRepository {

	public static Angle find(Long id) {
		EntityManager em = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			return em.find(Angle.class, id);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
//		EntityTransaction transaction = em.getTransaction();
//		transaction.begin();
//		
//		transaction.commit(); // em.flush()
//		em.close();
	}
}

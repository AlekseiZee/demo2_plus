package persistence.repository;

import com.example.demo2.PersistenceManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import persistence.entity.Angle;
import persistence.entity.Anglepair;

public class AngleJpaRepository {

	public static Angle read(Long id) {
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
	
	public static Angle create() {
		EntityManager em = null;
		EntityTransaction transaction = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			
			Anglepair anglepair = em.find(Anglepair.class, 12L);
			Angle angle = new Angle();
			angle.setAnglepair(anglepair);
			angle.setHangle(1.001);
			angle.setVangle(2.002);
			
			transaction = em.getTransaction();
			transaction.begin();
			em.persist(angle);
			em.flush();
			transaction.commit();
			return angle;
		} catch (Exception e) {
			try{
				if (transaction!=null) {
					transaction.rollback();
				}
			} catch (Exception e1) {
				e1.printStackTrace(System.out);
			}
			e.printStackTrace(System.out);
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}		
	}
	
	public static void deleteByEntity(Angle angle) {
		EntityManager em = null;
		EntityTransaction transaction = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			System.out.println("deleteByEntity() > angle entity is managed before merging - " + em.contains(angle));
			angle = em.merge(angle);
			System.out.println("deleteByEntity() > angle entity is managed after merging - " + em.contains(angle));
			transaction = em.getTransaction();
			transaction.begin();
			em.remove(angle);
			em.flush();
			transaction.commit();

		} catch (Exception e) {
			try{
				if (transaction!=null) {
					transaction.rollback();
				}
			} catch (Exception e1) {
				e1.printStackTrace(System.out);
			}
			e.printStackTrace(System.out);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public static void deleteByIdWithJPQL(Long id) {
		EntityManager em = null;
		EntityTransaction transaction = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			transaction = em.getTransaction();
			transaction.begin();
			em.createQuery("DELETE FROM Angle a WHERE a.id = :id")
				.setParameter("id", id).executeUpdate();
			transaction.commit();

		} catch (Exception e) {
			try{
				if (transaction!=null) {
					transaction.rollback();
				}
			} catch (Exception e1) {
				e1.printStackTrace(System.out);
			}
			e.printStackTrace(System.out);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public static void deleteByIdViaPErsistenceContext(Long id) {
		EntityManager em = null;
		EntityTransaction transaction = null;
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			Angle angle = em.find(Angle.class, id);
			transaction = em.getTransaction();
			transaction.begin();
			em.remove(angle);
			em.flush();
			transaction.commit();

		} catch (Exception e) {
			try{
				if (transaction!=null) {
					transaction.rollback();
				}
			} catch (Exception e1) {
				e1.printStackTrace(System.out);
			}
			e.printStackTrace(System.out);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
}

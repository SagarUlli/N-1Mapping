package dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.ObjectA;
import model.ObjectB;

public class MainClass {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dev");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		ObjectB b = new ObjectB();
		b.setName("ulli sagar");
		b.setAge(23);

		ObjectA a1 = new ObjectA();
		a1.setAcc_type("private");
		a1.setFollowers(500);
		a1.setName("sagar_ulli");
		a1.setB(b);

		ObjectA a2 = new ObjectA();
		a2.setAcc_type("private");
		a2.setName("_sagar.ulli_");
		a2.setFollowers(300);
		a2.setB(b);

//		------------------ Persist ------------------

		entityTransaction.begin();
		entityManager.persist(b);
		entityTransaction.commit();

//		------------------ Fetch ------------------

		ObjectA fetchA = entityManager.find(ObjectA.class, "_sagar.ulli_");
		System.out.println(fetchA);

		ObjectB fetchB = entityManager.find(ObjectB.class, "ulli sagar");
		System.out.println(fetchB);

//		------------------ Update ------------------

		ObjectA updateA = entityManager.find(ObjectA.class, "_sagar.ulli_");
		updateA.setAcc_type("public");
		entityTransaction.begin();
		entityManager.merge(updateA);
		entityTransaction.commit();

		ObjectB updateB = entityManager.find(ObjectB.class, "ulli sagar");
		updateB.setName("suprith");
		entityTransaction.begin();
		entityManager.merge(updateB);
		entityTransaction.commit();

//		------------------ Delete ------------------

		ObjectA deleteA = entityManager.find(ObjectA.class, "_sagar.ulli_");
		entityTransaction.begin();
		entityManager.merge(deleteA);
		entityTransaction.commit();

//		-------------- Remove Parent ------------------
// 						Hibernate Query Language

		List<?> list = entityManager.createQuery("select acc from ObjectA a").getResultList();
		Iterator<?> itr = list.iterator();
		while (itr.hasNext()) {
//							Break Relationship
			ObjectA a = (ObjectA) itr.next();
			if (a.getB().getName().equals("ulli sagar")) {
				a.setB(null);
				entityTransaction.begin();
				entityManager.merge(a);
				entityTransaction.commit();
			}
		}
// 							Remove ObjectB
		ObjectB deleteB = entityManager.find(ObjectB.class, "ulli sagar");
		entityTransaction.begin();
		entityManager.remove(deleteB);
		entityTransaction.commit();
	}
}

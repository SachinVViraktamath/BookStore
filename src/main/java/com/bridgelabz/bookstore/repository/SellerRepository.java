package com.bridgelabz.bookstore.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.dto.PasswordUpdate;
import com.bridgelabz.bookstore.entity.SellerEntity;

@Repository
public class SellerRepository {
	
	@PersistenceContext
    private EntityManager entityManger;

    /* Query for save the data into sellerTable */

    public SellerEntity save(SellerEntity seller) {
        Session session = entityManger.unwrap(Session.class);
        session.saveOrUpdate(seller);
        System.out.println("*****");
        return seller;
    }
    //* Query to get the seller information bby email */

	public SellerEntity getseller(String email) {
		System.out.println("$$$$$$$");
		Session session = entityManger.unwrap(Session.class);
		Query q = session.createQuery("FROM SellerEntity where email=:email");
		q.setParameter("email", email);
System.out.println("#####");
		return  (SellerEntity) q.uniqueResult();
	}

	public boolean verify(Long id) {
		Session session = entityManger.unwrap(Session.class);
		Query<SellerEntity> q = session.createQuery("update SellerEntity set is_verified =:p" + " " + " " + " where sellerId=:i");
		q.setParameter("p", true);
		q.setParameter("i", id);
		int status = q.executeUpdate();
		if (status > 0) {
			return true;

		} else {
			return false;
		}
	}
		public boolean update(PasswordUpdate update, Long id) {
			Session session = entityManger.unwrap(Session.class);
			Query<SellerEntity> q = session.createQuery("update SellerEntity set password=:p" + " " + " where id=:id");
			q.setParameter(" p", update.getConfirmPassword());
			q.setParameter("id", id);
			int status = q.executeUpdate();
			if (status > 0) {
				return true;
			} else {
				return false;
			}
	}
	public void delete(SellerEntity seller) {
		 Session session = entityManger.unwrap(Session.class);
		 session.delete(seller);
	}
	
	public List<SellerEntity> getSellers() {
		Session session = entityManger.unwrap(Session.class);
		List<SellerEntity> userList = session.createQuery("FROM SellerEntity").getResultList();
		return userList;
	}
}

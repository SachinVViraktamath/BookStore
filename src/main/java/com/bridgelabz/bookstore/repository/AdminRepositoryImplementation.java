package com.bridgelabz.bookstore.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.dto.UpdateAdminPassword;
import com.bridgelabz.bookstore.entity.AdminEntity;


@Repository
public class AdminRepositoryImplementation implements AdminRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public AdminEntity save(AdminEntity userInfromation) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(userInfromation);
		return userInfromation;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Optional<AdminEntity> getAdmin(String email) {
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("FROM AdminEntity where email =:email").setParameter("email", email).uniqueResultOptional();

	}
	
	
	
	@Override
	public boolean verify(Long id) {
		Session session = entityManager.unwrap(Session.class);

		@SuppressWarnings("unchecked")
		TypedQuery<AdminEntity> q = session
				.createQuery("update AdminEntity set is_verified =:p where user_id=:i").setParameter("p", true)
				.setParameter("i", id);
		int status = q.executeUpdate();
		if (status > 0) {
			return true;
		} else {
			return false;
		}

	}


	@SuppressWarnings("unchecked")
	@Override
	public Optional<AdminEntity> getAdminById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("FROM AdminEntity where id=:id").setParameter("id", id).uniqueResultOptional();

	}


	@Override
	public boolean upDateAdminPassword(UpdateAdminPassword information, Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("update AdminEntity set password =:p" + " " + " " + "where id=:i")
				.setParameter("p", information.getConfirmPassword()).setParameter("i", id);
		int status = q.executeUpdate();
		if (status > 0) {
			return true;
		} else {
			return false;
		}
	
	}
	
}


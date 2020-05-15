package com.bridgelabz.bookstore.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.entity.BookProfile;

@Repository
public class BookImageRespository {
	@PersistenceContext
	private EntityManager entityManager;

	public BookProfile addProfile(BookProfile image) {
		Session session = entityManager.unwrap(Session.class);
		session.save(image);
		return image;
	}

	/* Query for find the profilePic for particular user */
	public BookProfile findUserById(Long userId) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("from BookProfile where id=:id");
		BookProfile user = (BookProfile) q.getSingleResult();
		return user;
	}

	/* Query for delete the profilepic */
	public BookProfile delete(BookProfile profile) {
		Session session = entityManager.unwrap(Session.class);
		session.delete(profile);
		return null;
}
}

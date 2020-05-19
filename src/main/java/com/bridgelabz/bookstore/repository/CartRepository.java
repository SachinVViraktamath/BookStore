package com.bridgelabz.bookstore.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.entity.CartDetails;
import com.bridgelabz.bookstore.entity.Seller;


@Repository
public class CartRepository {
	@PersistenceContext
	private EntityManager manager;
	
@SuppressWarnings("unchecked")
public  Optional<CartDetails> findbyuserId(Long cartid,Long userId) {
		Session session = manager.unwrap(Session.class);
		return session.createQuery("FROM users_books_cart where users_user_id=:id and books_cart_cart_id=:cart_id").setParameter("id", userId)
				.setParameter("cart_id", cartid)
				.uniqueResultOptional();

	}
	
}


package com.bridgelabz.bookstore.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "BookQuantity")
@AllArgsConstructor
@NoArgsConstructor
public class BookQuantity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long quantityId;
	private int bookQty;

}

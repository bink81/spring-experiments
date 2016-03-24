package com.marzeta.ordering.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String number;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_ID")
	private Collection<OrderItemEntity> items;

	public Collection<OrderItemEntity> getItems() {
		return items;
	}

	public void setItems(Collection<OrderItemEntity> items) {
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}

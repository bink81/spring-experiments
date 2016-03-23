package com.marzeta.ordering.model;

import java.util.Collection;
import java.util.LinkedHashSet;

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
public class ExampleOrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String number;

	private String customer;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_ID")
	private Collection<ExampleItemEntity> items = new LinkedHashSet<ExampleItemEntity>();

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Collection<ExampleItemEntity> getItems() {
		return items;
	}

	public void setItems(Collection<ExampleItemEntity> items) {
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

package com.marzeta.ordering.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.marzeta.ordering.model.OrderItemEntity;
import com.marzeta.ordering.model.OrderEntity;

@ContextConfiguration(locations = "/META-INF/spring/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest {

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	@Transactional
	public void testSaveOrderWithItems() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		OrderEntity order = new OrderEntity();
		order.getItems().add(new OrderItemEntity());

		session.save(order);
		session.flush();

		assertNotNull(order.getId());
	}

	@Test
	@Transactional
	public void testSaveAndGet() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		OrderEntity order = new OrderEntity();
		order.setNumber("1");
		OrderItemEntity item = new OrderItemEntity();
		item.setName("foo");
		order.getItems().add(item);

		session.save(order);
		session.flush();
		session.clear();
		OrderEntity other = (OrderEntity) session.get(OrderEntity.class, order.getId());

		assertEquals(1, other.getItems().size());
		OrderEntity actual = other.getItems().iterator().next().getOrder();
		assertEquals(order.getNumber(), actual.getNumber());
	}

	@Test
	@Transactional
	public void testSaveAndFind() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		OrderEntity order = new OrderEntity();
		order.setNumber("1");
		OrderItemEntity item = new OrderItemEntity();
		item.setName("foo");
		order.getItems().add(item);

		session.save(order);
		session.flush();
		session.clear();
		OrderEntity other = (OrderEntity) session
				.createQuery("select o from OrderEntity o"
						+ " join o.items i"
						+ " where i.name=:name")
				.setString("name", "foo")
				.uniqueResult();

		assertEquals(1, other.getItems().size());
		OrderEntity actual = other.getItems().iterator().next().getOrder();
		assertEquals(order.getNumber(), actual.getNumber());
	}
}

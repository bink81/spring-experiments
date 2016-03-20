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

import com.marzeta.ordering.model.ExampleItem;
import com.marzeta.ordering.model.ExampleOrder;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleOrderServiceTest {

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	@Transactional
	public void testSaveOrderWithItems() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		ExampleOrder order = new ExampleOrder();
		order.getItems().add(new ExampleItem());

		session.save(order);
		session.flush();

		assertNotNull(order.getId());
	}

	@Test
	@Transactional
	public void testSaveAndGet() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		ExampleOrder order = new ExampleOrder();
		ExampleItem item = new ExampleItem();
		item.setProduct("foo");
		order.getItems().add(item);

		session.save(order);
		session.flush();
		// Otherwise the query returns the existing order and we didn't set the
		// parent in the item...
		session.clear();
		ExampleOrder other = (ExampleOrder) session.get(ExampleOrder.class, order.getId());

		assertEquals(1, other.getItems().size());
		assertEquals(order, other.getItems().iterator().next().getOrder());
	}

	@Test
	@Transactional
	public void testSaveAndFind() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		ExampleOrder order = new ExampleOrder();
		ExampleItem item = new ExampleItem();
		item.setProduct("foo");
		order.getItems().add(item);

		session.save(order);
		session.flush();
		// Otherwise the query returns the existing order and we didn't set the
		// parent in the item...
		session.clear();
		ExampleOrder other = (ExampleOrder) session
				.createQuery("select o from ExampleOrder o"
						+ " join o.items i"
						+ " where i.product=:product")
				.setString("product", "foo")
				.uniqueResult();

		assertEquals(1, other.getItems().size());
		assertEquals(order, other.getItems().iterator().next().getOrder());
	}
}

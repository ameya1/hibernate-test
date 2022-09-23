package com.dao;

import com.model.Customers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Customers> get() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Customers> criteria = builder.createQuery(Customers.class);

        // Specify criteria root
        criteria.from(Customers.class);

        // Execute query
        return session.createQuery(criteria).getResultList();
    }

}

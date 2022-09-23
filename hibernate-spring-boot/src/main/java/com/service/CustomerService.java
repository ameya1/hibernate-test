package com.service;

import com.dao.CustomerDao;
import com.model.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public List<Customers> get() {
        return customerDao.get();
    }
}

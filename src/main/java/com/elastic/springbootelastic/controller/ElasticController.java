package com.elastic.springbootelastic.controller;

import com.elastic.springbootelastic.entity.Customer;
import com.elastic.springbootelastic.repository.CustomerRepo;
import com.elastic.springbootelastic.service.QueryDSLService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ElasticController {
    static final Logger LOG = LogManager.getLogger(ElasticController.class.getName());

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private QueryDSLService queryservice;

    @PostMapping("/saveData")
    public String saveCustomer(@RequestBody Customer customers)
    {
        Customer cs  = customerRepo.save(customers);
        LOG.info("customer save sucessfully");
        if(cs.getFname().equals("")){LOG.error("Customer not saved");}
        return "Customer Added Successfully!";
    }
    @PostMapping("/saveAll")
    public String saveAllCustomer(@RequestBody List<Customer> customers)
    {
        System.out.println("dekjlo");
        for (Customer l : customers) {
            System.out.println("dekho:"+l.getFname());
        }
        Iterable<Customer> cs = customers;
        for (Customer li : cs) {
        System.out.println("dekho iterable:"+li.getFname());
    }
         customerRepo.saveAll(cs);
        LOG.info("customers save sucessfully");
        return "All Customers Added Successfully!";
    }

    @GetMapping("/findAll")
    public Iterable<Customer> getCustomers()
    {
        LOG.info("get all customers");
        return customerRepo.findAll();
    }

    @GetMapping("/findByFname/{fname}")
    public List<Customer> findByFname(@PathVariable String fname)
    {
        LOG.info("get customer by name");
        return customerRepo.findByFname(fname);
    }
    @GetMapping("/findByState/{state}/{city}")
    public List<Customer> findBystate(@PathVariable String state,@PathVariable String city)
    {
        LOG.info("get  customers by state and city");
        return customerRepo.findByState(state,city);
    }

    @GetMapping("/findbyany/{fname}/{lname}")
    public SearchHits<Customer> findall(@PathVariable String fname, @PathVariable String lname)
    {
        LOG.info("get all customers by firstname and lastname");
        return (SearchHits<Customer>) queryservice.getCustomers(fname,lname);
    }
    @GetMapping("/{anything}")
    public ResponseEntity<?> find(@PathVariable String anything)
    {
        LOG.info("get all customers by anything search");
        return ResponseEntity.ok(queryservice.getDetails(anything));
    }
    @GetMapping("/findExact/{exact}")
    public ResponseEntity<?> findExact(@PathVariable String exact)
    {
        LOG.info("get exact customer's details");
        return ResponseEntity.ok(queryservice.getDetailswithTerm(exact));
    }
}

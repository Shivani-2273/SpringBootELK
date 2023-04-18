package com.elastic.springbootelastic.repository;

import com.elastic.springbootelastic.entity.Customer;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends ElasticsearchRepository<Customer, Integer> {

    List<Customer> findByFname(String fname);
    @Query("{\"nested\":{\"path\":\"address\",\"query\":[{\"match\":{\"address.state\":\"?0\"}},{\"match\":{\"address.city\":\"?1\"}}]}}")
    List<Customer> findByState(String state,String c);
}

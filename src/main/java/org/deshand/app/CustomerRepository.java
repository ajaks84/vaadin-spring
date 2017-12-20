package org.deshand.app;


import java.util.List;

import org.deshand.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, Long> {

	List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);

	Customer findById(String id);
}

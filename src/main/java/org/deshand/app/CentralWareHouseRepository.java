package org.deshand.app;

import java.util.Collection;

import org.deshand.model.CentralWareHouse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CentralWareHouseRepository extends MongoRepository<CentralWareHouse, Long>{

	CentralWareHouse findById(String id);

	Collection<CentralWareHouse> findByPartNumberStartsWithIgnoreCase(String filterText);

	

}



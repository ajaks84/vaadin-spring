package org.deshand.app.repo;

import java.util.Collection;

import org.deshand.app.model.CentralWareHouse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CentralWareHouseRepository extends MongoRepository<CentralWareHouse, Long>{

	CentralWareHouse findById(String id);

	Collection<CentralWareHouse> findBypartDescriptionLikeIgnoreCase(String filterText);

	Collection<CentralWareHouse> findByshelfNameLikeIgnoreCase(String filterText);

	Collection<CentralWareHouse> findBypartNumberStartsWithIgnoreCase(String filterText);
	
//    Page<CentralWareHouse> findAll( String data,Pageable pageable); 


	

}



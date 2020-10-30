package com.vertigo.repositories;

import com.vertigo.models.ErsUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErsUserRepository extends CrudRepository<ErsUser, Integer> {

    public ErsUser findByUsername(String username);

}

package com.vertigo.repositories;

import com.vertigo.models.ErsReimbursement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErsReimbursementRepository extends CrudRepository<ErsReimbursement, Integer> {

}

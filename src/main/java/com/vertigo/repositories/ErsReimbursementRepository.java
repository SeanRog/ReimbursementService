package com.vertigo.repositories;

import com.vertigo.models.ErsReimbursement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErsReimbursementRepository extends CrudRepository<ErsReimbursement, Integer> {

    List<ErsReimbursement> findAllByAuthorId(int id);

}

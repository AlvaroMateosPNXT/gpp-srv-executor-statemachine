package com.pagonxt.gpp.executor.repository;

import com.pagonxt.gpp.executor.repository.model.Execution;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionRepository extends CrudRepository<Execution, UUID> {

  Optional<Execution> findByGlobalExecutionId(UUID globalExecutionId);

}

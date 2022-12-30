package com.pagonxt.gpp.executor.repository;


import com.pagonxt.gpp.executor.repository.model.State;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CrudRepository<State, Long> {

  Optional<State> findByStateName(String stateName);

}

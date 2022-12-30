package com.pagonxt.gpp.executor.repository;

import com.pagonxt.gpp.executor.repository.model.StateMachine;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateMachineRepository extends CrudRepository<StateMachine, UUID> {

  StateMachine findByStateMachineNameAndInitialTransitionIsTrue(String stateMachineName);

  Optional<StateMachine> findByStateMachineNameAndCurrentTransitionTransitionName(
      String stateMachine, String transition);
}

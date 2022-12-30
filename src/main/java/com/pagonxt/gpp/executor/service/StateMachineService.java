package com.pagonxt.gpp.executor.service;

import com.pagonxt.gpp.executor.repository.model.StateMachine;
import java.util.Optional;

public interface StateMachineService {

  StateMachine findByDirectiveAndInitialTransaction (String directive);

  Optional<StateMachine> findByDirectiveAndCurrentTransitionName (String directive, String transitionName);

}

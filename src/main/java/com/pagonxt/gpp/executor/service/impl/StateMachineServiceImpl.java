package com.pagonxt.gpp.executor.service.impl;

import com.pagonxt.gpp.executor.repository.StateMachineRepository;
import com.pagonxt.gpp.executor.repository.model.StateMachine;
import com.pagonxt.gpp.executor.service.StateMachineService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateMachineServiceImpl implements StateMachineService {

  @Autowired
  StateMachineRepository stateMachineRepository;

  @Override
  public StateMachine findByDirectiveAndInitialTransaction(String directive) {
    return stateMachineRepository.findByStateMachineNameAndInitialTransitionIsTrue(directive);
  }

  @Override
  public Optional<StateMachine> findByDirectiveAndCurrentTransitionName(String directive,
      String transitionName) {
    return stateMachineRepository.
        findByStateMachineNameAndCurrentTransitionTransitionName(directive, transitionName);
  }
}

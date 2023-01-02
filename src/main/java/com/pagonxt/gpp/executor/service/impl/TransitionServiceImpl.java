package com.pagonxt.gpp.executor.service.impl;

import com.pagonxt.gpp.executor.exception.TransitionNotFoundException;
import com.pagonxt.gpp.executor.repository.model.Activity;
import com.pagonxt.gpp.executor.repository.model.Execution;
import com.pagonxt.gpp.executor.repository.model.StateMachine;
import com.pagonxt.gpp.executor.service.ActivityService;
import com.pagonxt.gpp.executor.service.ExecutionService;
import com.pagonxt.gpp.executor.service.StateMachineService;
import com.pagonxt.gpp.executor.service.TransitionService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransitionServiceImpl implements TransitionService {

  @Autowired
  ExecutionService executionService;

  @Autowired
  StateMachineService stateMachineService;

  @Autowired
  ActivityService activityService;

  @Override
  public void processTransition(String globalExecutionId, String transitionName, Execution exec) {
    boolean isValidTransition = exec.getLastActivity().getStateMachine().getNextTransitions()
        .stream().anyMatch(t -> t.equals(transitionName));
    if (!isValidTransition) {
      Activity activity = new Activity(UUID.fromString(globalExecutionId), null);
      activity.setActivityLog(String.format("%s to %s transition is not allowed",
          exec.getLastActivity().getStateMachine().getCurrentTransition().getTransitionName(),
          transitionName));
      activity.setExecute(false);

      activityService.saveActivityAndExecution(exec, activity);

      throw new TransitionNotFoundException(String.format("Transition %s not allowed",
          transitionName));
    }

    Optional<StateMachine> nextStateMachine = stateMachineService.findByDirectiveAndCurrentTransitionName(
        exec.getStateMachineName(), transitionName);
    nextStateMachine.ifPresent(stateMachine -> {

      Activity activity = new Activity(UUID.fromString(globalExecutionId), stateMachine);
      activity.setActivityLog(String.format("%s to %s transition allowed",
          exec.getLastActivity().getStateMachine().getCurrentTransition().getTransitionName(),
          transitionName));
      activity.setExecute(true);

      activityService.saveActivityAndExecution(exec, activity);
      executionService.saveExecution(exec);

    });
  }

}

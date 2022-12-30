package com.pagonxt.gpp.executor.adapter.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;

import com.pagonxt.gpp.executor.adapter.controller.request.ExecutionRequest;
import com.pagonxt.gpp.executor.exception.ExecutionNotFoundException;
import com.pagonxt.gpp.executor.exception.IllegalStateMachineException;
import com.pagonxt.gpp.executor.exception.TransitionNotFoundException;
import com.pagonxt.gpp.executor.repository.model.Activity;
import com.pagonxt.gpp.executor.repository.model.Execution;
import com.pagonxt.gpp.executor.repository.model.ExecutionActivity;
import com.pagonxt.gpp.executor.repository.model.ExecutionActivityKey;
import com.pagonxt.gpp.executor.repository.model.StateMachine;
import com.pagonxt.gpp.executor.service.ExecutionService;
import com.pagonxt.gpp.executor.service.StateMachineService;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutionController {

  @Autowired
  StateMachineService stateMachineService;

  @Autowired
  ExecutionService executionService;

  @PostMapping("/executes")
  public ResponseEntity<String> manageExecution(@RequestBody ExecutionRequest executionRequest) {

    StateMachine stateMachineInitial = stateMachineService.findByDirectiveAndInitialTransaction(
        executionRequest.directive());

    Optional.ofNullable(stateMachineInitial).ifPresentOrElse(stateMachine ->
        executionService.processExecution(executionRequest, stateMachine),
        () -> {
      throw new IllegalStateMachineException("The directive does not exists");
    });

    return new ResponseEntity<>(String.format("globalExecutionId %s!", executionRequest.globalExecutionId()),
        ACCEPTED);

  }

  @PutMapping("/executes/{globalExecutionId}/transitions/{transitionName}")
  public ResponseEntity<String> manageTransition(@PathVariable String globalExecutionId, @PathVariable String transitionName) {

    Optional<Execution> execution = executionService.findExecutionByGlobalExecutionId(UUID.fromString(globalExecutionId));

    execution.ifPresentOrElse(exec -> {

      boolean isValidTransition = exec.getLastActivity().getStateMachine().getNextTransitions()
          .stream().anyMatch(t -> t.equals(transitionName));
      if (!isValidTransition) {
        Activity activity = new Activity(UUID.fromString(globalExecutionId), null);
        activity.setActivityLog(String.format("%s to %s transition is not allowed",
            exec.getLastActivity().getStateMachine().getCurrentTransition().getTransitionName(), transitionName));
        activity.setExecute(false);
        Activity savedActivity = executionService.saveActivity(activity);
        executionService.saveExecutionActivity(
            new ExecutionActivity(new ExecutionActivityKey(savedActivity.getId(),exec.getId())));
        throw new TransitionNotFoundException(String.format("Transition %s not allowed", transitionName));
      }

      Optional<StateMachine> nextStateMachine = stateMachineService.findByDirectiveAndCurrentTransitionName(
          exec.getStateMachineName(), transitionName);
      nextStateMachine.ifPresent(stateMachine -> {
        Activity activity = new Activity(UUID.fromString(globalExecutionId), stateMachine);
        activity.setActivityLog(String.format("%s to %s transition allowed",
            exec.getLastActivity().getStateMachine().getCurrentTransition().getTransitionName(), transitionName));
        activity.setExecute(true);
        Activity savedActivity = executionService.saveActivity(activity);
        executionService.saveExecutionActivity(
            new ExecutionActivity(new ExecutionActivityKey(savedActivity.getId(),exec.getId())));
        exec.setLastActivity(savedActivity);
        executionService.saveExecution(exec);

      });


    }, () -> {throw new ExecutionNotFoundException(String.format("GlobalExecutionId %s does not exists", globalExecutionId));});

    return new ResponseEntity<>(String.format("globalExecutionId %s!", globalExecutionId),
        ACCEPTED);

  }

}

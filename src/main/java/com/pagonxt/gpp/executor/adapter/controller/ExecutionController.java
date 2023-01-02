package com.pagonxt.gpp.executor.adapter.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

import com.pagonxt.gpp.executor.adapter.controller.request.ExecutionRequest;
import com.pagonxt.gpp.executor.adapter.controller.response.ExecutionStatusResponse;
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
import com.pagonxt.gpp.executor.service.TransitionService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @Autowired
  TransitionService transitionService;

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

    execution.ifPresentOrElse(exec ->
            transitionService.processTransition(globalExecutionId, transitionName, exec),
        () -> {throw new ExecutionNotFoundException(String.format("GlobalExecutionId %s does not exists", globalExecutionId));});

    return new ResponseEntity<>(String.format("globalExecutionId %s!", globalExecutionId),
        ACCEPTED);

  }

  @GetMapping("/executes/{globalExecutionId}")
  public ResponseEntity<Execution> getExecution(@PathVariable String globalExecutionId) {

    Optional<Execution> execution = executionService.findExecutionByGlobalExecutionId(UUID.fromString(globalExecutionId));
    execution.orElseThrow(() -> {
      throw new ExecutionNotFoundException(String.format("GlobalExecutionId %s does not exists", globalExecutionId));
    });
    return new ResponseEntity<>(execution.get(), OK);
  }

  @GetMapping("/executes/{globalExecutionId}/status")
  public ResponseEntity<ExecutionStatusResponse> getStatus(@PathVariable String globalExecutionId) {

    Optional<Execution> execution = executionService.findExecutionByGlobalExecutionId(UUID.fromString(globalExecutionId));


    Activity activity = execution.map(Execution::getLastActivity).orElseThrow(
        () -> {
          throw new ExecutionNotFoundException(
              String.format("GlobalExecutionId %s does not exists", globalExecutionId));
        });
    String log = activity.getActivityLog();
    Date creation = activity.getCreationDate();

    Optional<StateMachine> stateMachine = Optional.of(activity).map(Activity::getStateMachine);
    Set<String> nextTransitions = stateMachine.map(StateMachine::getNextTransitions).orElse(null);
    String status = stateMachine.map(
            stateMachine1 -> stateMachine1.getCurrentTransition().getToState().getStateName())
        .orElse(null);
    ExecutionStatusResponse response = new ExecutionStatusResponse(status, creation, log, nextTransitions);
    return new ResponseEntity<>(response, OK);
  }

}

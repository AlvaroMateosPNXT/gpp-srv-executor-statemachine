package com.pagonxt.gpp.executor.service.impl;

import com.pagonxt.gpp.executor.adapter.controller.request.ExecutionRequest;
import com.pagonxt.gpp.executor.repository.ActivityRepository;
import com.pagonxt.gpp.executor.repository.ExecutionActivityRepository;
import com.pagonxt.gpp.executor.repository.ExecutionRepository;
import com.pagonxt.gpp.executor.repository.model.Activity;
import com.pagonxt.gpp.executor.repository.model.Execution;
import com.pagonxt.gpp.executor.repository.model.ExecutionActivity;
import com.pagonxt.gpp.executor.repository.model.ExecutionActivityKey;
import com.pagonxt.gpp.executor.repository.model.StateMachine;
import com.pagonxt.gpp.executor.service.ExecutionService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionServiceImpl implements ExecutionService {

  @Autowired
  ActivityRepository activityRepository;

  @Autowired
  ExecutionActivityRepository executionActivityRepository;

  @Autowired
  ExecutionRepository executionRepository;

  @Override
  public Activity saveActivity(Activity activity) {
    return activityRepository.save(activity);
  }

  @Override
  public Execution saveExecution(Execution execution) {
    return executionRepository.save(execution);
  }

  @Override
  public ExecutionActivity saveExecutionActivity(ExecutionActivity executionActivity) {
    return executionActivityRepository.save(executionActivity);
  }

  @Override
  public Optional<Execution> findExecutionByGlobalExecutionId(UUID globalExecutionId) {
    return executionRepository.findByGlobalExecutionId(globalExecutionId);
  }

  @Override
  public void processExecution(ExecutionRequest executionRequest, StateMachine stateMachine) {
    UUID globalId = UUID.fromString(executionRequest.globalExecutionId());
    Activity activity = new Activity(globalId, null);

    Optional<Execution> execution = findExecutionByGlobalExecutionId(globalId);

    execution.ifPresentOrElse(exec -> {
      activity.setActivityLog(String.format("Execution duplicate for globalExecutionId: %s",
          globalId));
      activity.setExecute(false);
      Activity savedActivity = saveActivity(activity);
      saveExecutionActivity(
          new ExecutionActivity(new ExecutionActivityKey(savedActivity.getId(),exec.getId())));
    }, () -> {

      activity.setActivityLog(String.format("Transition executed: %s",
          stateMachine.getCurrentTransition().getTransitionName()));
      activity.setExecute(true);
      activity.setStateMachine(stateMachine);
      Activity savedActivity = saveActivity(activity);

      Execution savedExecution = saveExecution(
          new Execution(
              globalId,
              stateMachine.getStateMachineName(),
              savedActivity));
      saveExecutionActivity(
          new ExecutionActivity(new ExecutionActivityKey(savedActivity.getId(),savedExecution.getId())));
    });
  }

}

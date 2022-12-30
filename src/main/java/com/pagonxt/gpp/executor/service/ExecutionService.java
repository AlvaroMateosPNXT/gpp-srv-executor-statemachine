package com.pagonxt.gpp.executor.service;

import com.pagonxt.gpp.executor.adapter.controller.request.ExecutionRequest;
import com.pagonxt.gpp.executor.repository.model.Activity;
import com.pagonxt.gpp.executor.repository.model.Execution;
import com.pagonxt.gpp.executor.repository.model.ExecutionActivity;
import com.pagonxt.gpp.executor.repository.model.StateMachine;
import java.util.Optional;
import java.util.UUID;

public interface ExecutionService {

  Activity saveActivity (Activity activity);

  Execution saveExecution (Execution execution);

  ExecutionActivity saveExecutionActivity (ExecutionActivity executionActivity);

  Optional<Execution> findExecutionByGlobalExecutionId (UUID globalExecutionId);

  void processExecution(ExecutionRequest executionRequest, StateMachine stateMachine);


}

package com.pagonxt.gpp.executor.service;

import com.pagonxt.gpp.executor.repository.model.Activity;
import com.pagonxt.gpp.executor.repository.model.Execution;
import com.pagonxt.gpp.executor.repository.model.ExecutionActivity;

public interface ActivityService {

  Activity saveActivity (Activity activity);

  ExecutionActivity saveExecutionActivity (ExecutionActivity executionActivity);

  void saveActivityAndExecution(Execution exec, Activity activity);

}

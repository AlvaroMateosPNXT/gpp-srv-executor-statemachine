package com.pagonxt.gpp.executor.service.impl;

import com.pagonxt.gpp.executor.repository.ActivityRepository;
import com.pagonxt.gpp.executor.repository.ExecutionActivityRepository;
import com.pagonxt.gpp.executor.repository.model.Activity;
import com.pagonxt.gpp.executor.repository.model.Execution;
import com.pagonxt.gpp.executor.repository.model.ExecutionActivity;
import com.pagonxt.gpp.executor.repository.model.ExecutionActivityKey;
import com.pagonxt.gpp.executor.service.ActivityService;
import com.pagonxt.gpp.executor.service.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

  @Autowired
  ActivityRepository activityRepository;

  @Autowired
  ExecutionActivityRepository executionActivityRepository;

  @Override
  public void saveActivityAndExecution(Execution exec, Activity activity) {
    Activity savedActivity = saveActivity(activity);
    saveExecutionActivity(
        new ExecutionActivity(new ExecutionActivityKey(savedActivity.getId(), exec.getId())));
    exec.setLastActivity(savedActivity);
  }

  @Override
  public Activity saveActivity(Activity activity) {
    return activityRepository.save(activity);
  }

  @Override
  public ExecutionActivity saveExecutionActivity(ExecutionActivity executionActivity) {
    return executionActivityRepository.save(executionActivity);
  }
}

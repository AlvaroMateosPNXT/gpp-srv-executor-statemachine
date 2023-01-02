package com.pagonxt.gpp.executor.service;

import com.pagonxt.gpp.executor.repository.model.Execution;

public interface TransitionService {

  public void processTransition(String globalExecutionId, String transitionName, Execution exec);

}

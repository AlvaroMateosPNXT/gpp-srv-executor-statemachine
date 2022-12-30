package com.pagonxt.gpp.executor.repository.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
@Embeddable
public class ExecutionActivityKey {
  @Column(name = "activity_id")
  private UUID activityId;
  @Column(name = "execution_id")
  private UUID executionId;

  public ExecutionActivityKey() {
  }

  public ExecutionActivityKey(UUID activityId, UUID executionId) {
    this.activityId = activityId;
    this.executionId = executionId;
  }

  public UUID getActivityId() {
    return activityId;
  }

  public void setActivityId(UUID activityId) {
    this.activityId = activityId;
  }

  public UUID getExecutionId() {
    return executionId;
  }

  public void setExecutionId(UUID executionId) {
    this.executionId = executionId;
  }
}

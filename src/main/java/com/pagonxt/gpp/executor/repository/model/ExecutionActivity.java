package com.pagonxt.gpp.executor.repository.model;


import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.OneToOne;
import java.util.UUID;

@Entity
public class ExecutionActivity {

  @EmbeddedId
  private ExecutionActivityKey executionActivityKey;

  public ExecutionActivity() {
  }

  public ExecutionActivity(ExecutionActivityKey executionActivityKey) {
    this.executionActivityKey = executionActivityKey;
  }

  public ExecutionActivityKey getExecutionActivityKey() {
    return executionActivityKey;
  }

  public void setExecutionActivityKey(
      ExecutionActivityKey executionActivityKey) {
    this.executionActivityKey = executionActivityKey;
  }
}

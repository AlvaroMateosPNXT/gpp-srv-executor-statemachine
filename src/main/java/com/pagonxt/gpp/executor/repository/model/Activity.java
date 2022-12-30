package com.pagonxt.gpp.executor.repository.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Activity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull
  private UUID globalExecutionId;

  private boolean execute = true;

  private String activityLog;

  @OneToOne(targetEntity = StateMachine.class, cascade = CascadeType.ALL)
  @Embedded
  private StateMachine stateMachine;

  public Activity(UUID globalExecutionId) {
    this.globalExecutionId = globalExecutionId;
  }

  public Activity(UUID globalExecutionId, StateMachine stateMachine) {
    this.globalExecutionId = globalExecutionId;
    this.stateMachine = stateMachine;
  }

  public Activity() {
  }

  public UUID getId() {
    return id;
  }

  public void setGlobalExecutionId(UUID globalExecutionId) {
    this.globalExecutionId = globalExecutionId;
  }

  public StateMachine getStateMachine() {
    return stateMachine;
  }

  public String getActivityLog() {
    return activityLog;
  }

  public void setActivityLog(String activityLog) {
    this.activityLog = activityLog;
  }

  public boolean isExecute() {
    return execute;
  }

  public void setExecute(boolean execute) {
    this.execute = execute;
  }

  public void setStateMachine(StateMachine stateMachine) {
    this.stateMachine = stateMachine;
  }
}

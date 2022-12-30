package com.pagonxt.gpp.executor.repository.model;

import static java.util.Objects.nonNull;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "globalExecutionId"))
public class Execution {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  private UUID globalExecutionId;

  private String stateMachineName;

  @OneToMany
  @Embedded
  private List<Activity> activity;

  @Embedded
  @OneToOne
  private Activity lastActivity;

  public Execution() {
  }

  public Execution(UUID globalExecutionId, String stateMachine, Activity activity) {
    this.globalExecutionId = globalExecutionId;
    this.stateMachineName = stateMachine;
    this.activity = new ArrayList<>();
    this.activity.add(activity);
    this.lastActivity = activity;
  }

  public void startStateMachine(Activity activity) {
    this.activity.add(activity);
  }

  public String getStateMachineName() {
    return stateMachineName;
  }

  public void setStateMachineName(String stateMachineName) {
    this.stateMachineName = stateMachineName;
  }

  public UUID getId() {
    return id;
  }

  public UUID getGlobalExecutionId() {
    return globalExecutionId;
  }

  public void setGlobalExecutionId(UUID globalExecutionId) {
    this.globalExecutionId = globalExecutionId;
  }

  public Activity getLastActivity() {
    return lastActivity;
  }

  public void setLastActivity(Activity lastActivity) {
    this.lastActivity = lastActivity;
  }

  public List<Activity> getActivity() {
    return activity;
  }

  public void updateTransition(Activity activity) {
    this.lastActivity = activity;
    this.activity.add(activity);
  }

  public boolean isStartMachine() {
    return nonNull(this.activity) && !this.activity.isEmpty();
  }
}

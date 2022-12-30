package com.pagonxt.gpp.executor.repository.model;


import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class State {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private boolean initialState;

  private String stateName;

  @OneToOne
  @Embedded
  private Action action;

  public void setInitialState(boolean initialState) {
    this.initialState = initialState;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public Long getId() {
    return id;
  }

  public boolean isInitialState() {
    return initialState;
  }

  public String getStateName() {
    return stateName;
  }

  public Action getAction() {
    return action;
  }
}

package com.pagonxt.gpp.executor.repository.model;

import com.pagonxt.gpp.executor.exception.IllegalOperationsStateMachineException;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class StateMachine {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private boolean initialTransition;

  private String stateMachineName;

  @ElementCollection
  private Set<String> nextTransitions;

  @OneToOne
  @Embedded
  private Transition currentTransition;

  public UUID getId() {
    return id;
  }

  public Transition getCurrentTransition() {
    return currentTransition;
  }

  public String getStateMachineName() {
    return stateMachineName;
  }

  public void initialStateMachine(Transition transition) {
    this.currentTransition = transition;
  }

  public void initialTransaction(Transition transition, String... nextTransitions) throws IllegalOperationsStateMachineException {
    if(!transition.getToState().isInitialState()) {
      throw new IllegalOperationsStateMachineException(transition.getTransitionName());
    }
      this.initialTransition = true;
      this.currentTransition = transition;
      this.nextTransitions = new HashSet<>();
      this.nextTransitions.addAll(List.of(nextTransitions));
  }

  public void setTransactions(Transition transition2, String... nextTransitions) {
      this.currentTransition = transition2;
      this.nextTransitions = new HashSet<>();
      this.nextTransitions.addAll(List.of(nextTransitions));
  }

  public void setStateMachineName(String stateMachineName) {
    this.stateMachineName = stateMachineName;
  }

  public Set<String> getNextTransitions() {
    return nextTransitions;
  }

  public void setNextTransitions(Set<String> nextTransitions) {
    this.nextTransitions = nextTransitions;
  }

  @Override
  public String toString() {
    return "StateMachine{" +
        "id=" + id +
        ", transition2=" + currentTransition +
        ", initialTransition=" + initialTransition +
        ", stateMachineName='" + stateMachineName + '\'' +
        ", nextTransitions=" + nextTransitions +
        '}';
  }
}

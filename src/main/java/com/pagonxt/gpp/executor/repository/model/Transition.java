package com.pagonxt.gpp.executor.repository.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.UUID;
import org.hibernate.annotations.Type;


@Entity
public class Transition {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
//  @Type(type = "uuid-char")
  private UUID id;

  private String transitionName;

  @OneToOne
  @Embedded
  private State toState;

  public UUID getId() {
    return id;
  }

  public String getTransitionName() {
    return transitionName;
  }

  public State getToState() {
    return toState;
  }

  public void setTransitionName(String transitionName) {
    this.transitionName = transitionName;
  }

  public void setToState(State toState) {
    this.toState = toState;
  }

  @Override
  public String toString() {
    return "Transition{" +
        "id=" + id +
        ", transitionName='" + transitionName + '\'' +
        ", toState=" + toState +
        '}';
  }
}

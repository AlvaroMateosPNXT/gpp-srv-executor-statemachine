package com.pagonxt.gpp.executor.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Action {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String beanName;

  private boolean debug;

  public Long getId() {
    return id;
  }

  public String getBeanName() {
    return beanName;
  }

  public boolean isDebug() {
    return debug;
  }

  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }
}

package com.pagonxt.gpp.executor.repository;


import com.pagonxt.gpp.executor.repository.model.Action;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends CrudRepository<Action, Long> {}

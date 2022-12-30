package com.pagonxt.gpp.executor.repository;

import com.pagonxt.gpp.executor.repository.model.Activity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, UUID> {

}

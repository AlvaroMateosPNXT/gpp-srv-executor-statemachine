package com.pagonxt.gpp.executor.adapter.controller.response;

import java.util.Date;
import java.util.List;
import java.util.Set;

public record ExecutionStatusResponse(
    String status,
    Date timestamp,
    String log,
    Set<String> expectedNextTransitions) {

}

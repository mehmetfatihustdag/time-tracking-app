package com.foodtech.timetracking.exceptions;

import org.springframework.stereotype.Service;

import java.util.Map;

public interface ExceptionDetails {
  Map<String, Object> from(Exception e, String messageReplacement);
}

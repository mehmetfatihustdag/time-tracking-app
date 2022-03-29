package com.foodtech.timetracking.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BasicExceptionDetails implements ExceptionDetails {
    @Override
    public Map<String, Object> from(Exception e, String messageReplacement) {
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("message", messageReplacement);

        return data;
    }
}
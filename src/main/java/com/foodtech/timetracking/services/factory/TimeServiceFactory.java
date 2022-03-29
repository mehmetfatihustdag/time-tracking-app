package com.foodtech.timetracking.services.factory;


import static java.util.Objects.isNull;

import com.foodtech.timetracking.data.enums.TimeTrackingEnum;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Factory class to produce different objects of the time related operations
 * For now only PUNCH handler is created, but factory class makes it possible to extend the application with the possible features like (break, and vacation)
 *
 */
@Component
@RequiredArgsConstructor
public class TimeServiceFactory {

    private final List<TimeTrackingHandler> timeTrackingHandlerList;

    private final Map<String, TimeTrackingHandler> handlerCache = new HashMap<>();

    @PostConstruct
    public void registerProviderOnStartUp() {
        timeTrackingHandlerList.forEach( provider -> handlerCache.put(provider.getType(), provider));
    }

    public TimeTrackingHandler getHandler(TimeTrackingEnum timeTrackingType) {
        TimeTrackingHandler timeTrackingHandler = handlerCache.get(timeTrackingType.name());
        if (isNull(timeTrackingHandler)) {
            throw new IllegalArgumentException(
                    "There is no handler found with the given type");
        }
        return timeTrackingHandler;
    }

}



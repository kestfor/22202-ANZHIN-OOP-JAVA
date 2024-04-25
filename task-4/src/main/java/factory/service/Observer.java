package factory.service;

import factory.service.events.Event;

public interface Observer {
    void notify(Event event);
}
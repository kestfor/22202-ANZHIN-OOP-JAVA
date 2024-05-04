package factory.service;

import view.events.Event;

public interface Observer {
    void notify(Event event);
}
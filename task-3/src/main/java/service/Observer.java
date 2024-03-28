package service;
import events.Event;

public interface Observer {
    void notify(Event event);
}

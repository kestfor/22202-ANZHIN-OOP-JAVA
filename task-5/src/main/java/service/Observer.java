package service;
import events.Event;

public interface Observer {
    void actionPerformed(Event event);
}

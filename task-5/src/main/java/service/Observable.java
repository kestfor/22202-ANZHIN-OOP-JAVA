package service;
import java.util.ArrayList;
import java.util.List;
import events.Event;

public abstract class Observable {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver (Observer observer) {
        observers.add(observer);
    }

    public void notify(Event event) {
        for (Observer o: observers) {
            o.actionPerformed(event);
        }
    }
}

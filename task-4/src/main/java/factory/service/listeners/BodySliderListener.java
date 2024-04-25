package factory.service.listeners;

import factory.service.Observable;
import factory.service.events.BodySupplierEvent;
import factory.service.events.DealerEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BodySliderListener extends Observable implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        notify(new BodySupplierEvent(slider.getValue()));
    }
}

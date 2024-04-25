package factory.service.listeners;

import factory.service.Observable;
import factory.service.events.DealerEvent;
import factory.service.events.MotorSupplierEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MotorSliderListener extends Observable implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        notify(new MotorSupplierEvent(slider.getValue()));
    }
}

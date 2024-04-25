package factory;

import factory.service.Observable;
import factory.service.Observer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class Settings extends Observable implements Observer {
    public int accessoriesSupplierSpeed;
    public int bodiesSupplierSpeed;
    public int motorsSupplierSpeed;
    public int dealerPeriod;

    public Settings(int accessoriesSupplierSpeed, int bodiesSupplierSpeed, int motorsSupplierSpeed, int dealerPeriod) {
        this.accessoriesSupplierSpeed = accessoriesSupplierSpeed;
        this.bodiesSupplierSpeed = bodiesSupplierSpeed;
        this.dealerPeriod = dealerPeriod;
        this.motorsSupplierSpeed = motorsSupplierSpeed;
    }
}
package factory;

import factory.service.Observable;
import factory.service.Observer;

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
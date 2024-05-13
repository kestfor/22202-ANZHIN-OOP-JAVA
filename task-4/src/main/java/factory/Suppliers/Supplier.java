package factory.Suppliers;

import factory.Products.Product;
import factory.Warehouses.Warehouse;

abstract public class Supplier extends Thread {

    protected int period;
    protected final Warehouse<Product> warehouse;

    public Supplier(int period, Warehouse<Product> warehouse) {
        this.period = period;
        this.warehouse = warehouse;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int newPeriod) {
        if (newPeriod > 0) {
            this.period = newPeriod;
        }
    }

    public void run() {
        while (true) {
            try {
                deliver();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    abstract public void deliver() throws InterruptedException;
}

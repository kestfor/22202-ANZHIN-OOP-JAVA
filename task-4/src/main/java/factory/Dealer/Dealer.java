package factory.Dealer;

import factory.Products.Car;
import factory.CarsWarehouseController.CarsWarehouseController;
import factory.SingletonLogger;
import factory.Utils.IdsGenerator;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Dealer extends Thread {
    protected long id;
    protected int period;
    protected final CarsWarehouseController warehouseController;

    private static final SingletonLogger logger = new SingletonLogger("DealerLog", "log.txt");

    private boolean log;

    public Dealer(int period, CarsWarehouseController warehouseController, boolean log) {
        this.id = IdsGenerator.generateId();
        this.warehouseController = warehouseController;
        this.period = period;
        this.log = log;
    }

    public Dealer(int period, CarsWarehouseController warehouseController) {
        this(period, warehouseController, false);
    }

    public int getSpeed() {
        return this.period;
    }

    public void run() {
        while (true) {
            try {
                Car car = orderCar();
                if (log) {
                    String msg = MessageFormat.format("Dealer {0} : Auto {1} : (Body: {2}, Motor {3}, Accessory {4})",
                            id, car.getId(), car.getBody().getId(), car.getMotor().getId(), car.getAccessory().getId());
                    logger.getLogger().log(Level.INFO, msg);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Car orderCar() throws InterruptedException {
        sleep(period);
        synchronized (warehouseController) {
            warehouseController.notify();
        }
        return (Car) warehouseController.getCarWarehouse().get();
    }

    public void setSpeed(int period) {
        this.period = period;
    }

}

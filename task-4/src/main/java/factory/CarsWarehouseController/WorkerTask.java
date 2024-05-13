package factory.CarsWarehouseController;
import factory.Products.*;
import factory.Warehouses.*;

import static java.lang.Thread.sleep;

public class WorkerTask implements Runnable {

    protected final WarehousesMap warehouses;

    public WorkerTask(WarehousesMap warehouses) {
        this.warehouses = warehouses;
    }

    @Override
    public void run() {
        Body body;
        Motor motor;
        Accessory accessory;

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        body = (Body) warehouses.get(WarehousesMap.WarehouseNames.bodies).get();
        motor = (Motor)  warehouses.get(WarehousesMap.WarehouseNames.motors).get();
        accessory = (Accessory)  warehouses.get(WarehousesMap.WarehouseNames.accessories).get();

        Car car = new Car(body, motor, accessory);

        warehouses.get(WarehousesMap.WarehouseNames.cars).put(car);
    }
}

package factory.CarsWarehouseController;
import factory.Products.*;
import factory.Warehouses.*;

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

        body = (Body) warehouses.get(WarehousesMap.WarehouseNames.bodies).get();
        motor = (Motor)  warehouses.get(WarehousesMap.WarehouseNames.motors).get();
        accessory = (Accessory)  warehouses.get(WarehousesMap.WarehouseNames.accessories).get();

        Car car = new Car(body, motor, accessory);
        System.out.println("Собрана машина");

        warehouses.get(WarehousesMap.WarehouseNames.cars).put(car);
        System.out.println("Машина доставлена на склад");
    }
}

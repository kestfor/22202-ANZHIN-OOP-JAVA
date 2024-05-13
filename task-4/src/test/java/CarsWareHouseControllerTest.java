import factory.CarsWarehouseController.CarsWarehouseController;
import factory.CarsWarehouseController.TasksController;
import factory.Dealer.Dealer;
import factory.Suppliers.AccessoriesSupplier;
import factory.Suppliers.BodiesSupplier;
import factory.Suppliers.MotorsSupplier;
import factory.Warehouses.*;
import threadpool.BlockingQueue;
import threadpool.ThreadPool;

import static java.lang.Thread.sleep;

public class CarsWareHouseControllerTest {
    public static void main(String[] args) throws InterruptedException {

        CarsWarehouse carsWarehouse = new CarsWarehouse(10);
        BodiesWarehouse bodiesWarehouse = new BodiesWarehouse(10);
        MotorsWarehouse motorsWarehouse = new MotorsWarehouse(10);
        AccessoriesWarehouse accessoriesWarehouse = new AccessoriesWarehouse(10);

        AccessoriesSupplier accessoriesSupplier = new AccessoriesSupplier(1000, accessoriesWarehouse);
        BodiesSupplier bodiesSupplier = new BodiesSupplier(1500, bodiesWarehouse);
        MotorsSupplier motorsSupplier = new MotorsSupplier(2000, motorsWarehouse);

        accessoriesSupplier.start();
        bodiesSupplier.start();
        motorsSupplier.start();

        WarehousesMap warehousesMap = new WarehousesMap(motorsWarehouse, bodiesWarehouse, accessoriesWarehouse, carsWarehouse);
        TasksController tasksController = new TasksController(2, warehousesMap);
        CarsWarehouseController carsWarehouseController = new CarsWarehouseController(carsWarehouse, tasksController, 5);
        Dealer dealer = new Dealer(1000, carsWarehouseController);

        carsWarehouseController.start();
        dealer.start();
    }
}

package factory.CarsWarehouseController;
import factory.Warehouses.CarsWarehouse;

public class CarsWarehouseController extends Thread {

    protected final CarsWarehouse warehouse;
    protected final TasksController tasksController;
    protected int criticalSize;

    public CarsWarehouseController(CarsWarehouse warehouse, TasksController tasksController, int criticalSize) {
        this.warehouse = warehouse;
        this.criticalSize = criticalSize;
        this.tasksController = tasksController;
    }

    public CarsWarehouseController(CarsWarehouse warehouse, TasksController tasksController) {
        this(warehouse, tasksController, 5);
    }
    public CarsWarehouse getCarWarehouse() {
        return warehouse;
    }

    public synchronized void run() {
        while (true) {
            try {
                wait();
                synchronized (warehouse) {
                    if (warehouse.getCurrentSize() < criticalSize) {
                        tasksController.addTasks((criticalSize - warehouse.getCurrentSize()) * 2);
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

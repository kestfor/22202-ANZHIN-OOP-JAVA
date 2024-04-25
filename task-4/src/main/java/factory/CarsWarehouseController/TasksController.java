package factory.CarsWarehouseController;
import factory.Warehouses.*;
import threadpool.BlockingQueue;
import threadpool.ThreadPool;

public class TasksController {

    protected WarehousesMap warehouses;
    protected BlockingQueue<Runnable> tasks;

    protected ThreadPool workers;

    public TasksController(int workersNum, WarehousesMap warehouses) {
        this.warehouses = warehouses;
        this.tasks = new BlockingQueue<>();
        this.workers = new ThreadPool(workersNum, tasks);
        this.workers.start();
    }

    public void addTasks(int num) {
        for (int i = 0; i < num; i++) {
            tasks.put(new WorkerTask(warehouses));
        }
    }

}

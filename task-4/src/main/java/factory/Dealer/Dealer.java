package factory.Dealer;
import factory.Products.Car;
import factory.CarsWarehouseController.CarsWarehouseController;

public class Dealer extends Thread{
    protected int period;
    protected final CarsWarehouseController warehouseController;

    public Dealer(int period, CarsWarehouseController warehouseController) {
        this.warehouseController = warehouseController;
        this.period = period;
    }

    public int getSpeed() {
        return this.period;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("заказана машина");
                Car car = orderCar();
                System.out.println("получена машина");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Car orderCar() throws InterruptedException {
        sleep(period);
        synchronized (warehouseController) {
            warehouseController.notify();
            System.out.println("сообщение контроллеру");
        }
        return (Car) warehouseController.getCarWarehouse().get();
    }

    public void setSpeed(int period) {
        this.period = period;
    }

}

package factory.Suppliers;
import factory.Products.Product;
import factory.Products.Motor;
import factory.Utils.IdsGenerator;
import factory.Warehouses.Warehouse;

public class MotorsSupplier extends Supplier {

    public MotorsSupplier(int period, Warehouse<Product> warehouse) {
        super(period, warehouse);
    }

    @Override
    public void deliver() throws InterruptedException {
        sleep(period);
        warehouse.put(new Motor(IdsGenerator.generateId()));
    }
}

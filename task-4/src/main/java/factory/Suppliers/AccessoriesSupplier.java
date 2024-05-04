package factory.Suppliers;
import factory.Products.Accessory;
import factory.Products.Product;
import factory.Utils.IdsGenerator;
import factory.Warehouses.Warehouse;

public class AccessoriesSupplier extends Supplier {

    public AccessoriesSupplier(int period, Warehouse<Product> warehouse) {
        super(period, warehouse);
    }

    @Override
    public void deliver() throws InterruptedException {
        sleep(period);
        warehouse.put(new Accessory(IdsGenerator.generateId()));
    }
}


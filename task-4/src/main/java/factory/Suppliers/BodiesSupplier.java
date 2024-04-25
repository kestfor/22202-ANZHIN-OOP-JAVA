package factory.Suppliers;

import factory.Products.Body;
import factory.Products.Product;
import factory.Utils.IdsGenerator;
import factory.Warehouses.Warehouse;

public class BodiesSupplier extends Supplier {

    public BodiesSupplier(int period, Warehouse<Product> warehouse) {
        super(period, warehouse);
    }

    @Override
    public void deliver() throws InterruptedException {
        sleep(period);
        warehouse.put(new Body(IdsGenerator.generateId()));
        System.out.println("доставлен кузов");
    }
}

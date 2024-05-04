package factory.Warehouses;

import factory.Products.Product;

public class BodiesWarehouse extends Warehouse<Product> {
    public BodiesWarehouse(int capacity) {
        super(capacity);
    }
}

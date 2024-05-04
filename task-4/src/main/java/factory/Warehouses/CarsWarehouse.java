package factory.Warehouses;

import factory.Products.Product;

public class CarsWarehouse extends Warehouse<Product> {
    public CarsWarehouse(int capacity) {
        super(capacity);
    }

}

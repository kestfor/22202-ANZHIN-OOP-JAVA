package factory.Warehouses;

import factory.Products.Product;

import java.util.HashMap;

public class WarehousesMap {
    protected final HashMap<WarehouseNames, Warehouse<Product>> warehouses;

    public enum WarehouseNames {
        accessories(AccessoriesWarehouse.class.getName()),
        motors(MotorsWarehouse.class.getName()),
        bodies(BodiesWarehouse.class.getName()),
        cars(CarsWarehouse.class.getName());

        private final String name;
        WarehouseNames(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    public WarehousesMap(MotorsWarehouse motorsWarehouse, BodiesWarehouse bodiesWarehouse, AccessoriesWarehouse accessoriesWarehouse, CarsWarehouse carsWarehouse) {
        this.warehouses = new HashMap<>();
        this.warehouses.put(WarehouseNames.accessories, accessoriesWarehouse);
        this.warehouses.put(WarehouseNames.motors, motorsWarehouse);
        this.warehouses.put(WarehouseNames.bodies, bodiesWarehouse);
        this.warehouses.put(WarehouseNames.cars, carsWarehouse);
    }

    public Warehouse<Product> get(WarehouseNames name) {
        return warehouses.getOrDefault(name, null);
    }
}

package factory;

import factory.CarsWarehouseController.CarsWarehouseController;
import factory.CarsWarehouseController.TasksController;
import factory.ConfigReader.ConfigReader;
import factory.Dealer.Dealer;
import factory.Suppliers.AccessoriesSupplier;
import factory.Suppliers.BodiesSupplier;
import factory.Suppliers.MotorsSupplier;
import factory.Warehouses.*;
import factory.service.Observer;
import view.events.*;
import view.FactoryPanel;
import view.events.Event;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Factory extends JFrame implements Runnable, Observer {


    public final ArrayList<Dealer> dealers;
    public final Settings settings;
    public final ArrayList<AccessoriesSupplier> accessoriesSuppliers;
    public final BodiesSupplier bodiesSupplier;
    public final MotorsSupplier motorsSupplier;

    public final WarehousesMap warehouses;
    public final CarsWarehouseController carsWarehouseController;

    public Factory(ConfigReader configReader, Settings settings) {
        this.settings = settings;
        CarsWarehouse carsWarehouse = new CarsWarehouse(configReader.get(ConfigReader.Settings.storageAutoSize));
        BodiesWarehouse bodiesWarehouse = new BodiesWarehouse(configReader.get(ConfigReader.Settings.storageBodySize));
        MotorsWarehouse motorsWarehouse = new MotorsWarehouse(configReader.get(ConfigReader.Settings.storageMotorSize));
        AccessoriesWarehouse accessoriesWarehouse = new AccessoriesWarehouse(configReader.get(ConfigReader.Settings.storageAccessorySize));

        accessoriesSuppliers = new ArrayList<>();
        for (int i = 0; i < configReader.get(ConfigReader.Settings.accessorySuppliers); i++) {
            accessoriesSuppliers.add(new AccessoriesSupplier(settings.accessoriesSupplierSpeed, accessoriesWarehouse));
        }

        bodiesSupplier = new BodiesSupplier(settings.bodiesSupplierSpeed, bodiesWarehouse);
        motorsSupplier = new MotorsSupplier(settings.motorsSupplierSpeed, motorsWarehouse);

        warehouses = new WarehousesMap(motorsWarehouse, bodiesWarehouse, accessoriesWarehouse, carsWarehouse);

        TasksController tasksController = new TasksController(configReader.get(ConfigReader.Settings.workers), warehouses);
        carsWarehouseController = new CarsWarehouseController(carsWarehouse, tasksController);

        dealers = new ArrayList<>();
        boolean log = configReader.get(ConfigReader.Settings.logSale) == 1;
        for (int i = 0; i < configReader.get(ConfigReader.Settings.dealers); i++) {
            if (log) {
                dealers.add(new Dealer(settings.dealerPeriod, carsWarehouseController, true));
            } else {
                dealers.add(new Dealer(settings.dealerPeriod, carsWarehouseController));
            }
        }
    }

    public void run() {

        for (AccessoriesSupplier supplier : accessoriesSuppliers) {
            supplier.start();
        }

        bodiesSupplier.start();
        motorsSupplier.start();


        for (Dealer dealer : dealers) {
            dealer.start();
        }

        carsWarehouseController.start();
    }

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader();
        Settings settings = new Settings(5000, 2000, 3000, 10000);
        Factory factory = new Factory(configReader, settings);
        FactoryPanel factoryPanel = new FactoryPanel(factory);
        factory.setSize(new Dimension(1080, 720));
        factory.setTitle("factory");
        factory.getContentPane().add(factoryPanel);
        factory.setVisible(true);
        factory.setDefaultCloseOperation(EXIT_ON_CLOSE);
        factory.setResizable(false);
        settings.addObserver(factory);
        factory.run();
    }

    @Override
    public void notify(Event event) {
        if (event instanceof AccessoriesSupplierEvent) {
            this.settings.accessoriesSupplierSpeed = event.value * 1000;
            for (AccessoriesSupplier supplier : accessoriesSuppliers) {
                supplier.setPeriod(this.settings.accessoriesSupplierSpeed);
            }
        } else if (event instanceof BodySupplierEvent) {
            this.settings.bodiesSupplierSpeed = event.value * 1000;
            bodiesSupplier.setPeriod(this.settings.bodiesSupplierSpeed);
        } else if (event instanceof DealerEvent) {
            this.settings.dealerPeriod = event.value * 1000;
            for (Dealer dealer : dealers) {
                dealer.setSpeed(this.settings.dealerPeriod);
            }
        } else if (event instanceof MotorSupplierEvent) {
            this.settings.motorsSupplierSpeed = event.value * 1000;
            motorsSupplier.setPeriod(this.settings.motorsSupplierSpeed);
        }
    }
}

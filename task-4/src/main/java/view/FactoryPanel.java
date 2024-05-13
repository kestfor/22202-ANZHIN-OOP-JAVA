package view;

import factory.Factory;
import factory.Warehouses.WarehousesMap;
import view.listeners.AccessoriesSliderListener;
import view.listeners.BodySliderListener;
import view.listeners.DealerSliderListener;
import view.listeners.MotorSliderListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class FactoryPanel extends JPanel implements ActionListener {
    private final Factory factory;

    public FactoryPanel(Factory factory) {
        this.factory = factory;
        this.setBackground(new Color(56, 56, 56));

        AccessoriesSliderListener l1 = new AccessoriesSliderListener();
        BodySliderListener l2 = new BodySliderListener();
        DealerSliderListener l3 = new DealerSliderListener();
        MotorSliderListener l4 = new MotorSliderListener();

        l1.addObserver(factory.settings);
        l2.addObserver(factory.settings);
        l3.addObserver(factory.settings);
        l4.addObserver(factory.settings);

        SliderWithLabel accessoriesSupplierSlider = new SliderWithLabel(l1, factory.accessoriesSuppliers.get(0).getPeriod() / 1000, "Accessories suppliers period (sec)");
        SliderWithLabel bodiesSupplierSlider = new SliderWithLabel(l2, factory.bodiesSupplier.getPeriod() / 1000, "Bodies supplier period (sec)");
        SliderWithLabel dealerSlider = new SliderWithLabel(l3, factory.dealers.get(0).getSpeed() / 1000, "Dealers period (sec)");
        SliderWithLabel motorsSupplierSlider = new SliderWithLabel(l4, factory.motorsSupplier.getPeriod() / 1000, "Motors supplier period (sec)");

        accessoriesSupplierSlider.setLocation(0, 0);
        bodiesSupplierSlider.setLocation(0, 50);
        dealerSlider.setLocation(0, 100);
        motorsSupplierSlider.setLocation(0, 150);

        this.add(accessoriesSupplierSlider);
        this.add(bodiesSupplierSlider);
        this.add(dealerSlider);
        this.add(motorsSupplierSlider);
        Timer timer = new Timer(100, this);
        timer.start();

    }

    private String getData() {
        String info = "";
        WarehousesMap warehouses = factory.warehouses;
        if (warehouses != null) {
            info += "Accessories delivered: " + warehouses.get(WarehousesMap.WarehouseNames.accessories).getTotalProduced() + '\n';
            info += "Bodies delivered: " + warehouses.get(WarehousesMap.WarehouseNames.bodies).getTotalProduced() + '\n';
            info += "Motors delivered: " + warehouses.get(WarehousesMap.WarehouseNames.motors).getTotalProduced() + '\n';
            info += "Cars produced: " + warehouses.get(WarehousesMap.WarehouseNames.cars).getTotalProduced() + "\n\n";

            info += "Accessories in warehouse: " + warehouses.get(WarehousesMap.WarehouseNames.accessories).getCurrentSize() + '\n';
            info += "Bodies in warehouse: " + warehouses.get(WarehousesMap.WarehouseNames.bodies).getCurrentSize() + '\n';
            info += "Motors in warehouse: " + warehouses.get(WarehousesMap.WarehouseNames.motors).getCurrentSize() + '\n';
            info += "Cars in warehouse: " + warehouses.get(WarehousesMap.WarehouseNames.cars).getCurrentSize() + "\n";
        }
        return info;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        String data = getData();
        Graphics2D graphics2D = (Graphics2D) g;
        FontRenderContext frc = graphics2D.getFontRenderContext();
        Font font = new Font("Times New Roman", Font.BOLD, 30);
        TextLayout layout = new TextLayout(data, font, frc);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(font);
        String[] outputs = data.split("\n");
        for (int i = 0; i < outputs.length; i++) {
            graphics2D.drawString(outputs[i], 400, (int) (400 + i * layout.getBounds().getHeight() + 1));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

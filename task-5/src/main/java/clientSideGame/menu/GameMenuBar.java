package clientSideGame.menu;

import javax.swing.*;

public class GameMenuBar extends JMenuBar {

    final Iterable<MenuSection> sections;

    public GameMenuBar(Iterable<MenuSection> sections) {
        super();
        this.sections = sections;
        for (JMenu section : sections) {
            this.add(section);
        }
    }



}

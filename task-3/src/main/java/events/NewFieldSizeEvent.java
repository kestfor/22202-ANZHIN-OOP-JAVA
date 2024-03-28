package events;

import utils.Pair;

public class NewFieldSizeEvent extends Event {

    Pair<Integer, Integer> newSize;
    public NewFieldSizeEvent(Pair<Integer, Integer> newSize) {
        this.newSize = newSize;
    }

    public Pair<Integer, Integer> getNewSize() {
        return newSize;
    }
}

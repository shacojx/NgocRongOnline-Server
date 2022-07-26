package real.player;

import java.util.ArrayList;
import real.item.Item;

public class Inventory extends ArrayList<Item> {

    public void removeItem(int index) {
        Item item = new Item();
        item.id = -1;
        set(index, item);
    }
}

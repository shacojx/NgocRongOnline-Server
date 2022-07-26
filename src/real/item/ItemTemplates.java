package real.item;

import java.util.ArrayList;

public class ItemTemplates {

    public static ArrayList<ItemTemplate> itemTemplates = new ArrayList<>();

    public static void add(ItemTemplate it) {
        ItemTemplate.entrys.add(it.id, it);
    }

    public static ItemTemplate get(int id) {
        return ItemTemplate.entrys.get(id);
    }

    public static short getPart(int itemTemplateID) {
        return ItemTemplates.get(itemTemplateID).part;
    }

    public static int getIcon(int itemTemplateID) {
        return ItemTemplates.get((short) itemTemplateID).iconID;
    }

}

package real.item;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ItemTemplate {

    public int id;

    public byte type;

    public byte gender;

    public String name;

    public String description;
    
    public byte level;
    
    public long expires = 0;
    
    public int iconID;

    public short part;

    public boolean isUpToUp;

    public int strRequire;
    public ArrayList<ItemOption> itemoption = new ArrayList<>();
    public static ArrayList<ItemTemplate> entrys = new ArrayList<ItemTemplate>();
    private static HashMap<Integer, ItemOptionTemplate> options = new HashMap<Integer, ItemOptionTemplate>();
    public static void put(int id, ItemOptionTemplate option) {
        ItemTemplate.options.put(id, option);
    }
//                int slot = rs.getInt("slot");
//                Item item = new Item();
//                item.id = rs.getInt("item_id");
//                if (item.id != -1) {
//                    item.template = ItemTemplates.get(rs.getShort("item_id"));
//                    item.quantity = rs.getInt("quantity");
//                    loadOptionsItem(item);
//                }
//                player.itemsBody.add(slot, item);
    public static Item parseItem(String str) {
        if(str.equals("{}")){
            return null;
        }
        Item item = new Item();
        JSONObject job = (JSONObject) JSONValue.parse(str);
        item.id = Short.parseShort(job.get((Object)"id").toString());
        item.quantity = Short.parseShort(job.get((Object)"quantity").toString());
        item.template = ItemTemplate.ItemTemplateID(item.id);
        JSONArray Option = (JSONArray)JSONValue.parse(job.get((Object)"option").toString());
        if (Option.size() > 0) {
        for (Object Option2 : Option) {
            JSONObject job2 = (JSONObject)Option2;
            ItemOption option = new ItemOption(Integer.parseInt(job2.get((Object)"id").toString()), Integer.parseInt(job2.get((Object)"param").toString()));
            item.itemOptions.add(option);
        }
        }else
        {
        item.itemOptions.add(new ItemOption(73, 0));
        }
        return item;
    }
    public static ItemTemplate ItemTemplateID(int id) {
        for (ItemTemplate entry : entrys) {
            if(entry.id == id)
            {
                return entry;
            }
        }
        return null;
    }
    
}

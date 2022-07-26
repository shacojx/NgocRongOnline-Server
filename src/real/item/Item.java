package real.item;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Item {
    public int id;
    public ItemTemplate template;
    public String info;
    public String content;
    public int quantity;
    public boolean isExpires = false;
    public boolean isUpToUp;
    public ArrayList<ItemOption> itemOptions;
    public Item() {
        this.id = -1;
        this.quantity = 1;
        this.itemOptions = new ArrayList<ItemOption>();
    }
    public String getInfo() {
        String strInfo = "";
        for (ItemOption itemOption : itemOptions) {
            strInfo += itemOption.getOptionString();
        }
        return strInfo;
    }

    public String getContent() {
        return "Yêu cầu sức mạnh " + this.template.strRequire + " trở lên";
    }

}

package real.item;

import java.util.ArrayList;

public class ItemSell {

    public int id;
    //public byte type;
    public Item item;
    public byte buyType = -1;
    public int buyCoin = 0;
    public int buyCoinLock = 0;
    public int buyGold = 0;
    public boolean isNew;
    public int saleCoinLock = 0;
    public static ArrayList<ItemSell> itemCanSell = new ArrayList<>();
    
    public static ItemSell getItemSellByID(int id)
    {
        for (ItemSell itemSell : itemCanSell) {
            if(itemSell.id == id)
            {
                return itemSell;
            }
        }
        return null;
    }
//    public static ItemSell SellItemType(int type) {
//        for (ItemSell entry : entrys) {
//            if (entry.type == type) {
//                return entry;
//            }
//        }
//        return null;
//    }
//
//    public static ItemSell getItemShellByItemID(int id) {
//
//        for (ItemSell itemShell : entrys) {
//            for (int i = 0; i < itemShell.item.length; i++) {
//                if (itemShell.item[i].id == id) {
//                    return itemShell;
//                }
//            }
//        }
//        return null;
//    }
//
    public static ItemSell getItemSell(int id,byte typeBuy) {

        for (ItemSell itemSell : itemCanSell) {
            if(itemSell.item.template.id == id && itemSell.buyType == typeBuy)
            {
                 return itemSell;
            }
        }
        return null;
    }
//
//    public static Item[] getAllItem() {
//        return entrys.get(0).item;
//    }
}

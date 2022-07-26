/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package real.item;

import java.sql.Array;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Shop {
    public int npcID;
    public int idTabShop;
    public ArrayList<TabItemShop> tabShops = new ArrayList<>();
    public static ArrayList<Shop> shops = new ArrayList<>();
    
    public static ArrayList<TabItemShop> getTabShop(int npcID, int tabShopID)
    {
        for (Shop shop : shops) {
            if(shop.npcID == npcID && shop.idTabShop == tabShopID)
            {
                return shop.tabShops;
            }
        }
        return null;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package real.item;

import java.io.IOException;
import real.player.Player;
import server.Service;
import server.io.Message;

/**
 *
 * @author Admin
 */
public class useItem {
    public static void uesItem(Player p, byte typeUse, byte where, byte index) throws IOException{
    Item item = p.ItemBag[index];
    int itemAction = item.id;
    if (where == 0) {
            //item ở body
            if (typeUse == 0) {
                //sử dụng

            } else if (typeUse == 1) {
                //Bỏ vật phẩm ra đất
                Item itemDrop = p.ItemBody[index];
                if (itemDrop == null) {
                    p.sendAddchatYellow("Vật phẩm không tồn tại");
                    return;
                }
                p.ItemBody[index] = null;
                p.zone.PlayerDropItem(p, itemDrop);
                Service.gI().updateItemBody(p);
            }
        } else if (where == 1) {
            //item ở bag
            if (typeUse == 0) {
                //sử dụng
            
            
            } else if (typeUse == 1) {
                //Bỏ vật phẩm ra đất
                Item itemDrop = p.ItemBag[index];
                if (itemDrop == null) {
                    p.sendAddchatYellow("Vật phẩm không tồn tại");
                    return;
                }
                p.ItemBag[index] = null;
                p.zone.PlayerDropItem(p, itemDrop);
                p.updateItemBag();
            }
    }
    Message m = new Message(-43);
    m.writer().writeByte(itemAction);
    m.writer().writeByte(where);//vi tri item
    m.writer().writeByte(index);
 //   m.writer().writeUTF("Bạn có chắc chắn muốn sử dụng ?");
    m.writer().flush();
    p.session.sendMessage(m);
    m.cleanup();
    
    }
}

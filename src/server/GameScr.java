/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import real.item.Item;
import real.item.ItemOption;
import real.item.ItemOptionTemplate;
import real.item.ItemSell;
import real.item.ItemTemplate;
import real.item.ItemTemplates;
import real.item.TabItemShop;
import real.map.MobTemplate;
import real.player.Player;
import server.io.Message;
import server.io.Session;

public class GameScr {
     public static void SendFile(Session session, int cmd, String url) throws IOException {
        byte[] ab = GameScr.loadFile(url).toByteArray();
        Message msg = new Message(cmd);
        msg.writer().write(ab);
        msg.writer().flush();
        session.sendMessage(msg);
        msg.cleanup();
    }
     public static void reciveImage(Player p, Message m) throws IOException {
        int id = m.reader().readInt();
        m.cleanup();

        byte[] icon = FileIO.readFile("res/icon/x" + p.session.zoomLevel + "/" + id + ".png");
        if (icon.length == 0) {
            return;
        } 
        m = new Message(-67);
        m.writer().writeInt(id);
        m.writer().writeInt(icon.length);
        m.writer().write(icon);
        m.writer().flush();
        p.session.sendMessage(m);
        m.cleanup();
    }
    public static void reciveImageSmall(Player p, Message m) throws IOException {
        int id = m.reader().readInt();
        m.cleanup();

        byte[] icon = FileIO.readFile("res/icon/x" + p.session.zoomLevel + "/" + id + ".png");
        if (icon.length == 0) {
            return;
        } 
        m = new Message(-77);
        m.writer().writeInt(id);
        m.writer().writeInt(icon.length);
        m.writer().flush();
        p.session.sendMessage(m);
        m.cleanup();
    }    
    public static ByteArrayOutputStream loadFile(String url) {
        FileInputStream openFileInput;
        try {
            openFileInput = new FileInputStream(url);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = openFileInput.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            openFileInput.close();
            return byteArrayOutputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void UIshop(Player p, TabItemShop[] tabs) throws IOException {
        Message m = null;
        try {
             m = new Message(-44);
        m.writer().writeByte(0);
        m.writer().writeByte(tabs.length);
        
        for (int i = 0; i < tabs.length; i++) {
            TabItemShop tabItemShop = tabs[i];
            System.out.println("shop: "+ tabItemShop.itemsSell.size());
            
            m.writer().writeUTF(tabItemShop.tabName.replace("$", "\n"));
            m.writer().writeByte(tabItemShop.itemsSell.size());

            for (int j = 0; j < tabItemShop.itemsSell.size(); j++) {

                ItemSell itemSell = tabItemShop.itemsSell.get(j);
                if (itemSell == null) {
                    m.writer().writeShort(-1);
                    System.out.println("item null");
                    continue;
                }
                m.writer().writeShort(itemSell.item.template.id);
                m.writer().writeInt(itemSell.buyGold);
                m.writer().writeInt(itemSell.buyCoin);

                m.writer().writeByte(itemSell.item.itemOptions.size());
                for (ItemOption itemOption : itemSell.item.itemOptions) {
                    m.writer().writeByte(itemOption.id);
                    m.writer().writeShort(itemOption.param);
                }
                //hiển thị new item
                m.writer().writeByte((itemSell.isNew ? 1 : 0));
                //xử lý preview cải trang
                boolean isCT = itemSell.item.template.type == 5;
                m.writer().writeByte((isCT ? 1 : 0));
                if (isCT) {
                    //head
                    m.writer().writeShort(1);
                    //body
                    m.writer().writeShort(1);
                    //leg
                    m.writer().writeShort(1);
                    //bag
                    m.writer().writeShort(1);
                }
            }
            m.writer().flush();
            p.session.sendMessage(m);
            m.cleanup();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        
    }

//    public static void buyItemStore(Player p, Message m) throws IOException {
//        byte type = m.reader().readByte();
//        byte index = m.reader().readByte();
//        short num = 1;
//        if (m.reader().available() > 0) {
//            num = m.reader().readShort();
//        }
//        m.cleanup();
//        Item sell = ItemSell.getItemTypeIndex(type, index);
//        if (num <= 0 || sell == null) {
//            return;
//        }
//        int buycoin = sell.buyCoin * num;
//        int buycoinlock = sell.buyCoinLock * num;
//        int buycoingold = sell.buyGold * num;
//        if (buycoin < 0 || buycoinlock < 0 || buycoingold < 0) {
//            return;
//        }
//        ItemTemplate data = ItemTemplates.get((short) sell.id);
//        if ((!data.isUpToUp && p.c.getBagNull() >= num) || (data.isUpToUp && p.c.getIndexBagid(sell.id, sell.isLock) != -1) || (data.isUpToUp && p.c.getBagNull() > 0)) {
//                if (p.c.xu < buycoin) {
//                    p.conn.sendMessageLog("Không đủ xu");
//                    return;
//                }
//                if (p.c.yen < buycoinlock) {
//                    p.conn.sendMessageLog("Không đủ yên");
//                    return;
//                }
//                if (p.luong < buycoingold) {
//                    p.conn.sendMessageLog("Không đủ lượng");
//                    return;
//                }
//                p.vang = -buycoin;
//                p.luongKhoa = -buycoinlock;
//                p.luong = -buycoingold;
//                for (int i = 0; i < num; i++) {
//                    Item item = new Item();
//                    item.id = sell.id;
//                    if (sell.isLock) {
//                        item.isLock = true;
//                    }
//                    item.sys = sell.sys;
//                    if (sell.isExpires) {
//                        item.isExpires = true;
//                        item.expires = util.TimeMillis(sell.expires);
//                    }
//                    item.saleCoinLock = sell.saleCoinLock;
//                    for (Option Option : sell.options) {
//                        int idOp = Option.id;
//                        int par = util.nextInt(item.getOptionShopMin(idOp, Option.param), Option.param);
//                        Option option = new Option(idOp, par);
//                        item.options.add(option);
//                    }
//                    if (data.isUpToUp) {
//                        item.quantity = num;
//                        p.c.addItemBag(true, item);
//                        break;
//                    } else {
//                        p.c.addItemBag(false, item);
//                    }
//                }
//                m = new Message(13);
//                m.writer().writeInt(p.vang);//xu
//                m.writer().writeInt(p.luongKhoa);//yen
//                m.writer().writeInt(p.luong);//luong
//                m.writer().flush();
//                p.session.sendMessage(m);
//                m.cleanup();
//            } else {
//                p.sendAddchatYellow("Hành trang không đủ chỗ trống");
//            }
//        
//    }
    
    public static void reciveImageMOB(Player p, Message m) {
        try {
            int id = m.reader().readUnsignedByte();
            MobTemplate mob = MobTemplate.getMob(id);
            if (mob == null) {
                return;
            }
            Util.debug(mob.tempId + " Id mob " + id);
            int zoomLv = p.session.zoomLevel;
            if (zoomLv < 1 || zoomLv > 4) {
                zoomLv = 1;
            }
            ByteArrayOutputStream a = loadFile("Img/mob/x" + zoomLv + "/" + id);
            if (a != null) {
                a.flush();
                byte[] ab = a.toByteArray();
                m = new Message(-28);
                m.writer().write(ab);
                m.writer().flush();
                p.session.sendMessage(m);
            }
            a.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (m != null) {
                m.cleanup();
            }
        }
    }
    public static void saveFile(String url, byte[] data) {
        try {
            File f = new File(url);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(url);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package server;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import jdk.nashorn.internal.ir.BreakNode;
import real.item.Item;
import real.item.ItemOption;
import real.item.ItemSell;
import real.item.ItemTemplate;
import real.item.ItemTemplates;
import real.item.Shop;
import real.item.TabItemShop;
import real.map.Map;
import real.map.Npc;
import real.map.Zone;
import real.player.Player;
import real.player.PlayerManger;
import server.io.Message;
import server.io.Session;

public class Menu {
    Server server = Server.gI();
    public static void doMenuArray(Player p,int idnpc,String chat, String[] menu) {
        Message m = null;
        try {
            m = new Message(32);
            m.writer().writeShort(idnpc);
            m.writer().writeUTF(chat);
            m.writer().writeByte(menu.length);
            for (byte i = 0; i < menu.length; ++i) {
            m.writer().writeUTF(menu[i]);
            }
            m.writer().flush();
            p.session.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }

    }
    public static void doMenuArraySay(Player p,short id, String[] menu) {
        Message m = null;
        try {
            m = new Message(38);
            m.writer().writeShort(id);
            for(byte i = 0; i < menu.length; i++) {
                m.writer().writeUTF(menu[i]);
            }
            m.writer().flush();
            p.session.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }

    }
    public static void sendWrite(Player p, String title,short type) {
        Message m = null;
        try {
            m = new Message(88);
            m.writer().writeUTF(title);
            m.writer().writeShort(type);
            m.writer().flush();
            p.session.sendMessage(m);
            m.cleanup();
        } catch (IOException var5) {
            var5.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }

    }
    public void textBoxId(Session session,short menuId, String str) {
        Message msg;
        try {
            msg = new Message(88);
            msg.writer().writeInt(menuId);
            msg.writer().writeUTF(str);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }
     public void sendTB(Session session, Player title, String s) {
        Message m = null;
        try {
            m = new Message(94);
            m.writer().writeUTF(title.name);
            m.writer().writeUTF(s);
            m.writer().flush();
            PlayerManger.gI().SendMessageServer(m);
            session.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }

    }
     public void ChatTG(Player p, int avatar, String chat3,byte cmd) {
        Message m = null;
        try {
            m = new Message(-70);
            m.writer().writeShort(avatar);
            m.writer().writeUTF(chat3);
            m.writer().writeByte(cmd);
            m.writer().flush();
            PlayerManger.gI().SendMessageServer(m);
            p.session.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }
    }
     public void ChatTG(Player p,short avatar, String str) {
        Message m = null;
        try {
            m = new Message(94);
            m.writer().writeShort(avatar);
            m.writer().writeUTF(str);
            m.writer().flush();
            p.session.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }
    }
     
//     public void openUIShop(Player p) throws IOException{
//     Message m = null;  
//     try{
//        m = new Message(-44);
//            m.writer().writeByte(0);
//            //Dữ liệu ảo
//            String[] shopname = Text.getUI(0, 1);
//            Item[] allItem = ItemSell.getAllItem();
//            m.writer().writeByte(shopname.length);
//
//
//            for (int i = 0; i < shopname.length; i++) {
//                m.writer().writeUTF(shopname[i]);
//                m.writer().writeByte(allItem.length);
//                for(int j = 0 ; j < allItem.length; j++){
//
//                Item item = allItem[j];
//                 if (item == null) {
//                    m.writer().writeShort(-1);
//                    System.out.println("item null");
//                }
//                m.writer().writeShort(item.template.id);
//                m.writer().writeInt(item.buyCoin);
//                m.writer().writeInt(item.buyGold);
//
//                m.writer().writeByte(item.itemOptions.size());
//                for (ItemOption itemOption : item.itemOptions) {
//                        m.writer().writeByte(itemOption.id);
//                        m.writer().writeShort(itemOption.param);
//                }
//                //hiển thị new item
//                m.writer().writeByte((item.newItem ? 1 : 0));
//                //xử lý preview cải trang
//                boolean isCT = item.template.type == 5;
//                m.writer().writeByte((isCT ? 1 : 0));
//                if(isCT)
//                {
//                    //head
//                    m.writer().writeShort(1);
//                    //body
//                    m.writer().writeShort(1);
//                    //leg
//                    m.writer().writeShort(1);
//                    //bag
//                    m.writer().writeShort(1);
//                }
//            }
//        }
//            m.writer().flush();
//            p.session.sendMessage(m);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if(m != null) {
//                m.cleanup();
//            }
//        }
//     }
     //cmd -81 nâng cấp
     //cmd -57 clan
     //cmd -44 shop
     //cmd -41 thể lực
     //-34 đậu
     //-2 cộng vàng
     //+95 xu
     // -16 liveformdead
     // -105 máy bay
     // -106 time sử dụng item
      // - 119 rank
      //-126 win quay số
      // 113 eff
      //-97 năng động
      //-88 logout
      //-98 yes no
      //-35 rương đồ
     public void Test(Player p) {
        Message m = null;
        try {
//            Item[] shop = p.ItemBag;
//            
//            System.out.println("bag "+ p.ItemBag);
            m = new Message(-81);
            m.writer().writeByte(0);
//            
//            String[] shopname = new String[]{"Shop\nName"};
//            m.writer().writeByte(shopname.length);
//        //    for(int z = 0; z < shop.length;z++){
//            for(byte i = 0 ; i < shopname.length ; i++){
            m.writer().writeUTF("Ngọc Rồng Z");
            m.writer().writeUTF("Chọn đồ nâng cấp");
//           
//            }
//             Item[] allItem = ItemSell.getAllItem();
//            m.writer().writeByte(allItem.length);
//           
//            for(int j = 0 ; j < allItem.length; j++){
//                
//                Item item = allItem[j];
//                 if (item == null) {
//                    m.writer().writeShort(-1);
//                    System.out.println("item null");
//                }
//                m.writer().writeShort(item.template.id);
//                m.writer().writeInt(item.buyCoin);
//                m.writer().writeInt(item.buyGold);
//                    
//                m.writer().writeByte(item.itemOptions.size());
//                //System.out.println("otp size" + item.itemOptions.size());
//                for (ItemOption itemOption : item.itemOptions) {
//                        m.writer().writeByte(itemOption.id);
//                        m.writer().writeShort(itemOption.param);
//                }
//                //hiển thị new item
//                m.writer().writeByte((item.newItem ? 1 : 0));
//                //xử lý preview cải trang
//                boolean isCT = item.template.type == 5;
//                m.writer().writeByte((isCT ? 1 : 0));
//                if(isCT)
//                {
//                    m.writer().writeShort(item.template.part);
//                    m.writer().writeShort(1);
//                    m.writer().writeShort(1);
//                    m.writer().writeShort(1);
//                }
//            }
////              for (Item item : p.ItemBag) {
////                if (item == null) {
////                    m.writer().writeShort(-1);
////                    System.out.println("item null");
////                } else {
////                    m.writer().writeShort(item.template.id);
//////                    m.writer().writeInt(item.quantity);
//////                    m.writer().writeUTF(item.getInfo());
//////                    m.writer().writeUTF(item.getContent());
////                 //   m.writer().writeShort(1);
////                   m.writer().writeInt(1000);
////                   m.writer().writeInt(10000);
////                    }
////                    m.writer().writeByte(0);
////                    m.writer().writeByte(0);
////                    m.writer().writeShort(0);
////                    m.writer().writeByte(0);
////             m.writer().writeByte(0);
////                    
////            }
//            
////            m.writer().writeByte(0);
//       //     m.writer().writeUTF("5");
//        //    m.writer().writeUTF("5");
//        //    m.writer().writeByte(shop.length);
////            for(int z = 0; z < shop.length;z++){
////            m.writer().writeShort(1);
//            
////             m.writer().writeShort(1);
////           }
////            m.writer().writeShort(1);
////            m.writer().writeShort(1);
//      //      m.writer().writeInt(1);
//      //      m.writer().writeInt(p.luong);
////            Item[] itemsBox = p.ItemBox;
////            m.writer().writeByte(itemsBox.length);
////            for (int i = 0; i < itemsBox.length; i++) {
////                Item item = itemsBox[i];
////                if (item == null) {
////                    m.writer().writeShort(-1);
////            }
////            }
//  //          m.writer().writeByte(0);
//          //  m.writer().writeUTF("5");
//    //        m.writer().writeInt(10);
//     //       m.writer().writeUTF(p.name);
//        //    m.writer().writeUTF("5");
//      //        m.writer().writeInt(1);
////	      m.writer().writeShort(p.x);
////	      m.writer().writeShort(p.y - 60);
////	      m.writer().writeShort(1000);
//       //       m.writer().writeShort(1);
//    //      m.writer().writeByte(0);
//   //           m.writer().writeInt(1);
//    //          m.writer().writeByte(1);
            m.writer().flush();
            p.session.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }
    }
     public void LuckyRound(Player p,byte type,byte soluong) throws IOException{
         Message m = null;
        try {
         if(type == 0){
         m = new Message(-127);
         m.writer().writeByte(type);
         short[] arId = new short[]{2280,2281,2282,2283,2284,2285,2286};
         m.writer().writeByte(7);
         for(short i = 0; i< arId.length ;i++){
         m.writer().writeShort(arId[i]); 
         }
         m.writer().writeByte(soluong);
         m.writer().writeInt(10000);
         m.writer().writeShort(0);
         m.writer().flush();
         p.session.sendMessage(m);
         }else if(type == 1){
         m = new Message(-127);
         m.writer().writeByte(soluong);
         short[] arId = new short[]{2,3,4,5,6,7,8};
         for(short i = 0; i< soluong ;i++){
         m.writer().writeShort(arId[i]); 
         }
         m.writer().flush();
         p.session.sendMessage(m);
         }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }
     }
     public void confirmMenu(Player p, Message m) throws IOException, SQLException, InterruptedException {
        Short idNpc = m.reader().readShort();
        byte select = m.reader().readByte();
        switch (p.menuNPCID) {
        case 9:{
                if(select == 0){
                TabItemShop[] test = Shop.getTabShop(9, 0).toArray(new TabItemShop[0]);
                GameScr.UIshop(p, test);
                break;
                }
            break;
        }
        //Quy lão Kame
        case 13 :{
        if(p.menuID != -1)
        {
            if(p.menuID == 1 && select == 0)
            {
                p.openBox();
            }
        }
        if(select == 1){
        doMenuArray(p,idNpc,"Ngọc Rồng Z",new String[]{"Mở rương"});
        p.menuID = select;
        }
        break;
        }
        case 21:{
         if (select == 0) {
         Test(p);
         break;
         }
         if (select == 1) {
         if (p.ngoc < 10000) {
         p.sendAddchatYellow("Tải game tại NgocRongZ.TK");
         } else {
         p.sendAddchatYellow("Tải game tại NgocRongZ.TK");
               } 
             }
        }
        case 18:
        if(select == 0){
        Service.gI().CHAGE_MOD_BODY(p);
     //   LuckyRound(p,(byte)0,(byte)7);
        break;
        }
        default:{
        break;
        }
       
         //   Service.getInstance().serverMessage(p.session,"ID NPC " + b1);
         }
        m.cleanup();
     }
     public void menuHandler(Player p, Message m) throws IOException, SQLException, InterruptedException {
        byte idNPC = m.reader().readByte();// ID NPC
        byte menuID = m.reader().readByte();// Lớp nút 1
        byte select = m.reader().readByte();// Lớp nút 2
         System.out.println("menuID: "+ p.menuID);
         System.out.println("menuNPCID: "+ p.menuNPCID);
         System.out.println("select: "+ select);
        int tl;
        switch (p.menuNPCID) {
        
        case 13 :
            if(p.menuID == 1)
            {
                if(select == 0){
                p.openBox();
                }
            }
            break;
        case 21:{
            if (select == 0) {
            Test(p);
            break;
            }
            if (select == 1) {
            if (p.ngoc < 10000) {
            p.sendAddchatYellow("Tải game tại NgocRongZ.TK");
            } else {
            p.sendAddchatYellow("Tải game tại NgocRongZ.TK");
                  } 
            }
        }
        default:{
        break;
        }
       
         //   Service.getInstance().serverMessage(p.session,"ID NPC " + b1);
         }
        m.cleanup();
     }
     public  void openUINpc(Player p, Message m) throws IOException {
        short idnpc = m.reader().readShort();//idnpc
        int avatar;
        m.cleanup();
        p.menuID = -1;
        p.menuNPCID = idnpc;
        avatar = NpcAvatar(p, idnpc);
        m = new Message(33);
        if (p.menuNPCID == 21) {
        doMenuArray(p,idnpc,"Ngọc Rồng Z",new String[]{"Nâng Cấp"});
        return;
        }
        if (p.menuNPCID == 1) {
        ChatTG(p,avatar,Text.get(0, 0),(byte)0);
        return;
        }
        if (p.menuNPCID == 9) {
        doMenuArray(p,idnpc,Text.get(0, 1),new String[]{"Cửa Hàng"});
        return;
        }
        //Quy lão Kame
        if (p.menuNPCID == 13) {
        doMenuArray(p,idnpc,"Ngọc Rồng Z",new String[]{"Nói chuyện","Tính năng"});
        return;
        }
        if (p.menuNPCID == 18) {
        doMenuArray(p,idnpc,"Thử Vận May Mắn!",new String[]{"Quay Số"});
        return;
        }
        if(p.menuNPCID == 3){
        p.openBox();
        return;
        }else{
        Service.gI().serverMessage(p.session, "Npc id" + idnpc);
        }
      
//        
        
        
      //  Service.getInstance().serverMessage(p.session,"ID NPC " + idnpc);
        m.writer().flush();
        p.session.sendMessage(m);
        m.cleanup();
    }
    public int NpcAvatar(Player p,int npcID){
       
        for (int i = 0; i < p.getPlace().map.template.npcs.length; i++){
            if(p.getPlace().map.template.npcs[i].tempId == npcID)
            {
                return p.getPlace().map.template.npcs[i].avartar;
            }    

        }
        return -1;
    }
}

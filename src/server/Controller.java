package server;

import server.io.Message;
import server.io.Session;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import real.clan.ClanService;
import real.item.Item;
import real.item.ItemDAO;
import real.item.ItemSell;
import real.item.useItem;
import real.map.Map;
import real.map.Zone;
import real.map.Mob;
import real.map.WayPoint;
import real.player.Player;
import real.player.PlayerDAO;
import real.player.PlayerManger;
import real.player.UseSkill;
import real.player.User;

public class Controller {

    private static Controller instance;
    Server server = Server.gI();

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void onMessage(Session _session, Message m) {
        try {
            Player player = PlayerManger.gI().getPlayerByUserID(_session.userId);
            byte cmd = m.command;
            switch (cmd) {
                case -127:
                    byte typequay = m.reader().readByte();
                    byte soluong = m.reader().readByte();
                    server.menu.LuckyRound(player,typequay,soluong);
                    break;
                case -113:
                    UseSkill.useSkill(player, m);
                    break;
                case -84:
                    Service.gI().CHAGE_MOD_BODY(player);
                    break;
                case -107:
                    Service.gI().sendMessage(_session, -107, "detu");
                    break;
                case -101:
                    login2(_session);
                    break;

                case -87:
                    //send data
                    Manager.sendData(player);
                    break;
                case -81:
                    byte actionn = m.reader().readByte();
                    byte indexUI = m.reader().readByte();
                    System.out.println("action " + actionn + "index UI " + indexUI);
                    Service.gI().upgrade(player, actionn, indexUI);
                    break;
                case -80:
                    Service.gI().sendMessage(_session, -80, "1630679754715_-80_r");
                    break;
                case -74:
                    byte type = m.reader().readByte();
                    if (type == 1) {
                        Service.gI().sizeImageSource(_session);
                        Util.debug("Type 1 load");
                    } else if (type == 2) {
                        Service.gI().imageSource(_session);
                        Util.debug("Type 2 load");
                    }

                    break;
                case -71:
                    String chat2 = m.reader().readUTF();
//                    if (player.name.equals("admin")) {
//                        Service.gI().serverTB(player, chat2);
//                        return;
//                    }

                    Service.gI().ChatGolbaL(player, chat2);
                    break;
                case -70:
                    String chat3 = m.reader().readUTF();
                    server.menu.ChatTG(player, player.head, chat3, (byte) 1);
                    break;
                case -77:
                case -67:
                //    int id = m.reader().readInt();
               //     System.out.println("icon " + id);
                  //  Service.gI().requestIcon(_session, id);
                    GameScr.reciveImage(player, m);
                    break;
                case -66:
                    int effId = m.reader().readShort();
                    Service.gI().effData(_session, effId);
                    break;
                case -63:
                    // id image logo clan
                    Service.gI().sendMessage(_session, -63, "1630679755147_-63_r");
                    break;

                case -55:
                    //leaveClan
                    Service.gI().serverMessage(_session, "leaveClan");
                    Util.log("leaveClan");
                    break;
                case -50:
                    int clanId = m.reader().readInt();
                    ClanService.gI().clanMember(_session, clanId);
                    break;
                case -47:
                    String clanName = m.reader().readUTF();
                    ClanService.gI().searchClan(_session, clanName);
                    break;
                case -46:
                    byte action = m.reader().readByte();
                    Util.log("Clan action: " + action);
                    if (action == 4) {
                        m.reader().readByte();
                        Service.gI().updateSloganClan(_session, m.reader().readUTF());
                    }
                    break;
                case -43:
                    try {
                        //use, drop item handler
                        byte typeUse = m.reader().readByte();
                        byte where = m.reader().readByte();
                        byte index = m.reader().readByte();
//                        if (index == -1) {
//                            short template = m.reader().readShort();
//                        }
                        System.out.println("-43 typeUse: " + typeUse + " where: " + where + " index: " + index);
                  //      UseItemHandler(player, typeUse, where, index);
                        useItem.uesItem(player,typeUse, where, index);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case -41:
                    //UPDATE_CAPTION
                    Service.gI().sendMessage(_session, -41, "1630679754812_-41_r");
                    break;
                case -40:
                    ReadMessage.gI().getItem(_session, m);
                    break;
                case -39:
                    //finishLoadMap
                    player.zone.loadPlayers(player);
                    break;
                case -32:
                    int bgId = m.reader().readShort();
                    Service.gI().bgTemp(_session, bgId);
                    break;
                case -30:
                    messageSubCommand(_session, m);
                    break;

                case -29:
                    messageNotLogin(_session, m);
                    break;
                case -28:
                    messageNotMap(_session, m);
                    break;
                case -27:
                    _session.sendSessionKey();
                    Service.gI().sendMessage(_session, -111, "1630679748814_-111_r");
                    Service.gI().versionImageSource(_session);
                    Service.gI().sendMessage(_session, -29, "1630679748828_-29_2_r");
                    break;
                case -23:
                    WayPoint[] wp = player.map.template.wayPoints;
                    if (wp != null) {
                        player.zone.VGo(player, m);
                    } else {
                        Service.gI().serverMessage(_session, "Không thể vào map");
                        Service.gI().resetPoint(_session, player.x - 50, player.y);
                    }
                    Util.log("change map");
                    break;
                case -20:
                    try {
                        short itemMapId = m.reader().readShort();
                        player.zone.PickItemDrop(player, itemMapId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case -7:
                    byte b = m.reader().readByte();
//                    if (b == 0) {
//                        player.move(msg.reader().readShort(), player.getY());
//                    } else {
//                        player.move(msg.reader().readShort(), msg.reader().readShort());
//                    }
                    try {
                        player.x = m.reader().readShort();
                        player.y = m.reader().readShort();
                    } catch (Exception e) {
                    }
                    player.zone.playerMove(player);
                    break;
                case 6:
                    //mua item trong shop

                    try {
                        byte typeBuy = m.reader().readByte();
                        short itemID = m.reader().readShort();
                        //nên fix lại để tối ưu hiệu năng đây chỉ là tạm thời

                        ItemSell itemBuy = ItemSell.getItemSell(itemID, typeBuy);
                        if (itemBuy == null) {
                            player.sendAddchatYellow("Item " + itemID + " chưa được mở bán");
                            Service.gI().buyDone(player);
                            return;
                        }
                        System.out.println("itemBuy.item.template.name: " + itemBuy.item.template.name);
                        if (typeBuy != itemBuy.buyType) {
                            if (typeBuy == 0) {
                                player.sendAddchatYellow("Item không bán bằng vàng");
                            }
                            if (typeBuy == 1) {
                                player.sendAddchatYellow("Item không bán bằng ngọc");
                            }
                            Service.gI().buyDone(player);
                            return;
                        }
                        boolean isCanBuy = false;

                        if (itemBuy.buyType == 0) {
                            int goldReslut = player.vang - itemBuy.buyGold;
                            if (goldReslut >= 0) {
                                player.vang -= itemBuy.buyGold;
                                System.out.println("buyGold : " + itemBuy.buyGold);
                                isCanBuy = true;
                            } else {
                                player.sendAddchatYellow("Bạn thiếu " + Math.abs(goldReslut) + " để mua vật phẩm");
                            }

                        } else if (itemBuy.buyType == 1) {
                            System.out.println("buyCoin : " + itemBuy.buyCoin);
                            if (player.ngocKhoa - itemBuy.buyCoin >= 0) {
                                player.ngocKhoa -= itemBuy.buyCoin;
                                isCanBuy = true;
                            } else if (player.ngoc - itemBuy.buyCoin >= 0) {
                                player.ngoc -= itemBuy.buyCoin;
                                isCanBuy = true;
                            } else {
                                player.sendAddchatYellow("Bạn thiếu " + Math.abs(player.ngoc -= itemBuy.buyCoin) + " để mua vật phẩm");
                            }
                        } else {
                            player.sendAddchatYellow("lỗi typeBuy");
                        }
                        if (isCanBuy) {
                            player.addItemToBag(itemBuy.item);
                        }
                        player.updateItemBag();
                        Service.gI().buyDone(player);
                        System.out.println("  typeBuy: " + typeBuy + "  itemID: " + itemID);
                    } catch (Exception e) {
                        player.sendAddchatYellow("Mua vật phẩm không thành công");
                        e.printStackTrace();
                    }
                    break;
                case 11:
                    byte modId = m.reader().readByte();
                    Service.gI().requestModTemplate(_session, modId);
                    break;
                case 21:
                    //Chon khu vuc
                    if (player != null) {
                        player.zone.selectUIZone(player, m);
                    }
                    break;

                case 22:
                    //Xử lý menu có option 
                    server.menu.menuHandler(player, m);
                    System.out.println("Menu 22");
                    break;
                case 29:
                    if (player != null) {
                        player.zone.openUIZone(player);
                        break;
                    }

                case 32:
                    server.menu.confirmMenu(player, m);
                    break;

                case 33:
                    //gọi NPC   
                    server.menu.openUINpc(player, m);
                    break;
                case 34:
                    short selectSkill = m.reader().readShort();
                    player.selectSkill = player.getSkill(selectSkill);
                    Util.log("skill select temp " + selectSkill + " - skillId " + player.selectSkill.skillId);
                    break;
                case 35:
                    break;
                case 44:
                    String text = m.reader().readUTF();
                    if (server.isDebug) {
                        if (text.contains("m ")) {
                            int mapId = Integer.parseInt(text.replace("m ", ""));

                            Map maptele = Manager.getMapid(mapId);
                            teleportToMAP(player, maptele);
                            Util.debug("Map go " + player.map.id);
                        } else if (text.contains("smtn ")) {
                            int amount = Integer.parseInt(text.replace("smtn ", ""));
                            player.UpdateSMTN((byte) 2, amount);
                        } else if (text.equals("shop")) {
                            //Gamesrc.UIshop(player, tabs);
                            //Service.getInstance().sendMessage(_session, -44, "1632921172115_-44_r");
                        } else if (text.equals("die")) {
                            player.getPlace().LiveFromDead(player);
                        } else if (text.equals("check")) {
                            player.sendAddchatYellow("MAP " + player.x + " " + player.y);
                        } else if (text.contains("u ")) {
                            short u = Short.parseShort(text.replace("u ", ""));
                            player.y += u;
                            player.zone.playerMove(player);
                        }

                    } else {
                        player.zone.chat(_session, text);
                    }
                    break;

                //tấn công quái    
                case 54:
                    player.getPlace().FightMob(player, m);
                    break;
                // nhap
                case 88:
                    Draw.Draw(player, m);
                    break;
                default:
                    Util.log("CMD: " + cmd);
                    player.sendAddchatYellow("Tính năng " + cmd + " chưa mở");
                    break;
            }
        } catch (Exception e) {
        }
        if (m != null) {
            m.cleanup();
        }
    }

    public void UseItemHandler(Player p, byte typeUse, byte where, byte index) {

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
    }

    public void teleportToMAP(Player p, Map map) {

        p.zone.leave(p);
        map.getPlayers().add(p);
        p.map = map;
        p.map.area[0].Enter(p);

    }

    public void messageNotLogin(Session session, Message msg) {
        if (msg != null) {
            try {
                byte cmd = msg.reader().readByte();
                switch (cmd) {
                    case 0:
                        login(session, msg);
                        break;
                    case 2:
                        session.setClientType(msg);
                        break;
                    default:
                        Util.log("messageNotLogin: " + cmd);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void messageNotMap(Session _session, Message _msg) {
        if (_msg != null) {
            try {
                Player player = PlayerManger.gI().getPlayerByUserID(_session.userId);
                byte cmd = _msg.reader().readByte();
                switch (cmd) {
                    case 2:
                        createChar(_session, _msg);
                        break;
                    // send data map
                    case 6:
                        Manager.sendMap(player);
                        break;
                    // send data skill
                    case 7:
                        Manager.sendSkill(player);
                        break;
                    // send data item
                    case 8:
                        Manager.sendItem(player);
                        break;
                    case 10:
                        Util.log("map temp");
                        // -28_10 REQUEST_MAPTEMPLATServiceE
                        Service.gI().mapTemp(_session, player.map.getId());
                        //Service.getInstance().sendMessage(-28, "1630679754853_-28_10_r");
                        break;
                    case 13:
                        //client ok
                        Util.log("client ok");
                        break;
                    default:
                        Util.log("messageNotMap: " + cmd);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void messageSubCommand(Session _session, Message _msg) {
        if (_msg != null) {
            try {
                Player player = PlayerManger.gI().getPlayerByUserID(_session.userId);
                byte command = _msg.reader().readByte();
                switch (command) {
                    case 5:

                    case 16:
                        byte type = _msg.reader().readByte();
                        short point = _msg.reader().readShort();
                        player.increasePoint(type, point);
                        break;
                    case 63:
                        Service.gI().GET_PLAYER_MENU(player);
                        break;
                    default:
                        Util.log("messageSubCommand: " + command);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void login(Session session, Message msg) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String user = msg.reader().readUTF();
            String pass = msg.reader().readUTF();
            msg.reader().readUTF();
            msg.reader().readByte();
            // -77 SMALLIMAGE_VERSION
            Service.gI().sendMessage(session, -77, "1630679752225_-77_r");
            // -93 BGITEM_VERSION
            Service.gI().sendMessage(session, -93, "1630679752231_-93_r");
            conn = DBService.gI().getConnection();
            pstmt = conn.prepareStatement("select * from account where username=? and password=? limit 1");
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            Util.log("user " + user + " - pass " + pass);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                session.nhanvat = rs.getString("nhanvat").toLowerCase();
                session.taikhoan = rs.getString("username").toLowerCase();
                session.matkhau = rs.getString("password").toLowerCase();
                session.userId = rs.getInt("id");
                Util.debug("TEN NV " + session.nhanvat);
                if (rs.getBoolean("ban")) {
                    Service.gI().serverMessage(session, "Tài khoản đã bị khóa vui lòng liên hệ admin để biết thêm chi tiết");
                } else if (PlayerManger.gI().getPlayerByUserID(session.userId) != null) {
                    Service.gI().serverMessage(session, "Bạn đang đăng nhập trên thiết bị khác");
                } else {
                    pstmt = conn.prepareStatement("select * from player where account_id=? limit 1");
                    pstmt.setInt(1, session.userId);
                    msg.cleanup();
                    Util.debug("Load xịt");
                    rs = pstmt.executeQuery();
                    if (rs.first()) {
//                        long time1 = rs.getTimestamp("last_logout_time").getTime();
//                        long time2 = (System.currentTimeMillis() - time1) / 1000;
//                        if (time2 >= 0) {
                        Player player = Player.setup(session.userId);
                        PlayerManger.gI().getPlayers().add(player);
                        player.active();
                        player.session = session;
                        //player.loadItems();
                        // -28_4 
                        Service.gI().updateVersion(session);
                 //       Service.gI().sendMessage(session, -28, "1630679754226_-28_4_r");
                        // -31 ITEM_BACKGROUND
                        Service.gI().itemBg(session, 0);
                        sendInfo(session);
                        player.sendAddchatYellow("Tải game tại NgocRongZ.TK");
                        PlayerManger.gI().put(session);
                        PlayerManger.gI().put(player);
                        session.player = player;
                        //last_login_time
//                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//                            pstmt = conn.prepareStatement("update player set last_login_time=? where id=?");
//                            pstmt.setTimestamp(1, timestamp);
//                            pstmt.setInt(2, 1);
//                            pstmt.executeUpdate();
//                        } else {
//                            //Service.getInstance().loginDe((short) (30 - time2));
//                        }
                    } else {
                        Service.gI().sendMessage(session, -28, "1630679754226_-28_4_r");
                        Service.gI().sendMessage(session, -31, "1631370772604_-31_r");
                        Service.gI().sendMessage(session, -82, "1631370772610_-82_r");
                        Service.gI().sendMessage(session, 2, "1631370772901_2_r");
                    }
                }
            } else {
                Service.gI().serverMessage(session, "Tài khoản mật khẩu không chính xác !");
            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void createChar(Session session, Message msg) {
        Connection conn = DBService.gI().getConnection();
        PreparedStatement pstmt = null;
        //String CREATE_NHANVAT = "UPDATE account SET nhanvat==? WHERE id=?)";
        try {
            String name = msg.reader().readUTF();
            int gender = msg.reader().readByte();
            int head = msg.reader().readByte();
            if (gender > 2 || gender < 0) {
                session.player.sendAddchatYellow("Hành tinh lựa chòn không hợp lệ !");
            }
            pstmt = conn.prepareStatement("SELECT * FROM `player` WHERE name=?");
            Util.debug("id" + session.userId);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.first()) {
                if (PlayerDAO.create(session, name, gender, head)) {
                    session.player = Player.setup(session.userId);
                    PlayerManger.gI().getPlayers().add(session.player);
                    session.player.active();
                    session.player.session = session;
                    Service.gI().updateVersion(session);
                    Service.gI().itemBg(session, 0);
                    sendInfo(session);
                    session.player.sendAddchatYellow("Tải game tại NgocRongZ.TK");
                    PlayerManger.gI().put(session);
                    PlayerManger.gI().put(session.player);
                    session.player = session.player;

                }
            } else {
                Service.gI().serverMessage(session, "Tên đã tồn tại");
            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void login2(Session session) {
        String user = "User" + Util.nextInt(2222222, 8888888);
        Connection conn = DBService.gI().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO account(username,password,nhanvat) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user);
            ps.setString(2, "");
            ps.setBoolean(3, true);
            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.first()) {
                    Service.gI().login2(session, user);

                }
            } else {
                Service.gI().serverMessage(session, "Có lỗi vui lòng thử lại");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("lỗi server.Controller.login2()");
        }
    }

    public void sendInfo(Session session) {
        Player player = PlayerManger.gI().getPlayerByUserID(session.userId);
        // -82 TILE_SET
        Service.gI().tileSet(session, player.map.id);
        // 112 SPEACIAL_SKILL
        Service.gI().sendMessage(session, 112, "1630679754607_112_r");
        // -42 ME_LOAD_POINT
        Service.gI().loadPoint(session, player);
        //Service.getInstance().sendMessage(-42, "1630679754614_-42_r");
        // 40 TASK_GET
        Service.gI().sendMessage(session, 40, "1630679754622_40_r");
        // -22 MAP_CLEAR
        Service.gI().clearMap(session);
        //Service.getInstance().sendMessage(-22, "1630679754629_-22_r");
        // -42 ME_LOAD_POINT
        //Service.getInstance().sendMessage(-42, "1630679754637_-42_r");
        // -30_0 ME_LOAD_ALL
        Service.gI().loadPlayer(session, player);
        //Service.getInstance().sendMessage(session, -30, "1632838985276_-30_0_r");
        //Service.getInstance().sendMessage(-42, "1630679754652_-42_r");
        // -53 CLAN_INFO
        if (player.clan != null) {
            //ClanService.gI().clanInfo(session, player);
        }

        //Service.getInstance().sendMessage(-53, "1630679754659_-53_r");
        // -64 UPDATE_BAG
        //Service.getInstance().sendMessage(-64, "1630679754666_-64_r");
        // -90 UPDATE_BODY
        //Service.getInstance().sendMessage(-90, "1630679754673_-90_r");
        // -69 MAXSTAMINA
        Service.gI().sendMessage(session, -69, "1630679754701_-69_r");
        // -68 STAMINA
        Service.gI().sendMessage(session, -68, "1630679754708_-68_r");
        // -80 FRIEND
        Service.gI().sendMessage(session, -80, "1630679754715_-80_r");
        // -97 UPDATE_ACTIVEPOINT
        Service.gI().sendMessage(session, -97, "1630679754722_-97_r");
        // -107 PET_INFO
        Service.gI().sendMessage(session, -107, "1630679754733_-107_r");
        // -119 THELUC
        Service.gI().sendMessage(session, -119, "1630679754740_-119_r");
        // -113 CHANGE_ONSKILL
        Service.gI().sendMessage(session, -113, "1630679754747_-113_r");
        // 50 GAME_INFO
        //Service.getInstance().sendMessage(session, 50, "1630679754755_50_r");
        // -30_4 ME_LOAD_INFO
        //Service.getInstance().sendMessage(-68, "1630679754776_-68_r");
        //Service.getInstance().sendMessage(-30, "1630679754782_-30_4_r");
        // -24 MAP_INFO
        //session.player.map.join(session.player);
//        Service.getInstance().mapInfo(session, player);
//        player.place.joinMap(session, player.map);
//        player.place.loadPlayers(session, player.map);
        for (Map map : server.maps) {
            if (map.id != player.map.id) {
                continue;
            }
            for (int i = 0; i < map.area.length; i++) {
                if (map.area[i].players.size() < map.template.maxplayers) {
                    map.area[i].Enter(player);
                    return;
                }
            }
        }
        //Service.getInstance().sendMessage(-24, "1630679754789_-24_r");
        //Service.getInstance().sendMessage(-30, "1630679754795_-30_4_r");
    }

    public void logout(Session session) {
        //tối ưu

        if (session.player != null) {
            // Map map = session.player.map;
            //      PlayerDAO.updateDB(player);
            session.player.timer.cancel();
            session.player.zone.exitMap(session.player);
            session.player.zone.players.remove(session.player);
            session.player.map = null;
            PlayerManger.gI().getPlayers().remove(session.player);

        }

        //old
//        Player player = PlayerManger.gI().getPlayerByUserID(session.userId);
//        if (player != null) {
//            Map map = player.map;
//            //      PlayerDAO.updateDB(player);
//            player.timer.cancel();
//            player.zone.exitMap(player, map);
//            map.getPlayers().remove(player);
//            player.map = null;
//            PlayerManger.gI().getPlayers().remove(player);
    }

}

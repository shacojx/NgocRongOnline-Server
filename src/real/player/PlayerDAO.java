package real.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import real.clan.ClanManager;
import real.item.Item;
import real.item.ItemDAO;
import real.map.Map;
import real.skill.Skill;
import real.skill.SkillData;
import server.DBService;
import server.Manager;
import server.Util;
import server.io.Session;

public class PlayerDAO {

    public static boolean create(Session userId, String name, int gender, int head) {
        String CREATE_PLAYER = "INSERT INTO player(account_id,name,power,vang,luong,luong_khoa,gender,head,where_id,where_x,where_y,limit_power) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        String CREATE_PLAYER_POINT = "INSERT INTO player_point(player_id,hp_goc,mp_goc,dam_goc,def_goc,crit_goc,tiem_nang) VALUES (?,?,?,?,?,?,?)";
        String CREATE_PLAYER_BODY = "INSERT INTO player_body(player_id,item_id,slot) VALUES (?,?,?)";
        String CREATE_PLAYER_BAG = "INSERT INTO player_bag(player_id,item_id,slot) VALUES (?,?,?)";
        String CREATE_PLAYER_BOX = "INSERT INTO player_box(player_id,item_id,slot) VALUES (?,?,?)";
        boolean check = false;
        System.out.println("test1");
        Connection conn = DBService.gI().getConnection();
        ResultSet rss = null;
        int playerid = userId.userId;
        try {
            PreparedStatement ps = null;
            Util.debug("Create User : " + userId.userId);
            ps = conn.prepareStatement(CREATE_PLAYER, Statement.RETURN_GENERATED_KEYS);
            conn.setAutoCommit(false);
            ps.setInt(1, playerid);
            ps.setString(2, name);
            //sức mạnh
            ps.setLong(3, 1200);
            //vàng
            ps.setInt(4, 20000);
            //ngọc
            ps.setInt(5, 20);
            //ngọc tím
            ps.setInt(6, 0);
            ps.setInt(7, gender);
            ps.setInt(8, head);
            ps.setInt(9, gender + 39);
            ps.setInt(10, 180);
            ps.setInt(11, 384);
            ps.setInt(12, 1);
            System.out.println("test");
            if (ps.executeUpdate() == 1) {
                System.out.println("test");
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.first()) {
                    int playerId = rs.getInt(1);
                    ps = conn.prepareStatement(CREATE_PLAYER_POINT);
                    ps.setInt(1, playerId);
                    
                    switch (gender) {
                        case 0:
                            ps.setInt(2, 200);
                            ps.setInt(3, 100);
                            ps.setInt(4, 12);
                            break;
                        case 1:
                            ps.setInt(2, 100);
                            ps.setInt(3, 200);
                            ps.setInt(4, 12);
                            break;
                        case 2:
                            ps.setInt(2, 100);
                            ps.setInt(3, 100);
                            ps.setInt(4, 15);
                            break;
                    }
                    ps.setInt(5, 0);
                    ps.setInt(6, 0);
                    ps.setLong(7, 1200);
                    ps.executeUpdate();
                    ps = conn.prepareStatement(CREATE_PLAYER_BAG);
                    for (int i = 0; i < 20; i++) {
                        ps.setInt(1, playerId);
                        ps.setInt(2, -1);
                        ps.setInt(3, i);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    ps = conn.prepareStatement(CREATE_PLAYER_BOX);
                    for (int i = 0; i < 20; i++) {
                        ps.setInt(1, playerId);
                        ps.setInt(2, -1);
                        ps.setInt(3, i);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    ps = conn.prepareStatement(CREATE_PLAYER_BODY);
                    for (int i = 0; i < 7; i++) {
                        ps.setInt(1, playerId);
                        ps.setInt(2, -1);
                        ps.setInt(3, i);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    check = true;
                }
            }
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public static Player load(int _userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Player player = null;
        try {
            conn = DBService.gI().getConnection();
            ps = conn.prepareStatement("SELECT * FROM player WHERE account_id=? LIMIT 1");
            ps.setInt(1, _userId);
            rs = ps.executeQuery();
            if (rs.first()) {
                player = new Player();
                player.id = rs.getInt("id");
                player.taskId = rs.getByte("task_id");
                player.name = rs.getString("name");
                player.head = rs.getShort("head");
                player.gender = rs.getByte("gender");
                player.nClass = SkillData.nClasss[player.gender];
                player.power = rs.getLong("power");
                player.vang = rs.getInt("vang");
                player.ngoc = rs.getInt("luong");
                player.ngocKhoa = rs.getInt("luong_khoa");
                player.x = rs.getShort("where_x");
                player.y = rs.getShort("where_y");
                if (rs.getInt("clan_id") != -1) {
                    player.clan = ClanManager.gI().getClanById(rs.getInt("clan_id"));
                }
                Map map = Manager.getMapid(rs.getByte("where_id"));
                map.getPlayers().add(player);
                player.map = map;
            }
            ps = conn.prepareStatement("SELECT * FROM player_point WHERE player_id=? LIMIT 1");
            ps.setInt(1, player.id);
            rs = ps.executeQuery();
            if (rs.first()) {
                player.hpGoc = rs.getInt("hp_goc");
                player.mpGoc = rs.getInt("mp_goc");
                player.damGoc = rs.getInt("dam_goc");
                player.defGoc = rs.getShort("def_goc");
                player.critGoc = rs.getByte("crit_goc");
                player.tiemNang = rs.getLong("tiem_nang");
                player.limitPower = rs.getByte("limit_power");
            }
            ps = conn.prepareStatement("SELECT * FROM player_skill WHERE player_id=?");
            ps.setInt(1, player.id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int tempId = rs.getInt("temp_id");
                int level = rs.getInt("level");
                Skill skill = player.nClass.getSkillTemplate(tempId).skills[level - 1];
                player.skill.add(skill);
            }
            conn.close();
        } catch (Exception e) {
        }
        return player;
    }

//    public static void updateDB(Player player) {
//        String UPDATE_PLAYER = "UPDATE player SET power=?,vang=?,luong=?,luong_khoa=?,clan_id=?,task_id=?,head=?,where_id=?,where_x=?,where_y=?,last_logout_time=? WHERE id=?";
//        String UPDATE_PLAYER_POINT = "UPDATE player_point SET hp_goc=?,mp_goc=?,dam_goc=?,def_goc=?,crit_goc=?,tiem_nang=? WHERE player_id=?";
//        String UPDATE_INFO_CLAN = "UPDATE clan_member SET head=?,body=?,leg=? WHERE clan_id=? AND player_id=?";
//        String UPDATE_PLAYER_BODY = "UPDATE player_body SET item_id=? WHERE player_id=? AND slot=?";
//        String UPDATE_PLAYER_BAG = "UPDATE player_bag SET item_id=? WHERE player_id=? AND slot=?";
//        String UPDATE_PLAYER_BOX = "UPDATE player_box SET item_id=? WHERE player_id=? AND slot=?";
//        Connection conn;
//        PreparedStatement ps;
//        try {
//            conn = DBService.gI().getConnection();
//            ps = conn.prepareStatement(UPDATE_PLAYER);
//            ps.setLong(1, player.power);
//            ps.setInt(2, player.vang);
//            ps.setInt(3, player.luong);
//            ps.setInt(4, player.luongKhoa);
//            if (player.clan != null) {
//                ps.setInt(5, player.clan.id);
//            } else {
//                ps.setInt(5, -1);
//            }
//            ps.setInt(6, player.taskId);
//            ps.setInt(7, player.head);
//            ps.setInt(8, player.map.id);
//            ps.setInt(9, player.x);
//            ps.setInt(10, player.y);
//            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            ps.setTimestamp(11, timestamp);
//            ps.setInt(12, player.id);
//            if (ps.executeUpdate() == 1) {
//                Util.log("Update player info " + player.id + " on data base");
//            }
//            ps = conn.prepareStatement(UPDATE_PLAYER_POINT);
//            ps.setInt(1, player.hpGoc);
//            ps.setInt(2, player.mpGoc);
//            ps.setInt(3, player.damGoc);
//            ps.setInt(4, player.defGoc);
//            ps.setInt(5, player.critGoc);
//            ps.setLong(6, player.tiemNang);
//            ps.setInt(7, player.id);
//            if (ps.executeUpdate() == 1) {
//                Util.log("Update player info " + player.id + " on data base");
//            }
//            if (player.clan != null) {
//                ps = conn.prepareStatement(UPDATE_INFO_CLAN);
//                ps.setInt(1, player.getHead());
//                ps.setInt(2, player.getBody());
//                ps.setInt(3, player.getLeg());
//                ps.setInt(4, player.clan.id);
//                ps.setInt(5, player.id);
//                if (ps.executeUpdate() == 1) {
//                    Util.log("Update clan member " + player.id + " on data base");
//                }
//            }

//            ps = conn.prepareStatement(UPDATE_PLAYER_BODY);
//            conn.setAutoCommit(false);
//            for (int i = 0; i < player.ItemBody.length; i++) {
//                Item[] item = player.ItemBody;
//                ps.setInt(1, item.id);
//                ps.setInt(2, player.id);
//                ps.setInt(3, i);
//                ps.addBatch();
//                if (item.id != -1) {
//                    ItemDAO.updateDB(item);
//                }
//            }
//            ps.executeBatch();
//            ps = conn.prepareStatement(UPDATE_PLAYER_BAG);
//            for (int i = 0; i < player.itemsBag.size(); i++) {
//                Item item = player.itemsBag.get(i);
//                ps.setInt(1, item.id);
//                ps.setInt(2, player.id);
//                ps.setInt(3, i);
//                ps.addBatch();
//                if (item.id != -1) {
//                    ItemDAO.updateDB(item);
//                }
//            }
//            ps.executeBatch();
//            ps = conn.prepareStatement(UPDATE_PLAYER_BOX);
//            for (int i = 0; i < player.itemsBox.size(); i++) {
//                Item item = player.itemsBox.get(i);
//                ps.setInt(1, item.id);
//                ps.setInt(2, player.id);
//                ps.setInt(3, i);
//                ps.addBatch();
//                if (item.id != -1) {
//                    ItemDAO.updateDB(item);
//                }
//            }
//            ps.executeBatch();
//            conn.commit();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void updatePlayersInfo(ArrayList<Player> players) {
//        String UPDATE_PLAYER = "UPDATE player SET power=?,vang=?,luong=?,luong_khoa=?,clan_id=?,task_id=?,head=?,body=?,leg=? WHERE id=?";
//        String UPDATE_PLAYER_POINT = "UPDATE player_point SET hp_goc=?,mp_goc=?,dam_goc=?,def_goc=?,crit_goc=?,tiem_nang=? WHERE player_id=?";
//        Connection conn;
//        PreparedStatement ps;
//        try {
//            conn = DBService.gI().getConnection();
//            ps = conn.prepareStatement(UPDATE_PLAYER);
//            for (Player player : players) {
//                ps.setLong(1, player.power);
//                ps.setInt(2, player.vang);
//                ps.setInt(3, player.luong);
//                ps.setInt(4, player.luongKhoa);
//                if (player.clan != null) {
//                    ps.setInt(5, player.clan.id);
//                } else {
//                    ps.setInt(5, -1);
//                }
//                ps.setInt(6, player.taskId);
//                ps.setInt(7, player.head);
//                ps.setInt(8, player.getBody());
//                ps.setInt(9, player.getLeg());
//                ps.setInt(10, player.id);
//                if (ps.executeUpdate() == 1) {
//                    Util.log("Update player info " + player.id + " on data base");
//                }
//                ps = conn.prepareStatement(UPDATE_PLAYER_POINT);
//                ps.setInt(1, player.hpGoc);
//                ps.setInt(2, player.mpGoc);
//                ps.setInt(3, player.damGoc);
//                ps.setInt(4, player.defGoc);
//                ps.setInt(5, player.critGoc);
//                ps.setLong(6, player.tiemNang);
//                ps.setInt(7, player.id);
//                if (ps.executeUpdate() == 1) {
//                    Util.log("Update player info " + player.id + " on data base");
//                }
//                ps.addBatch();
//            }
//            ps.executeBatch();
//            conn.commit();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}

package real.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import server.DBService;

public class MapData {
//    public static int numzone;
//    public static ArrayList<Map> loadMap() {
//        ArrayList<Map> maps = new ArrayList<>();
//        Connection conn = DBService.gI().getConnection();
//        try {
//            PreparedStatement ps = conn.prepareStatement("SELECT * FROM map");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Map map = new Map(rs.getInt("id"));
//                map.name = rs.getString("name");
//                map.type = rs.getByte("type");
//                map.planetId = rs.getByte("planet_id");
//                map.tileId = rs.getByte("tile_id");
//                map.bgId = rs.getByte("bg_id");
//                map.bgType = rs.getByte("bg_type");
//                MapData.numzone = rs.getByte("numzone");
//                map.npcs = loadListNPC(map.id);
//                map.mobs = loadListMob(map.id);
//                map.wayPoints = loadListWayPoint(map.id);
//                maps.add(map);
//            }
//            rs.close();
//            ps.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return maps;
//    }
//
//    public static ArrayList<Npc> loadListNPC(int mapId) {
//        ArrayList<Npc> npcs = new ArrayList<>();
//        Connection conn = DBService.gI().getConnection();
//        try {
//            PreparedStatement ps = conn.prepareStatement("SELECT * FROM map_npc WHERE map_id=?");
//            ps.setInt(1, mapId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Npc npc = new Npc();
//                npc.status = rs.getInt("status");
//                npc.cx = rs.getInt("cx");
//                npc.cy = rs.getInt("cy");
//                npc.tempId = rs.getInt("temp_id");
//                npc.avartar = rs.getInt("avartar");
//                npcs.add(npc);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return npcs;
//    }
//
//    public static ArrayList<Mob> loadListMob(int mapId) {
//        ArrayList<Mob> mobs = new ArrayList<>();
//        Connection conn = DBService.gI().getConnection();
//        try {
//            PreparedStatement ps = conn.prepareStatement("SELECT * FROM map_mob WHERE map_id=?");
//            ps.setInt(1, mapId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Mob mob = new Mob();
//                mob.tempId = rs.getInt("temp_id");
//                mob.level = rs.getByte("level");
//                mob.maxHp = rs.getInt("max_hp");
//                mob.pointX = rs.getShort("point_x");
//                mob.pointY = rs.getShort("point_y");
//                mob.hp = mob.maxHp;
//                mob.status = 5;
//                mobs.add(mob);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return mobs;
//    }

    public static ArrayList<WayPoint> loadListWayPoint(int mapId) {
        ArrayList<WayPoint> wayPoints = new ArrayList<>();
        Connection conn = DBService.gI().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM map_waypoint WHERE map_id=?");
            ps.setInt(1, mapId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WayPoint wp = new WayPoint();
                wp.minX = rs.getShort("min_x");
                wp.minY = rs.getShort("min_y");
                wp.maxX = rs.getShort("max_x");
                wp.maxY = rs.getShort("max_y");
                wp.name = rs.getString("name");
                wp.isEnter = rs.getBoolean("is_enter");
                wp.isOffline = rs.getBoolean("is_offline");
                wp.goMap = rs.getByte("go_map");
                wp.goX = rs.getShort("go_x");
                wp.goY = rs.getShort("go_y"); 
                wayPoints.add(wp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wayPoints;
    }
}

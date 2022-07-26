package real.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import real.player.Player;
import server.DBService;

public class ItemDAO {

    public static void create(int tempId, ArrayList<ItemOption> itemOptions) {
        Connection conn = DBService.gI().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO item(id,quantity) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(2, tempId);
            ps.setInt(3, 1);
            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.first()) {
                    int itemId = rs.getInt(0);
                    ps = conn.prepareStatement("INSERT INTO item(id,option_id,param) VALUES(?,?,?)");
                    conn.setAutoCommit(false);
                    for (ItemOption itemOption : itemOptions) {
                        ps.setInt(1, itemId);
                        ps.setInt(2, itemOption.optionTemplate.id);
                        ps.setInt(3, itemOption.param);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    conn.commit();
                }
            }
            conn.close();
        } catch (Exception e) {
        }
    }

    public static Item load(int itemId) {
        Connection conn = DBService.gI().getConnection();
        Item item = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM item WHERE id=? LIMIT 1");
            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                item = new Item();
                item.quantity = rs.getInt("quantity");
                item.template = ItemTemplates.get(rs.getShort("id"));
            }
            ps = conn.prepareStatement("SELECT * FROM item WHERE id=?");
            ps.setInt(1, itemId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemOption option = new ItemOption();
                option.optionTemplate = ItemData.iOptionTemplates[rs.getInt("option_id")];
                option.param = rs.getShort("param");
                item.itemOptions.add(option);
            }
        } catch (Exception e) {
        }
        return item;
    }

//    public static void loadPlayerItems(Player player) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = DBService.gI().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM player_body LEFT JOIN item ON player_body.item_id=item.id WHERE player_id=? ORDER BY slot");
//            ps.setInt(1, player.id);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                int slot = rs.getInt("slot");
//                Item item = new Item();
//                item.id = rs.getInt("item_id");
//                if (item.id != -1) {
//                    item.template = ItemTemplates.get(rs.getShort("item_id"));
//                    item.quantity = rs.getInt("quantity");
//                    loadOptionsItem(item);
//                }
//                player.itemsBody.add(slot, item);
//            }
//            ps = conn.prepareStatement("SELECT * FROM player_bag LEFT JOIN item ON player_bag.item_id=item.id WHERE player_id=? ORDER BY slot");
//            ps.setInt(1, player.id);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                int slot = rs.getInt("slot");
//                Item item = new Item();
//                item.id = rs.getInt("item_id");
//                if (item.id != -1) {
//                    item.template = ItemTemplates.get(rs.getShort("item_id"));
//                    item.quantity = rs.getInt("quantity");
//                    loadOptionsItem(item);
//                }
//                player.itemsBag.add(slot, item);
//            }
//            ps = conn.prepareStatement("SELECT * FROM player_box LEFT JOIN item ON player_box.item_id=item.id WHERE player_id=? ORDER BY slot");
//            ps.setInt(1, player.id);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                int slot = rs.getInt("slot");
//                Item item = new Item();
//                item.id = rs.getInt("item_id");
//                if (item.id != -1) {
//                    item.template = ItemTemplates.get(rs.getShort("item_id"));
//                    item.quantity = rs.getInt("quantity");
//                    loadOptionsItem(item);
//                }
//                player.itemsBox.add(slot, item);
//            }
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static void loadOptionsItem(Item item) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBService.gI().getConnection();
            ps = conn.prepareStatement("SELECT * FROM item WHERE id=?");
            ps.setInt(1, item.id);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemOption option = new ItemOption();
                option.optionTemplate = ItemData.iOptionTemplates[rs.getInt("option_id")];
                option.param = rs.getInt("param");
                item.itemOptions.add(option);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void updateDB(Item item) {
        try {
            Connection conn = DBService.gI().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM item WHERE id=?");
            ps.setInt(1, item.id);
            if (ps.executeUpdate() == 1) {
                //System.out.println("delete option item " + item.id);
            }
            ps = conn.prepareStatement("UPDATE item SET quantity=? WHERE id=?");
            ps.setInt(1, item.quantity);
            ps.setInt(2, item.id);
            if (ps.executeUpdate() == 1) {
                //System.out.println("delete item " + item.id);
            }
            ps = conn.prepareStatement("INSERT INTO itemn(id,option_id,param) VALUES(?,?,?)");
            conn.setAutoCommit(false);
            for (ItemOption itemOption : item.itemOptions) {
                ps.setInt(1, item.id);
                ps.setInt(2, itemOption.optionTemplate.id);
                ps.setInt(3, itemOption.param);
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

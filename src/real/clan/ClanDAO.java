package real.clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import server.DBService;

public class ClanDAO {

    public static void create(Clan clan) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBService.gI().getConnection();
            ps = conn.prepareStatement("INSERT INTO clan(id, name, leader_id) VALUES(?,?,?)");
            ps.setInt(1, clan.id);
            ps.setString(2, clan.name);
            ps.setInt(3, clan.leaderID);
            ps.executeUpdate();
            ps.close();
            ps = null;
            ps = conn.prepareStatement("INSERT INTO clan_member(clan_id, user_id, role) VALUES(?,?,?)");
            ps.setInt(1, clan.id);
            ps.setInt(2, clan.leaderID);
            ps.setByte(4, (byte) 0);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Clan> load() {
        ArrayList<Clan> clans = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBService.gI().getConnection();
            ps = conn.prepareStatement("SELECT * FROM clan");
            rs = ps.executeQuery();
            while (rs.next()) {
                Clan clan = new Clan();
                clan.id = rs.getInt("id");
                clan.name = rs.getString("name");// name
                clan.slogan = rs.getString("slogan");// slogan
                clan.imgID = rs.getByte("img_id");// img id
                clan.level = rs.getByte("level");// level
                clan.powerPoint = rs.getLong("power_point");// power point
                clan.leaderID = rs.getInt("leader_id");// leader id
                clan.clanPoint = rs.getInt("clan_point");// clan point
                clan.currMember = 1;// curr mem
                clan.maxMember = rs.getByte("max_member");// max mem
                clan.time = rs.getTimestamp("create_time").getTime();// time
                clan.members = getMembers(clan.id);
                clans.add(clan);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clans;
    }

    public static ArrayList<Member> getMembers(int clanId) {
        ArrayList<Member> members = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBService.gI().getConnection();
            ps = conn.prepareStatement("SELECT * FROM clan_member WHERE clan_id=?");
            ps.setInt(1, clanId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.id = rs.getInt("player_id");
                member.role = rs.getByte("role");
                member.donate = rs.getInt("donate");
                member.receiveDonate = rs.getInt("receive_donate");
                member.clanPoint = rs.getInt("clan_point");
                member.currPoint = rs.getInt("curr_point");
                member.joinTime = (int) rs.getTimestamp("join_time").getTime() / 1000;
                members.add(member);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("error getMembers: " + e.toString());
        }
        return members;
    }

//    public static byte getRole(int playerId) {
//        byte role = -1;
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = DBService.gI().getConnection();
//            ps = conn.prepareStatement("SELECT role FROM clan_member WHERE player_id=? LIMIT 1");
//            ps.setInt(1, playerId);
//            rs = ps.executeQuery();
//            if (rs.first()) {
//                role = rs.getByte("role");
//            }
//            rs.close();
//        } catch (Exception e) {
//            System.out.println("error getMembers: " + e.toString());
//        } finally {
//            try {
//                if (ps != null) {
//                    ps.close();
//                    ps = null;
//                }
//                if (conn != null) {
//                    conn.close();
//                    conn = null;
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return role;
//    }
//
//    public static void updateSlogan(int clanId, String slogan) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = DBService.gI().getConnection();
//            ps = conn.prepareStatement("UPDATE clan SET slogan=? WHERE id=? LIMIT 1");
//            ps.setString(1, slogan);
//            ps.setInt(2, clanId);
//            ps.executeUpdate();
//        } catch (Exception ex) {
//            System.out.println(ex);
//        } finally {
//            try {
//                if (ps != null) {
//                    ps.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void changeRole(int playerId, int role) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = DBService.gI().getConnection();
//            ps = conn.prepareStatement("UPDATE clan_member SET role=?,update_time=? WHERE player_id=? LIMIT 1");
//            ps.setInt(1, role);
//            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
//            ps.setInt(3, playerId);
//            ps.executeUpdate();
//        } catch (Exception ex) {
//            System.out.println(ex);
//        } finally {
//            try {
//                if (ps != null) {
//                    ps.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void transferLeader(int clanId, int oldLeaderId, int newLeaderId) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = DBService.gI().getConnection();
//            conn.setAutoCommit(false);
//            ps = conn.prepareStatement("UPDATE clan_member SET role=?,update_time=? WHERE player_id=? LIMIT 1");
//            ps.setInt(1, ClanRole.PRESIDENT.value());
//            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
//            ps.setInt(3, newLeaderId);
//            ps.addBatch();
//            ps.setInt(1, ClanRole.OFFICER.value());
//            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
//            ps.setInt(3, oldLeaderId);
//            ps.addBatch();
//            ps.executeBatch();
//            conn.commit();
//            conn.setAutoCommit(true);
//            ps.close();
//            ps = null;
//            ps = conn.prepareStatement("UPDATE clan SET leader_id=?,update_time=? WHERE id=? LIMIT 1");
//            ps.setInt(1, newLeaderId);
//            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
//            ps.setInt(3, clanId);
//            ps.executeUpdate();
//        } catch (Exception ex) {
//            System.out.println(ex);
//        } finally {
//            try {
//                if (ps != null) {
//                    ps.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void distory(int _guildID) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = DBService.gI().getConnection();
//            ps = conn.prepareStatement("DELETE FROM clan_member WHERE clan_id=?");
//            ps.setInt(1, _guildID);
//            ps.executeUpdate();
//            ps.close();
//            ps = null;
//            ps = conn.prepareStatement("DELETE FROM clan WHERE id=? LIMIT 1");
//            ps.setInt(1, _guildID);
//            ps.executeUpdate();
//        } catch (Exception ex) {
//            System.out.println(ex);
//        } finally {
//            try {
//                if (ps != null) {
//                    ps.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static boolean add(int clanId, int playerId) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = DBService.gI().getConnection();
//            ps = conn.prepareStatement("INSERT INTO clan_member(clan_id, player_id, role, create_time, update_time) VALUES(?,?,?,?,?)");
//            ps.setInt(1, clanId);
//            ps.setInt(2, playerId);
//            ps.setByte(3, ClanRole.NORMAL.value());
//            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
//            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
//            ps.executeUpdate();
//            return true;
//        } catch (Exception ex) {
//            System.out.println(ex);
//        } finally {
//            try {
//                if (ps != null) {
//                    ps.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }

    public static void removeClanMember(int _userID) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBService.gI().getConnection();
            ps = conn.prepareStatement("DELETE FROM clan_member WHERE player_id=? LIMIT 1");
            ps.setInt(1, _userID);
            ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Clan> searchClan(String text) {
        ArrayList<Clan> clans = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBService.gI().getConnection();
            ps = conn.prepareStatement("select * from clan where name like ? limit 10");
            ps.setString(1, text + "%");
            rs = ps.executeQuery();
            if (DBService.resultSize(rs) != 0) {
                while (rs.next()) {
                    Clan clan = new Clan();
                    clan.id = rs.getByte("id");// id
                    clan.name = rs.getString("name");// name
                    clan.slogan = rs.getString("slogan");// slogan
                    clan.imgID = rs.getByte("img_id");// img id
                    clan.powerPoint = rs.getLong("power_point");// power point
                    clan.leaderID = rs.getInt("leader_id");// leader id
                    clan.currMember = 1;// curr mem
                    clan.maxMember = 10;// max mem
                    clan.time = (int) System.currentTimeMillis();// time
                    clans.add(clan);
                }
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("error: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clans;
    }
}

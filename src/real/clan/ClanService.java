package real.clan;

import java.util.ArrayList;
import real.player.Player;
import server.Util;
import server.io.Message;
import server.io.Session;

public class ClanService {

    private static ClanService instance;

    public static ClanService gI() {
        if (instance == null) {
            instance = new ClanService();
        }
        return instance;
    }

    public void clanInfo(Session session, Player pl) {
        Message msg;
        Clan clan = pl.clan;
        try {
            msg = new Message(-53);
            ArrayList<Member> members = clan.members;
            msg.writer().writeInt(clan.id);
            msg.writer().writeUTF(clan.name);
            msg.writer().writeUTF(clan.slogan);
            msg.writer().writeByte(clan.imgID);
            msg.writer().writeUTF(Util.powerToString(clan.powerPoint));
            msg.writer().writeUTF("");//leaderName
            msg.writer().writeByte(members.size());
            msg.writer().writeByte(clan.maxMember);
            msg.writer().writeByte(0);
            msg.writer().writeInt(clan.clanPoint);
            msg.writer().writeByte(clan.level);
            for (Member member : members) {
                msg.writer().writeInt(member.id);
                msg.writer().writeShort(383);
                msg.writer().writeShort(385);
                msg.writer().writeShort(384);
                msg.writer().writeUTF("Gino");//playerName
                msg.writer().writeByte(member.role);
                msg.writer().writeUTF("40,2 Tỉ");
                msg.writer().writeInt(0);
                msg.writer().writeInt(0);
                msg.writer().writeInt(0);
                msg.writer().writeInt(0);
                msg.writer().writeInt(member.joinTime);
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void clanMember(Session session, int clanId) {
        Message msg;
        ArrayList<Member> members = ClanManager.gI().getMemberByIdClan(clanId);
        try {
            msg = new Message(-50);
            msg.writer().writeByte(members.size());
            for (Member member : members) {
                msg.writer().writeInt(member.id);
                msg.writer().writeShort(383);
                msg.writer().writeShort(0);
                msg.writer().writeShort(384);
                msg.writer().writeUTF("Gino Fake");//playerName
                msg.writer().writeByte(member.role);
                msg.writer().writeUTF("40,2 Tỉ");
                msg.writer().writeInt(0);
                msg.writer().writeInt(0);
                msg.writer().writeInt(0);
                msg.writer().writeInt(member.joinTime);
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void searchClan(Session session, String text) {
        Message msg;
        try {
            ArrayList<Clan> clans = ClanManager.gI().search(text);
            msg = new Message(-47);
            msg.writer().writeByte(clans.size());
            for (Clan clan : clans) {
                msg.writer().writeInt(clan.id);
                msg.writer().writeUTF(clan.name);
                msg.writer().writeUTF(clan.slogan);
                msg.writer().writeByte(clan.id);
                String powerPoint = Util.powerToString(clan.powerPoint);
                msg.writer().writeUTF(powerPoint);
                msg.writer().writeUTF("Gino");//leaderName
                //int currMember = ClanDB.getMembers(clan.id).size();
                msg.writer().writeByte(1);
                msg.writer().writeByte(clan.maxMember);
                msg.writer().writeInt((int) clan.time);
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            System.out.println("-47 " + e.toString());
        }
    }
}

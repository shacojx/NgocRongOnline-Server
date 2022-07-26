package real.clan;

import java.util.ArrayList;

public class Clan {

    public int id;

    public String name;

    public String slogan;

    public byte imgID;

    public byte level;

    public int clanPoint;

    public long powerPoint;

    public int leaderID;

    public byte currMember;

    public byte maxMember;

    public long time;

    public ArrayList<Member> members;

    public Clan() {
        this.members = new ArrayList<>();
    }

}

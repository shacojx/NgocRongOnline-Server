package real.map;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import real.player.Player;
import server.GameScr;
import server.Util;

public final class Map{
    public int id;
    public byte planetId;
    public byte tileId;
    public byte bgId;
    public byte bgType;
    public byte type;
    public String name;
    public ArrayList<Player> players;
    public ArrayList<WayPoint> wayPoints;
    public ArrayList<Npc> npcs;
    public ArrayList<Mob> mobs;
    public ArrayList<ItemMap> items;
    public MapTemplate template;
    public Zone[] area;
    private boolean runing;
    private Object LOCK;
    private Thread threadUpdate;
    private class RunPlace implements Runnable {
        public RunPlace(){};
        public void run() {
            long l1;
            long l2;
            while (Map.this.runing) {
                try {
                    l1 = System.currentTimeMillis();
                    Map.this.update();
                    l2 = System.currentTimeMillis() - l1;
                    Thread.sleep(Math.abs(500L - l2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
    }
    public Map(MapTemplate mapTemplate) {
        this.LOCK = new Object();
        this.id = mapTemplate.id;
        this.template = mapTemplate;
        this.area = new Zone[mapTemplate.numarea];
        for (byte i = 0; i < this.template.numarea; ++i) {
            this.area[i] = new Zone(this, i);
        }
        this.players = new ArrayList<>();
        this.wayPoints = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.mobs = new ArrayList<>();
        this.items = new ArrayList<>();
        this.initMob();
        this.threadUpdate = new Thread(new RunPlace());
    }
    public void initMob() {
        for (byte j = 0; j < area.length; j++) {
            area[j].mobs.clear();
            int k = 0;
            for (short i = 0; i < this.template.arMobid.length; i++) {
                Mob m = new Mob(k, this.template.arMobid[i], 10);
                m.level = this.template.arrMoblevel[i];
                m.pointX = this.template.arrMobx[i];
                m.pointY = this.template.arrMoby[i];
                m.maxHp = this.template.arrMaxhp[i];
                m.hp = m.maxHp;
                m.status = 5;
                if (m.status== 3) {
                    if ( j%5==0) {
                        m.hp = m.maxHp *= 200;
                    } else {
                        m.status = 0;
                    }
                } else if (m.status== 2) {
                    m.hp = m.maxHp *= 100;
                } else if (m.status == 1) {
                    m.hp = m.maxHp *= 10;
                }
                area[j].mobs.add(m);
                k++;
     //           Util.debug("Mob " + area[j].mobs );
            }
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getPlanetId() {
        return planetId;
    }

    public void setPlanetId(byte planetId) {
        this.planetId = planetId;
    }

    public byte getBgId() {
        return bgId;
    }

    public void setBgId(byte bgId) {
        this.bgId = bgId;
    }

    public byte getBgType() {
        return bgType;
    }

    public void setBgType(byte bgType) {
        this.bgType = bgType;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(ArrayList<WayPoint> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public ArrayList<Npc> getNpcs() {
        return npcs;
    }

    public void setNpcs(ArrayList<Npc> npcs) {
        this.npcs = npcs;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public void setMobs(ArrayList<Mob> mobs) {
        this.mobs = mobs;
    }

    public ArrayList<ItemMap> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemMap> items) {
        this.items = items;
    }
    public void loadMapFromResource() {
//        if (this.id == 0 || this.id == 56 || (this.id > 72 && this.id < 125) || (this.id > 125 && this.id < 133) ||
//                (this.id > 133 && this.id < 139) || this.id > 148) {
//            return;
//        }
        ByteArrayInputStream bai = null;
        DataInputStream dis = null;
        try {
            byte[] ab = GameScr.loadFile("res/map/" + this.id).toByteArray();
            bai = new ByteArrayInputStream(ab);
            dis = new DataInputStream(bai);
            this.template.tmw = this.ushort((short)dis.read());
            this.template.tmh = this.ushort((short)dis.read());
            this.template.maps = new char[dis.available()];
            int i;
            for (i = 0; i < this.template.tmw * this.template.tmh; i++)
                this.template.maps[i] = (char)dis.readByte();
            this.template.types = new int[this.template.maps.length];
            if(dis != null) {
                dis.close();
            }
            if(bai != null) {
                bai.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void update() {
        byte i;
        for (i = 0; i < this.area.length; ++i) {
            if(this.area[i] != null) {
                this.area[i].update();
            }
        }
    }

    public void start() {
        if (this.runing) {
            this.close();
        }
        this.runing = true;
        this.threadUpdate.start();
    }
    public int ushort(short s) {
        return s & 0xFFFF;
    }
    public void close() {
        this.runing = false;
        byte i;
        for (i = 0; i < this.area.length; ++i) {
            if(this.area[i] != null) {
                this.area[i].close();
                this.area[i] = null;
            }
        }
        this.threadUpdate = null;
        this.template = null;
        this.LOCK = null;
    }
}

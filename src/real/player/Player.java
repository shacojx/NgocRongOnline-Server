package real.player;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import real.clan.Clan;
import real.clan.ClanManager;
import real.item.Item;
import static real.item.ItemDAO.loadOptionsItem;
import real.item.ItemOption;
import real.item.ItemSell;
import real.item.ItemTemplate;
import real.item.ItemTemplates;

import real.item.useItem;
import real.map.Map;
import real.map.Zone;
import real.map.WayPoint;
import real.skill.NClass;
import real.skill.Skill;
import real.skill.SkillData;
import server.Manager;
import server.SQLManager;
import server.Server;
import server.Service;
import server.Util;
import server.io.Message;
import server.io.Session;

public class Player {

    public Zone zone = null;
    public Session session;
    public int id;
    public Map map;
    public int menuNPCID = -1;
    public int menuID = -1;
    public short x;
    public short y;
    public String name;
    public short taskId;
    public byte taskIndex;
    public byte gender;
    public short head;
    public short body;
    public short leg;
    public long power;
    public int vang;
    public int ngocKhoa;
    public int ngoc;
    public int hpGoc;
    public int mpGoc;
    public int hp;
    public int mp;
    public int damGoc;
    public short defGoc;
    public byte critGoc;
    public byte typePk;
    public byte limitPower;
    public long tiemNang;
    public NClass nClass;
    public Clan clan;
    public Skill selectSkill;
    public byte[] KSkill = null;
    public byte[] OSkill = null;
    public short CSkill = -1;
    public byte maxluggage = 30;
    public byte levelBag = 0;
    public Item[] ItemBag = null;
    public Item[] ItemBox = null;
    public Item[] ItemBody = null;
    public ArrayList<Player> nearPlayers;
    public int mobAtk = -1;
    public Timer timer;
    public boolean isdie = false;
    public byte cPk = 0;
    public ArrayList<Skill> skill;
    public long CSkilldelay = 0;
    public static short[][] infoId = {{281, 361, 351}, {512, 513, 536}, {514, 515, 537}};
    public Detu detu;
    public byte petfucus = 0;
    public Player() {
        this.ItemBag = null;
        this.ItemBody = null;
        this.ItemBox = null;
        this.nearPlayers = new ArrayList<>();
        this.skill = new ArrayList<>();
        this.timer = new Timer();
        this.body = -1;
        this.leg = -1;
    }

    public Zone getPlace() {
        return zone;
    }

    public void active() {
        this.timer = new Timer();
        this.timer.schedule(new PlayerTask(), 10000L, 30000L);
    }

    public Skill getSkill(int id) {
        for (Skill skl : this.skill) {
            if (skl.skillId == id) {
                return skl;
            }
        }
        return null;
    }

    public void gotoMap(Map _map) {
        if (this.map != null && _map != null) {
            this.map.getPlayers().remove(this);
            if (this.map != _map) {
                //send msg exit to player in map
                this.zone.exitMap(this);
            }
            this.map = _map;
            this.map.getPlayers().add(this);
        }
    }

    public short getHead() {
        return head;
    }

    public short getDefaultBody() {
        if (this.gender == 0) {
            return 57;
        } else if (this.gender == 1) {
            return 59;
        } else if (this.gender == 2) {
            return 57;
        }
        return -1;
    }

    public short getDefaultLeg() {
        if (this.gender == 0) {
            return 58;
        } else if (this.gender == 1) {
            return 60;
        } else if (this.gender == 2) {
            return 58;
        }
        return -1;
    }

    public short PartHead() {

        if (this.ItemBody[5] == null) {
            return head;
        }
        return ItemTemplate.ItemTemplateID(this.ItemBody[5].id).part;
    }

    public  short PartBody() {
        if (this.ItemBody[0] == null) {
            return body;
        }
        return ItemTemplate.ItemTemplateID(this.ItemBody[0].id).part;
    }

    public short Leg() {
        if (this.ItemBody[1] == null) {
            return leg;
        }
        return ItemTemplate.ItemTemplateID(this.ItemBody[1].id).part;
    }

    public int getMount() {
        for (Item item : ItemBag) {
            if (item != null && item.id != -1) {
                if (item.template.type == 23 || item.template.type == 24) {
                    return item.template.id;
                }
            }
        }
        return -1;
    }

    public int getHpFull() {
        return hpGoc;
    }

    public int getMpFull() {
        return mpGoc;
    }

    public int getDamFull() {
        return damGoc;
    }

    public short getDefFull() {
        return defGoc;
    }

    public byte getCritFull() {
        return critGoc;
    }

    public byte getSpeed() {
        return 7;
    }

    public void updateVangNgocHPMP() {
        Message m;
        try {
            m = new Message(-30);
            m.writer().writeByte(4);
            m.writer().writeInt(this.vang);
            m.writer().writeInt(this.ngoc);
            m.writer().writeByte(this.hp);
            m.writer().writeByte(this.mp);
            m.writer().writeInt(this.ngocKhoa);
            this.session.sendMessage(m);
            m.cleanup();
        } catch (Exception e) {
        }

    }

    public void updateItemBag() {
        Message msg;
        try {
            Item[] itemsBody = this.ItemBag;
            msg = new Message(-36);
            msg.writer().writeByte(0);
            msg.writer().writeByte(itemsBody.length);
            for (Item item : itemsBody) {
                if (item == null) {
                    msg.writer().writeShort(-1);
                } else {

                    System.out.println("----Item----");
                    System.out.println("item.template.id: " + item.template.id);
                    System.out.println("quantity: " + item.quantity);
                    System.out.println("getInfo: " + item.getInfo());
                    System.out.println("getContent: " + item.getContent());
                    System.out.println("item.itemOptions.size(): " + item.itemOptions.size());

                    msg.writer().writeShort(item.template.id);
                    msg.writer().writeInt(item.quantity);
                    msg.writer().writeUTF(item.getInfo());
                    msg.writer().writeUTF(item.getContent());
                    msg.writer().writeByte(item.itemOptions.size());
                    for (ItemOption itemOption : item.itemOptions) {
                        msg.writer().writeByte(itemOption.optionTemplate.id);
                        msg.writer().writeShort(itemOption.param);
                    }
                }

            }
            this.session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            System.out.print("-36 ");
            e.printStackTrace();
        }
    }

    public byte getBoxNull() {
        for (byte i = 0; i < this.ItemBox.length; ++i) {
            if (this.ItemBox[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public byte getBagNull() {
        for (byte i = 0; i < this.ItemBag.length; ++i) {
            if (this.ItemBag[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public Item getIndexBag(final int index) {
        if (index < this.ItemBag.length && index >= 0) {
            return this.ItemBag[index];
        }
        return null;
    }

    public Item getIndexBox(final int index) {
        if (index < this.ItemBox.length && index >= 0) {
            return this.ItemBox[index];
        }
        return null;
    }

    protected Item getItemIdBag(final int id) {
        for (int i = 0; i < this.ItemBag.length; ++i) {
            final Item item = this.ItemBag[i];
            if (item != null && item.id == id) {
                return item;
            }
        }
        return null;
    }

    public int getIndexBagid(final int id) {
        for (int i = 0; i < this.ItemBag.length; ++i) {
            final Item item = this.ItemBag[i];
            if (item != null && item.id == id) {
                return i;
            }
        }
        return -1;
    }

    public byte getIndexBoxid(final int id) {
        for (byte i = 0; i < this.ItemBox.length; ++i) {
            final Item item = this.ItemBox[i];
            if (item != null && item.id == id) {
                return i;
            }
        }
        return -1;
    }

    protected int getIndexBagItem(final int id) {
        for (int i = 0; i < this.ItemBag.length; ++i) {
            final Item item = this.ItemBag[i];
            if (item != null && item.id == id) {
                return i;
            }
        }
        return -1;
    }

    public int getIndexBagNotItem() {
        for (int i = 0; i < this.ItemBag.length; ++i) {
            final Item item = this.ItemBag[i];
            if (item == null) {
                return i;
            }
        }
        return -1;
    }

    protected byte getIndexBoxNotItem() {
        for (byte i = 0; i < this.ItemBox.length; ++i) {
            final Item item = this.ItemBox[i];
            if (item == null) {
                return i;
            }
        }
        return -1;
    }

    protected byte getIndexBody() {
        for (byte i = 0; i < this.ItemBody.length; ++i) {
            final Item item = this.ItemBody[i];
            if (item == null) {
                return i;
            }
        }
        return -1;
    }

    public byte getAvailableBag() {
        byte num = 0;
        for (int i = 0; i < this.ItemBag.length; ++i) {
            if (this.ItemBag[i] == null) {
                ++num;
            }
        }
        return num;
    }

    public Boolean addItemToBag(Item item) {
        if (item == null) {
            return false;
        }
        for (int i = 0; i < this.ItemBag.length; i++) {
            if (this.ItemBag[i] != null) {
                continue;
            }
            this.ItemBag[i] = item;
            return true;
        }
        this.sendAddchatYellow("Hành trang không đủ chỗ trống");
        return false;

    }

    public void itemBagToBox(Message m) throws IOException {
        final byte index = m.reader().readByte();
        m.cleanup();
        final Item item = this.getIndexBag(index);
        if (item == null) {
            return;
        }
        final ItemTemplate data = ItemTemplate.ItemTemplateID(item.id);
        byte indexBox = this.getIndexBoxid(item.id);
        if (!item.isExpires && data.isUpToUp && indexBox != -1) {
            this.ItemBag[index] = null;
            final Item item2 = this.ItemBox[indexBox];
            item2.quantity += item.quantity;
        } else {
            if (this.getBoxNull() <= 0) {
                this.sendAddchatYellow("Rương đồ không đủ chỗ trống");
                return;
            }
            indexBox = this.getIndexBoxNotItem();
            this.ItemBag[index] = null;
            this.ItemBox[indexBox] = item;
        }
        m = new Message(4);
        m.writer().writeByte(index);
        m.writer().writeByte(indexBox);
        m.writer().flush();
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void itemBoxToBag(Message m) throws IOException {
        final byte index = m.reader().readByte();
        m.cleanup();
        final Item item = this.getIndexBox(index);
        if (item == null) {
            return;
        }
        final ItemTemplate data = ItemTemplate.ItemTemplateID(item.id);
        int indexBag = this.getIndexBagid(item.id);
        if (indexBag != -1) {
            this.ItemBox[index] = null;
            final Item item2 = this.ItemBag[indexBag];
            item2.quantity += item.quantity;
        } else {
            if (this.getAvailableBag() <= 0) {
                this.sendAddchatYellow("Rương đồ không đủ chỗ trống");
                return;
            }
            indexBag = this.getIndexBagNotItem();
            this.ItemBox[index] = null;
            this.ItemBag[indexBag] = item;
        }
        m = new Message(5);
        m.writer().writeByte(index);
        m.writer().writeByte(indexBag);
        m.writer().flush();
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void useItemBody(Item item, short indexItemBag) {
        int index = -1;
        if (item.id != -1) {
            if (item.template.gender == 1 || item.template.gender == 0 || item.template.gender == 2 || item.template.gender == 3) {
                //  if (item.template.strRequire >= this.power) {
                if (item.template.type >= 0 && item.template.type <= 11) {
                    index = item.template.type;
                }
                if (item.template.type == 32) {
                    index = 6;
                }
                if (item.template.type == 5) {
                    this.head = item.template.part;
                }
//                } else {
//                    Service.getInstance().serverMessage(this.session, "Sức mạnh không đủ yêu cầu");
//                }
            } else {
                Service.gI().serverMessage(this.session, "Sai hành tinh");
            }
        }
        if (index != -1) {
            if (this.ItemBody[index] != null) {
                this.ItemBag[indexItemBag] = this.ItemBody[index];
            } else {
                this.ItemBag[indexItemBag] = null;
            }

            this.ItemBody[index] = item;

        }
    }

    public void itemBodyToBag(int index) {
        Item itemInBody = this.ItemBody[index];
        byte indexNull = this.getBagNull();
        if (indexNull == -1) {
            sendAddchatYellow("Hành trang không còn chổ trống");
            return;
        }
        //Item item = this.ItemBag[i];
        if (indexNull != -1) {
            this.ItemBag[indexNull] = itemInBody;
            this.ItemBody[index] = null;
            Service.gI().updateItemBody(this);
            Service.gI().updateItemBag(this);
        }
//        for (int i = 0; i < this.ItemBody.length; i++) {
//            byte intexNull = this.getBagNull();
//            if(intexNull == -1)
//            {
//                sendAddchatYellow("Hành trang không còn chổ trống");
//                return;
//            }
//            //Item item = this.ItemBag[i];
//            if (this.ItemBag[intexNull].id != -1) {
//                this.ItemBag[intexNull] = _item;
//                removeItemBody(index);
//                Service.getInstance().updateItemBody(session, this);
//                Service.getInstance().updateItemBag(session, this);
//                break;
//            }
//        }
    }

    public void itemBagToBody(byte index) throws IOException {
        Item item = this.ItemBag[index];
        useItemBody(item, index);
        //removeItemBag(index, item.quantity);
        Service.gI().updateItemBag(this);
        Service.gI().updateItemBody(this);
    }

    public void removeItemBag(int index) throws IOException {
        Message m;

        this.ItemBag[index] = null;
        m = new Message(-30);
        m.writer().writeByte(69);
        m.writer().writeByte(index);
        m.writer().flush();
        this.session.sendMessage(m);
        m.cleanup();

//        
//        Message m;
//        Item item = new Item();
//        item.id = -1;
//        this.ItemBag[index].id = -1;
//        m = new Message(-30);
//        m.writer().writeByte(69);
//        m.writer().writeByte(index);
//        m.writer().flush();
//        this.session.sendMessage(m);
//        m.cleanup();
    }

    public void loadBox() throws IOException {
        Message m;
        m = new Message(-35);
        m.writer().writeByte(0);
        m.writer().writeByte(this.ItemBox.length);
        for (int i = 0; i < this.ItemBox.length; i++) {
            m.writer().writeShort(this.ItemBox[i].id);
            m.writer().writeInt(this.ItemBox[i].quantity);
            m.writer().writeUTF(this.ItemBox[i].getInfo());
            m.writer().writeUTF(this.ItemBox[i].getContent());
            m.writer().writeByte(this.ItemBox[i].itemOptions.size());
            for (int j = 0; j < this.ItemBox[i].itemOptions.size(); j++) {
                m.writer().writeByte(0);
                m.writer().writeShort(0);
            }
        }
        this.session.sendMessage(m);
        m.cleanup();
    }

    //type 0 SM, 1 TN, 2 SM TN
    public void UpdateSMTN(byte type, long amount) {
        Message msg;
        try {
            msg = new Message(-3);
            msg.writer().writeByte(type);
            msg.writer().writeInt((int) (amount > Integer.MAX_VALUE ? Integer.MAX_VALUE : amount));
            this.session.sendMessage(msg);
            System.out.println("send done");
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    //mở rương
    public void openBox() throws IOException {
        Message m;
        m = new Message(-35);
        m.writer().writeByte(1);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public synchronized void removeItemBag(byte index, int quantity) {
        Item item = getIndexBag(index);
        try {
            item.quantity -= quantity;
            Message m = new Message(69);
            m.writer().writeByte(index);
            m.writer().writeShort(quantity);
            m.writer().flush();
            this.session.sendMessage(m);
            m.cleanup();
            if (item.quantity <= 0) {
                this.ItemBag[index] = null;
            }
        } catch (IOException iOException) {
        }
    }
//    public static void useItem(Player p, Message m) {
//        try {
//            if(p != null && p.session != null && m != null && m.reader().available() > 0) {
////                if(p.c.getEffId(16) != null) {
////                    p.removeEffect(16);
////                }
//                byte itemAction = m.reader().readByte();
//                byte where = m.reader().readByte();
//	        byte index = m.reader().readByte();
//		short itemID = m.reader().readShort();
//                m.cleanup();
//                Item item = p.ItemBody[index];
//                for(int i = 0; i< p.ItemBody.length;i++){
//                    where = (byte) p.ItemBody[i].id;
//                }
//                
//                if (item != null) {
//                 UseItem.uesItem(p, itemAction, where,index);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if(m != null) {
//                m.cleanup();
//            }
//        }
//    }

    public void removeItemBody(int index) {
        Message m = null;
        try {
            this.ItemBody[index] = null;
//            if (index == 10) {
//                this.p.mobMeMessage(0, (byte)0);
//            }
//            m = new Message(-30);
//            m.writer().writeByte(-80);
//            m.writer().writeByte(index);
//            m.writer().flush();
//            this.session.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (m != null) {
                m.cleanup();
            }
        }

    }
//    

    public void removeItemBox(int index) {
        Item item = new Item();
        item.id = -1;
        this.ItemBox[index].id = -1;
    }

    public void increasePoint(byte type, short point) {
        if (point <= 0) {
            return;
        }
        long tiemNangUse = 0;
        if (type == 0) {
            int pointHp = point * 20;
            tiemNangUse = point * (2 * (this.hpGoc + 1000) + pointHp - 20) / 2;
            System.out.println("debug HP =" + (this.hpGoc + pointHp));
            if ((this.hpGoc + pointHp) <= getHpMpLimit()) {
                if (useTiemNang(tiemNangUse)) {
                    hpGoc += pointHp;
                }
            } else {
                Service.gI().serverMessage(this.session, "Vui lòng mở giới hạn sức mạnh");
                return;
            }
        }
        if (type == 1) {
            int pointMp = point * 20;
            tiemNangUse = point * (2 * (this.mpGoc + 1000) + pointMp - 20) / 2;
            if ((this.mpGoc + pointMp) <= getHpMpLimit()) {
                if (useTiemNang(tiemNangUse)) {
                    mpGoc += pointMp;
                }
            } else {
                Service.gI().serverMessage(this.session, "Vui lòng mở giới hạn sức mạnh");
                return;
            }
        }
        if (type == 2) {
            tiemNangUse = point * (2 * this.damGoc + point - 1) / 2 * 100;
            if ((this.damGoc + point) <= getDamLimit()) {
                if (useTiemNang(tiemNangUse)) {
                    damGoc += point;
                }
            } else {
                Service.gI().serverMessage(this.session, "Vui lòng mở giới hạn sức mạnh");
                return;
            }
        }
        if (type == 3) {
            tiemNangUse = 2 * (this.defGoc + 5) / 2 * 100000;
            if ((this.defGoc + point) <= getDefLimit()) {
                if (useTiemNang(tiemNangUse)) {
                    defGoc += point;
                }
            } else {
                Service.gI().serverMessage(this.session, "Vui lòng mở giới hạn sức mạnh");
                return;
            }
        }
        if (type == 4) {
            tiemNangUse = 50000000L;
            for (int i = 0; i < this.critGoc; i++) {
                tiemNangUse *= 5L;
            }
            if ((this.critGoc + point) <= getCrifLimit()) {
                if (useTiemNang(tiemNangUse)) {
                    critGoc += point;
                }
            } else {
                Service.gI().serverMessage(this.session, "Vui lòng mở giới hạn sức mạnh");
                return;
            }
        }
        Service.gI().loadPoint(this.session, this);
    }

    public boolean useTiemNang(long tiemNang) {
        if (this.tiemNang < tiemNang) {
            Service.gI().serverMessage(this.session, "Bạn không đủ tiềm năng");
            return false;
        }
        if (this.tiemNang >= tiemNang) {
            this.tiemNang -= tiemNang;
            return true;
        }
        return false;
    }

    public int getHpMpLimit() {
        if (limitPower == 0) {
            return 220000;
        }
        if (limitPower == 1) {
            return 240000;
        }
        if (limitPower == 2) {
            return 300000;
        }
        if (limitPower == 3) {
            return 350000;
        }
        if (limitPower == 4) {
            return 400000;
        }
        if (limitPower == 5) {
            return 450000;
        }
        return 0;
    }

    public int getDamLimit() {
        if (limitPower == 0) {
            return 11000;
        }
        if (limitPower == 1) {
            return 12000;
        }
        if (limitPower == 2) {
            return 15000;
        }
        if (limitPower == 3) {
            return 18000;
        }
        if (limitPower == 4) {
            return 20000;
        }
        if (limitPower == 5) {
            return 22000;
        }
        return 0;
    }

    public short getDefLimit() {
        if (limitPower == 0) {
            return 550;
        }
        if (limitPower == 1) {
            return 600;
        }
        if (limitPower == 2) {
            return 700;
        }
        if (limitPower == 3) {
            return 800;
        }
        if (limitPower == 4) {
            return 100;
        }
        if (limitPower == 5) {
            return 22000;
        }
        return 0;
    }

    public byte getCrifLimit() {
        if (limitPower == 0) {
            return 5;
        }
        if (limitPower == 1) {
            return 6;
        }
        if (limitPower == 2) {
            return 7;
        }
        if (limitPower == 3) {
            return 8;
        }
        if (limitPower == 4) {
            return 9;
        }
        if (limitPower == 5) {
            return 10;
        }
        return 0;
    }

    public void move(short _toX, short _toY) {
        if (_toX != this.x) {
            this.x = _toX;
        }
        if (_toY != this.y) {
            this.y = _toY;
        }
        this.zone.playerMove(this);
    }

    public WayPoint isInWaypoint() {
        for (WayPoint wp : map.wayPoints) {
            if (x >= wp.minX && x <= wp.maxX && y >= wp.minY && y <= wp.maxY) {
                return wp;
            }
        }
        return null;
    }

    public void update() {
        if (this.hp < this.getHpFull()) {
            this.hp = this.getHpFull();
            this.mp = this.getMpFull();
            Service.gI().loadPoint(this.session, this);
        }
    }

    class PlayerTask extends TimerTask {

        @Override
        public void run() {
            Player.this.update();
        }

    }

    public void sortBag() throws IOException {
        try {
            int i;
            for (i = 0; i < ItemBag.length; i = (i + 1)) {
                if (ItemBag[i] != null && !(ItemBag[i]).isExpires && (ItemTemplate.ItemTemplateID(ItemBag[i].id)).isUpToUp) {
                    for (int j = (i + 1); j < ItemBag.length; j = (j + 1)) {
                        if (ItemBag[j] != null && !(ItemBag[i]).isExpires && (ItemBag[j]).id == (ItemBag[i]).id) {
                            (ItemBag[i]).quantity += (ItemBag[j]).quantity;
                            ItemBag[j] = null;
                        }
                    }
                }
            }

            for (i = 0; i < ItemBag.length; i = (i + 1)) {
                if (ItemBag[i] == null) {
                    for (int j = (i + 1); j < ItemBag.length; j = j + 1) {
                        if (ItemBag[j] != null) {
                            ItemBag[i] = ItemBag[j];
                            ItemBag[j] = null;
                            break;
                        }
                    }
                }
            }
        } catch (Exception exception) {
        }
        final Message m = new Message(-30);
        m.writer().writeByte(18);
        m.writer().flush();
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void sortBox() throws IOException {
        for (byte i = 0; i < this.ItemBox.length; ++i) {
            if (this.ItemBox[i] != null && !this.ItemBox[i].isExpires && ItemTemplate.ItemTemplateID(this.ItemBox[i].id).isUpToUp) {
                for (byte j = (byte) (i + 1); j < this.ItemBox.length; ++j) {
                    if (this.ItemBox[j] != null && !this.ItemBox[i].isExpires && this.ItemBox[j].id == this.ItemBox[i].id) {
                        final Item item = this.ItemBox[i];
                        item.quantity += this.ItemBox[j].quantity;
                        this.ItemBox[j] = null;
                    }
                }
            }
        }
        for (byte i = 0; i < this.ItemBox.length; ++i) {
            if (this.ItemBox[i] == null) {
                for (byte j = (byte) (i + 1); j < this.ItemBox.length; ++j) {
                    if (this.ItemBox[j] != null) {
                        this.ItemBox[i] = this.ItemBox[j];
                        this.ItemBox[j] = null;
                        break;
                    }
                }
            }
        }
        final Message m = new Message(-30);
        m.writer().writeByte(19);
        m.writer().flush();
        this.session.sendMessage(m);
        m.cleanup();
    }

    //chim thông báo
    public void sendAddchatYellow(String str) {
        try {
            Message m = new Message(-25);
            m.writer().writeUTF(str);
            m.writer().flush();
            session.sendMessage(m);
            m.cleanup();
        } catch (IOException ex) {
        }
    }
    public void requestItem(int typeUI) throws IOException {
        Message m = new Message(23);
        m.writer().writeByte(typeUI);
        m.writer().flush();
        session.sendMessage(m);
        m.cleanup();
    }
    public static Player setup(int account_id) {
        try {
            synchronized (Server.LOCK_MYSQL) {
                ResultSet rs = SQLManager.stat.executeQuery("SELECT * FROM `player` WHERE `account_id`LIKE'" + account_id + "';");
                if (rs != null && rs.first()) {
                    Player player = new Player();
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
                    player.hpGoc = rs.getInt("hp_goc");
                    player.mpGoc = rs.getInt("mp_goc");
                    player.hp = player.hpGoc;
                    player.mp = player.mpGoc;
                    player.damGoc = rs.getInt("dame_goc");
                    player.defGoc = rs.getShort("def_goc");
                    player.critGoc = rs.getByte("crit_goc");
                    player.tiemNang = rs.getLong("tiem_nang");
                    player.limitPower = rs.getByte("limit_power");
                    JSONArray jar = (JSONArray) JSONValue.parse(rs.getString("skill"));
                    JSONObject job;
                    Skill skill;
                    byte index;
                    if (jar != null) {
                        job = null;
                        for (index = 0; index < jar.size(); ++index) {
                            job = (JSONObject) jar.get(index);
                            skill = new Skill();
                            skill.skillId = Byte.parseByte(job.get("id").toString());
                            skill.point = Byte.parseByte(job.get("point").toString());
                            player.skill.add(skill);
                            job.clear();
                        }
                    }
                    player.maxluggage = rs.getByte("maxluggage");
                    player.levelBag = rs.getByte("levelBag");
                    JSONObject job2 = null;
                    player.ItemBag = new Item[player.maxluggage];
                    jar = (JSONArray) JSONValue.parse(rs.getString("ItemBag"));
                    int j;
                    if (jar != null) {
                        for (j = 0; j < jar.size(); ++j) {
                            job2 = (JSONObject) jar.get(j);
                            index = Byte.parseByte(job2.get("index").toString());
                            player.ItemBag[index] = ItemTemplate.parseItem(jar.get(j).toString());
                            job2.clear();
                        }
                    }

                    player.ItemBox = new Item[30];
                    jar = (JSONArray) JSONValue.parse(rs.getString("ItemBox"));
                    if (jar != null) {
                        for (j = 0; j < jar.size(); ++j) {
                            job2 = (JSONObject) jar.get(j);
                            index = Byte.parseByte(job2.get("index").toString());
                            player.ItemBox[index] = ItemTemplate.parseItem(jar.get(j).toString());
                            job2.clear();
                        }
                    }

                    player.ItemBody = new Item[6];
                    jar = (JSONArray) JSONValue.parse(rs.getString("ItemBody"));
                    if (jar != null) {
                        for (j = 0; j < jar.size(); ++j) {
                            job2 = (JSONObject) jar.get(j);
                            index = Byte.parseByte(job2.get("index").toString());
                            player.ItemBody[index] = ItemTemplate.parseItem(jar.get(j).toString());
                            job2.clear();
                        }
                    }
                    rs.close();
                    return player;
//                    } else {
//                        Client.gI().kickSession(nja.p.conn);
//                        return null;
                    //       }
                } else {
                    return null;
                }
            }
        } catch (Exception var23) {
            var23.printStackTrace();
            return null;
        }
    }

}

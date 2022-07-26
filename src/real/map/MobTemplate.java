package real.map;

import java.util.ArrayList;

public class MobTemplate {
    public int tempId;

    public byte level;

    public int hp;

    public int maxHp;

    public short pointX;

    public short pointY;
    
    public byte status;
    public byte rangeMove;
    public byte speed;
    public String name;
    public static ArrayList<MobTemplate> entrys = new ArrayList<MobTemplate>();

    public static MobTemplate getMob(int id) {
        for (MobTemplate mob : MobTemplate.entrys) {
            if (mob.tempId == id) {
                return mob;
            }
        }
        return null;
    }
}

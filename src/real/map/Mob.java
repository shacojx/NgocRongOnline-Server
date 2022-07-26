package real.map;

import java.util.ArrayList;
import java.util.HashMap;

public class Mob {

    public int tempId;
    public int level;
    public int hp;
    public int maxHp;
    public short pointX;
    public short pointY;
    public long xpup;
    public byte status;
    public boolean isDie;
    public boolean isRefresh = true;
    public long timeRefresh;
    public MobTemplate template;
    public Mob(int id,int idtemplate, int level) {
        this.tempId = id;
        this.template = MobTemplate.entrys.get(idtemplate);
        this.level = level;
        this.hp = maxHp = template.maxHp;
        this.xpup = 100000;
        this.isDie = false;
        this.isRefresh = true;
        this.level = level;
        this.xpup = 10000L;
        this.isDie = false;
        
    }
    public static MobTemplate getMob(int id) {
        for (MobTemplate mob : MobTemplate.entrys) {
            if (mob.tempId == id) {
                return mob;
            }
        }
        return null;
    }
    public void updateHP(int num) {
        hp += num;
        if (hp <= 0) {            
            hp = 0;
            status = 0;
            isDie = true;
            if (isRefresh) {
                timeRefresh = System.currentTimeMillis()+ 5000;
            }
        }
    }
    public void update() {

    }
}

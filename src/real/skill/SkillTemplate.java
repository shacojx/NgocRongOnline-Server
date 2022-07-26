package real.skill;

public class SkillTemplate {

    public boolean isBuffToPlayer() {
        return this.type == 2;
    }

    public boolean isUseAlone() {
        return this.type == 3;
    }

    public boolean isAttackSkill() {
        return this.type == 1;
    }

    public byte id;

    public int classId;

    public String name;

    public int maxPoint;

    public int manaUseType;

    public int type;

    public int iconId;

    public String[] description;

    public Skill[] skills;

    public String damInfo;
}

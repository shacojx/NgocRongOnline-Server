package real.skill;

import java.util.ArrayList;

public class Skills {

    public static void add(Skill skill) {
        Skills.skills.add(skill.skillId, skill);
    }

    public static Skill get(short skillId) {
        return (Skill) Skills.skills.get(skillId);
    }

    public static ArrayList<Skill> skills = new ArrayList<>();
}

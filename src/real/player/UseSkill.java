/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package real.player;

import java.io.IOException;
import real.skill.Skill;
import real.skill.SkillOptionTemplate;
import real.skill.SkillTemplate;
import real.skill.Skills;
import server.io.Message;

public class UseSkill {
    public static void useSkill(Player p, Message m) throws IOException {
        short idSkill = m.reader().readShort();
        m.cleanup();
        Skill skill = p.getSkill(idSkill);
        if (skill != null && System.currentTimeMillis() > p.CSkilldelay) {
            Skill data = Skills.get(idSkill);
            if (data.skillId != 0) {
            p.CSkill = idSkill;
            }
        }
    }
}

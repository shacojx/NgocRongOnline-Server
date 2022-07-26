/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package real.player;

import java.util.ArrayList;
import real.item.Item;
import real.skill.Skill;

/**
 *
 * @author Admin
 */
public class Detu extends Player{
    public ArrayList<Skill> skill;
    public Detu(Player n) {
        try {
            this.id = -n.id-100000;
            this.ItemBody = new Item[6];
            this.KSkill = new byte[3];
            this.OSkill = new byte[5];
            this.skill = n.skill;
            for (byte i = 0; i < this.KSkill.length; i++) {
                this.KSkill[i] = -1;
            }
            for (byte i = 0; i < this.OSkill.length; i++) {
                this.OSkill[i] = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package real.skill;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import server.FileIO;
import server.Util;

public class SkillData {

    public static NClass[] nClasss;

    private static SkillOptionTemplate[] sOptionTemplates;

    public static void createSkill() {
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(FileIO.readFile("data/NRskill_v5"));
            DataInputStream dis = new DataInputStream(is);
            dis.readByte();
            dis.readByte();
            sOptionTemplates = new SkillOptionTemplate[(int) dis.readByte()];
            for (int i = 0; i < sOptionTemplates.length; i++) {
                sOptionTemplates[i] = new SkillOptionTemplate();
                sOptionTemplates[i].id = i;
                sOptionTemplates[i].name = dis.readUTF();
            }
            nClasss = new NClass[dis.readByte()];
            for (int j = 0; j < nClasss.length; j++) {
                nClasss[j] = new NClass();
                nClasss[j].classId = j;
                nClasss[j].name = dis.readUTF();
                nClasss[j].skillTemplates = new SkillTemplate[(int) dis.readByte()];
                for (int k = 0; k < nClasss[j].skillTemplates.length; k++) {
                    nClasss[j].skillTemplates[k] = new SkillTemplate();
                    nClasss[j].skillTemplates[k].id = dis.readByte();
                    nClasss[j].skillTemplates[k].name = dis.readUTF();
                    nClasss[j].skillTemplates[k].maxPoint = (int) dis.readByte();
                    nClasss[j].skillTemplates[k].manaUseType = (int) dis.readByte();
                    nClasss[j].skillTemplates[k].type = (int) dis.readByte();
                    nClasss[j].skillTemplates[k].iconId = (int) dis.readShort();
                    nClasss[j].skillTemplates[k].damInfo = dis.readUTF();
                    /*nClasss[j].skillTemplates[k].description = */dis.readUTF();
                    nClasss[j].skillTemplates[k].skills = new Skill[(int) dis.readByte()];
                    for (int l = 0; l < nClasss[j].skillTemplates[k].skills.length; l++) {
                        nClasss[j].skillTemplates[k].skills[l] = new Skill();
                        nClasss[j].skillTemplates[k].skills[l].skillId = dis.readShort();
                        nClasss[j].skillTemplates[k].skills[l].template = nClasss[j].skillTemplates[k];
                        nClasss[j].skillTemplates[k].skills[l].point = (int) dis.readByte();
                        nClasss[j].skillTemplates[k].skills[l].powRequire = dis.readLong();
                        nClasss[j].skillTemplates[k].skills[l].manaUse = (int) dis.readShort();
                        nClasss[j].skillTemplates[k].skills[l].coolDown = dis.readInt();
                        nClasss[j].skillTemplates[k].skills[l].dx = (int) dis.readShort();
                        nClasss[j].skillTemplates[k].skills[l].dy = (int) dis.readShort();
                        nClasss[j].skillTemplates[k].skills[l].maxFight = (int) dis.readByte();
                        nClasss[j].skillTemplates[k].skills[l].damage = dis.readShort();
                        nClasss[j].skillTemplates[k].skills[l].price = dis.readShort();
                        nClasss[j].skillTemplates[k].skills[l].moreInfo = dis.readUTF();
                        //Skills.add(nClasss[j].skillTemplates[k].skills[l]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Util.log("finish createSkill");
    }
}

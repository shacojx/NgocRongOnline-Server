package real.item;

import cache.Part;
import cache.PartImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.sql.Array;
import java.util.ArrayList;
import server.FileIO;
import server.Util;

public class ItemData {

    public static ItemOptionTemplate[] iOptionTemplates;
    public static Part[] part;
    public int id;

    public static void loadDataItem() {
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(FileIO.readFile("res/cache/NRitem0"));
            DataInputStream dis = new DataInputStream(is);
            dis.readByte();
            dis.readByte();
            dis.readByte();
            iOptionTemplates = new ItemOptionTemplate[(int) dis.readUnsignedByte()];
            for (int i = 0; i < iOptionTemplates.length; i++) {
                iOptionTemplates[i] = new ItemOptionTemplate();
                iOptionTemplates[i].id = i;
                iOptionTemplates[i].name = dis.readUTF();
                iOptionTemplates[i].type = (int) dis.readByte();
            }
            is = new ByteArrayInputStream(FileIO.readFile("res/cache/NRitem1"));
            dis = new DataInputStream(is);
            dis.readByte();
            dis.readByte();
            dis.readByte();
            int num = (int) dis.readShort();
            for (int j = 0; j < num; j++) {
                ItemTemplate it = new ItemTemplate();
                it.id = j;
                it.type = dis.readByte();
                it.gender = dis.readByte();
                it.name = dis.readUTF();
                it.description = dis.readUTF();
                it.level = dis.readByte();
                it.strRequire = dis.readInt();
                it.iconID = dis.readShort();
                it.part = dis.readShort();
                it.isUpToUp = dis.readBoolean();
                Util.debug("id " + it.id + " name" + it.name);
//                ItemTemplate.entrys.add(it);
            }
            is = new ByteArrayInputStream(FileIO.readFile("res/cache/NRitem100"));
            dis = new DataInputStream(is);
            dis.readByte();
            dis.readByte();
            dis.readByte();
            int num5 = dis.readShort();
            int[][] array;
            array = new int[num5][];
            
            for (int i = 0; i < array.length; i++) {
                int num6 = dis.readByte();
                array[i] = new int[num6];
                for (int j = 0; j < num6; j++) {
                    array[i][j] = dis.readShort();
                    Util.log("id " + j + " part " + array[i][j]);
                }
            }
            
            is = new ByteArrayInputStream(FileIO.readFile("res/cache/NRitem2"));
            dis = new DataInputStream(is);
            dis.readByte();
            dis.readByte();
            dis.readByte();
            int num2 = (int) dis.readShort();
            int num3 = (int) dis.readShort();
            for (int j = num2; num2 <= num3; j++) {
                ItemTemplate it2 = new ItemTemplate();
                it2.id = j;
                it2.type = dis.readByte();
                it2.gender = dis.readByte();
                it2.name = dis.readUTF();
                it2.description = dis.readUTF();
                it2.level = dis.readByte();
                it2.strRequire = dis.readInt();
                it2.iconID = dis.readShort();
                it2.part = dis.readShort();
                it2.isUpToUp = dis.readBoolean();
                System.err.println("id " + it2.id + " name " + it2.name + " part " + it2.part);
            }
            
            is = new ByteArrayInputStream(FileIO.readFile("res/cache/data/NR_part"));
            dis = new DataInputStream(is);
//            dis.readByte();
//            dis.readByte();
            int number = dis.readShort();
            part = new Part[number];
            for (int i = 0; i < part.length; i++) {
                int type = dis.readByte();
                part[i] = new Part(type);
                for (int j = 0; j < part[i].pi.length; j++) {
                    part[i].pi[j] = new PartImage();
                    part[i].pi[j].id = dis.readShort();
                    part[i].pi[j].dx = dis.readByte();
                    part[i].pi[j].dy = dis.readByte();
                }

            }
            Util.log("finish load Part");
        } catch (Exception e) {
        }
    }
}

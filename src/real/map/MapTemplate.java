package real.map;

import java.util.ArrayList;
import real.map.ItemMap;
import real.map.Npc;
import real.map.WayPoint;

public class MapTemplate {
    public int id;
    public byte planetId;
    public byte tileId;
    public byte bgId;
    public byte bgType;
    public byte type;
    public String name;
    public WayPoint[] wayPoints;
    public byte maxplayers;
    public Npc[] npcs;
    public byte numarea;
    public Mob[] mobs;
    public ArrayList<ItemMap> items;
    public static MapTemplate[] arrTemplate;
        public static int T_EMPTY = 0;
	public static int T_TOP = 2;
	public static int T_LEFT = 4;
	public static int T_RIGHT = 8;
	public static int T_TREE = 16;
	public static int T_WATERFALL = 32;
	public static int T_WATERFLOW = 64;
	public static int T_TOPFALL = 128;
	public static int T_OUTSIDE = 256;
	public static int T_DOWN1PIXEL = 512;
	public static int T_BRIDGE = 1024;
	public static int T_UNDERWATER = 2048;
	public static int T_SOLIDGROUND = 4096;
	public static int T_BOTTOM = 8192;
	public static int T_DIE = 16384;
	public static int T_HEBI = 32768;
	public static int T_BANG = 65536;
	public static int T_JUM8 = 131072;
	public static int T_NT0 = 262144;
	public static int T_NT1 = 524288;
    public int tmw;
    public int tmh;
    public int pxw;
    public int pxh;
    public char[] maps;
    public int[] types;
    public short[] arMobid;
    public short[] arrMobx;
    public short[] arrMoby;
    public int[] arrMoblevel;
    public int[] arrMaxhp;
    public static String getNameByID(int id)
    {

        for (MapTemplate mapTemplate : arrTemplate) {
            if(id == mapTemplate.id)
            {
                return mapTemplate.name;
            }
        }
        return "";
    }
}

//package real.map;
//
//import java.util.ArrayList;
//import server.Util;
//
//public class MapManager implements Runnable {
//
//    private static MapManager instance;
//
//    public ArrayList<Map> maps;
//    public Map[] arrTemplate;
//    public MapManager() {
//        this.maps = new ArrayList<>();
//        new Thread(this).start();
//    }
//
//    public static MapManager gI() {
//        if (instance == null) {
//            instance = new MapManager();
//        }
//        return instance;
//    }
//
//    public void init() {
//        Util.log("load map");
//        maps = MapData.loadMap();
//        Util.log("finish load map: " + maps.size());
//    }
//
//    public Map getMapById(int _id) {
//        for (Map map : maps) {
//            if (map.id == _id) {
//                return map;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            long l1 = System.currentTimeMillis();
//
//            for (Map map : maps) {
//                map.update();
//            }
//
//            long l2 = System.currentTimeMillis() - l1;
//            if (l2 < 1000) {
//                try {
//                    Thread.sleep(1000 - l2);
//                } catch (InterruptedException e) {
//                }
//            }
//        }
//    }
//}

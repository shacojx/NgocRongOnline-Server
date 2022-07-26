package server;

import java.io.ByteArrayOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.joda.time.DateTime;
import real.clan.ClanManager;
import real.item.ItemData;
import real.map.Map;
import real.map.MapTemplate;
import real.player.PlayerManger;
import real.skill.SkillData;
import server.io.Session;

public class Server {
    private Controller controller;
    private static Server instance;
    public static Object LOCK_MYSQL = new Object();
    public static boolean isDebug = true;
    public static Manager manager;
    public Menu menu;
    public static Map[] maps;
    public static ByteArrayOutputStream[] cache = new ByteArrayOutputStream[7];
    public static void MessageDebug(String message)
    {
        if(!isDebug)
        {return;}
        System.err.println(DateTime.now()+" - Debug: " + message);
    }
    public void init() {
        ItemData.loadDataItem();
        menu = new Menu();
        manager = new Manager();
        ClanManager.gI().init();
        SkillData.createSkill();
        
        this.controller = new Controller();
        cache[0] = GameScr.loadFile("res/cache/NRdata");
        cache[1] = GameScr.loadFile("res/cache/NRmap");
        cache[2] = GameScr.loadFile("res/cache/NRskill");
        cache[3] = GameScr.loadFile("res/cache/NRitem0");
        cache[4] = GameScr.loadFile("res/cache/NRitem1");
        cache[5] = GameScr.loadFile("res/cache/NRitem2");
        cache[6] = GameScr.loadFile("res/cache/NRitem100");
        this.maps = new Map[MapTemplate.arrTemplate.length];
        short i;

        for (i = 0; i < this.maps.length; ++i) {
            
            this.maps[i] = new Map(MapTemplate.arrTemplate[i]);
            this.maps[i].start();
        }
    }

    public static Server gI() {
        if (instance == null) {
            instance = new Server();
            instance.init();
        }
        return instance;
    }
    
    public static void main(String[] args) {
        Server.gI().run();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                        Util.log("Player: " + PlayerManger.gI().size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        }).start();
    }

    public void run() {
        ServerSocket listenSocket = null;
        try {
            Util.log("Start server...");
            listenSocket = new ServerSocket(Server.manager.port);
            while (true) {
                Socket sc = listenSocket.accept();
                Session session = new Session(sc, controller);
                session.start();
                Util.log("Accept socket listen " + sc.getPort());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}

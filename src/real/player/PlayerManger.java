package real.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import real.item.ItemDAO;
import server.io.Message;
import server.io.Session;

public class PlayerManger {

    private static PlayerManger instance;

    private ArrayList<Player> players;
    private ArrayList<User> users;
    private Timer timer;
    public final ArrayList<Session> conns;
    public final HashMap<Integer, Session> conns_id;
    public final HashMap<Integer, Player> players_id = new HashMap<Integer, Player>();
    public final HashMap<String, Player> players_uname = new HashMap<String, Player>();
    public PlayerManger() {
        this.players = new ArrayList<>();
        this.timer = new Timer();
        this.users = new ArrayList<>();
        this.conns = new ArrayList<>();
        this.conns_id = new HashMap<>();
    }
    
    public static PlayerManger gI(){
        if (instance == null){
            instance = new PlayerManger();
        }
        return instance;
    }
    
//    public Player load(int userId){
//        Player player = PlayerDAO.load(userId);
//        ItemDAO.loadPlayerItems(player);
//        return player;
//    }

    public Player getPlayerByUserID(int _userID) {
        for (Player player : players) {
            if (player.session.userId == _userID){
                return player;
            }
        }
        return null;
    }
    public User getUserID(int _userID) {
        for (User player : users) {
            if (player.session.userId == _userID){
                return player;
            }
        }
        return null;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public int size(){
        return players.size();
    }
    public void SendMessageServer(Message m) {
        synchronized (conns) {
            for (int i = conns.size()-1; i >= 0; i--)
                if (conns.get(i).player != null)
                    conns.get(i).sendMessage(m);
        }
    }
    public void put(Session conn) {
        if (!conns_id.containsValue(conn))
            conns_id.put(conn.userId, conn);
        if (!conns.contains(conn))
            conns.add(conn);
    }
    public void put(Player p) {
        if (!players_id.containsKey(p.id))
            players_id.put(p.id, p);
    }

}

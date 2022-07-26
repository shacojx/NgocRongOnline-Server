package server;

import java.io.IOException;
import java.sql.SQLException;
import real.player.Player;
import server.io.Message;

public class Draw {
    
    private static final Server server = Server.gI();
    
    public static void Draw(Player p, Message m) throws IOException {
      //  short menuId = m.reader().readShort();
        String str = m.reader().readUTF();
        short menuId = m.reader().readShort();
        m.cleanup();
        System.out.println("menuId "+menuId+" str "+str);
        byte b = -1;
        try {
            b = m.reader().readByte();
        } catch (IOException e) {}
        m.cleanup();
        switch (menuId) {
            case 51:
                p.session.matkhau = str;
                break;
            case 52:
                p.session.matkhau = str;
                break;
        }
    }
}

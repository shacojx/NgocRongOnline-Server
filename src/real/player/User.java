/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package real.player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import server.SQLManager;
import server.Util;
import server.io.Session;

public class User {
    public String host;
    public static String mysql_part;
    public static String backup_part;
    public static String mysql_host;
    public static int mysql_port;
    public static String mysql_database_data;
    public int id;
    public String username;
    public String password;
    public String nhanvat = null;
    public Session session;
    
}

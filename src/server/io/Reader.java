package server.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Reader {

    private ByteArrayInputStream is;

    private DataInputStream dis;

    public Reader(byte[] data) {
        this.is = new ByteArrayInputStream(data);
        this.dis = new DataInputStream(is);
    }
    
    public DataInputStream read(){
        return this.dis;
    }
}

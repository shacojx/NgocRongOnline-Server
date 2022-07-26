package server.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Message {

    public byte command;
    
    public byte cmd;
    
    public boolean isBigMsg;

    private ByteArrayOutputStream os;

    private DataOutputStream dos;

    private ByteArrayInputStream is;

    private DataInputStream dis;

    public Message() {
    }

    public Message(int command) {
        this((byte) command);
    }

    public Message(byte command) {
        this.command = command;
        this.os = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(os);
    }

    public Message(byte command, byte[] data) {
        this.command = command;
        this.is = new ByteArrayInputStream(data);
        this.dis = new DataInputStream(is);
    }

    public DataOutputStream writer() {
        return dos;
    }

    public DataInputStream reader() {
        return dis;
    }

    public byte[] getData() {
        return os.toByteArray();
    }

    public void cleanup() {
        try {
            if (dis != null) {
                dis.close();
            }
            if (dos != null) {
                dos.close();
            }
        } catch (Exception e) {
        }
    }
}

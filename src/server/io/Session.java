package server.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import real.player.Player;
import server.Controller;

public class Session extends Thread {

    private boolean sendKeyComplete, connected;

    private byte[] keys = "gino".getBytes();

    private byte curR, curW;

    private Socket socket;

    private DataInputStream dis;

    private DataOutputStream dos;

    private Thread sendThread;

    private Thread receiveThread;

    private Controller controller;
    public byte zoomLevel;
    private ArrayList<Message> messages;
    public Player player = null;
    public int userId;
    public String nhanvat = null;
    public String taikhoan;
    public String matkhau;
    public Session(Socket socket, Controller controller) {
        try {
            this.socket = socket;
            this.controller = controller;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.messages = new ArrayList<>();
            this.connected = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (sendKeyComplete) {
                        if (messages.size() > 0) {
                            Message msg = messages.get(0);
                            doSendMessage(msg);
                            messages.remove(0);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            while (true) {
                Message msg = readMessage();
                if (msg != null) {
                    System.out.println("Read CMD " + msg.command);
                    controller.onMessage(this, msg);
                    msg.cleanup();
                } else {
                    break;
                }
            }
        } catch (Exception e) {
        }
        if (connected) {
            
            System.out.println("Session " + socket.getPort() + " disconnect...");
            disconnect();
        }
    }

    /* AAAAAAA */
//    public void start() {
//        receiveThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true) {
//                        Message msg = readMessage();
//                        if (msg != null) {
//                            //System.out.println("Read message " + msg.command);
//                            controller.onMessage(this, msg);
//                            msg.cleanup();
//                        } else {
//                            break;
//                        }
//                    }
//                } catch (Exception e) {
//                }
//                if (connected) {
//                    System.out.println("Session " + socket.getPort() + " disconnect...");
//                    disconnect();
//                }
//            }
//        });
//        receiveThread.start();
//        sendThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (sendKeyComplete) {
//                        if (messages.size() > 0) {
//                            Message msg = messages.get(0);
//                            doSendMessage(msg);
//                            messages.remove(0);
//                        }
//                        try {
//                            Thread.sleep(10);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
    
    private Message readMessage() throws Exception {
        byte cmd = dis.readByte();
        if (sendKeyComplete) {
            cmd = readKey(cmd);
        }
        int size;
        if (sendKeyComplete) {
            byte b1 = dis.readByte();
            byte b2 = dis.readByte();
            size = (readKey(b1) & 255) << 8 | readKey(b2) & 255;
        } else {
            size = dis.readUnsignedShort();
        }
        byte data[] = new byte[size];
        int len = 0;
        int byteRead = 0;
        while (len != -1 && byteRead < size) {
            len = dis.read(data, byteRead, size - byteRead);
            if (len > 0) {
                byteRead += len;
            }
        }
        if (sendKeyComplete) {
            for (int i = 0; i < data.length; i++) {
                data[i] = readKey(data[i]);
            }
        }
        return new Message(cmd, data);
    }

    public void sendMessage(Message msg) {
        messages.add(msg);
    }

    public synchronized void doSendMessage(Message msg) {
        byte[] data = msg.getData();
        try {
            if (sendKeyComplete) {
                byte b = writeKey(msg.command);
                dos.writeByte(b);
            } else {
                dos.writeByte(msg.command);
            }
            if (data != null) {
                int size = data.length;
                if (msg.command == -32 || msg.command == -66 || msg.command == -74 || msg.command == 11 || msg.command == -67 || msg.command == -87 || msg.command == 66) {
                    byte b = writeKey((byte) (size));
                    dos.writeByte(b - 128);
                    byte b2 = writeKey((byte) (size >> 8));
                    dos.writeByte(b2 - 128);
                    byte b3 = writeKey((byte) (size >> 16));
                    dos.writeByte(b3 - 128);
                } else if (sendKeyComplete) {
                    int byte1 = writeKey((byte) (size >> 8));
                    dos.writeByte(byte1);
                    int byte2 = writeKey((byte) (size & 255));
                    dos.writeByte(byte2);
                } else {
                    dos.writeShort(size);
                }
                if (sendKeyComplete) {
                    for (int i = 0; i < data.length; i++) {
                        data[i] = writeKey(data[i]);
                    }
                }
                dos.write(data);
            } else {
                dos.writeShort(0);
            }
            dos.flush();
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte writeKey(byte b) {
        byte i = (byte) ((keys[curW++] & 255) ^ (b & 255));
        if (curW >= keys.length) {
            curW %= keys.length;
        }
        return i;
    }

    private byte readKey(byte b) {
        byte i = (byte) ((keys[curR++] & 255) ^ (b & 255));
        if (curR >= keys.length) {
            curR %= keys.length;
        }
        return i;
    }

    private void disconnect() {
        controller.logout(this);
        curR = 0;
        curW = 0;
        try {
            connected = false;
            if (socket != null) {
                socket.close();
                socket = null;
            }
            if (dos != null) {
                dos.close();
                dos = null;
            }
            if (dis != null) {
                dis.close();
                dis = null;
            }
            sendThread = null;
            receiveThread = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSessionKey() {
        Message msg = new Message(-27);
        try {
            msg.writer().writeByte(keys.length);
            msg.writer().writeByte(keys[0]);
            for (int i = 1; i < keys.length; i++) {
                msg.writer().writeByte(keys[i] ^ keys[i - 1]);
            }
            doSendMessage(msg);
            msg.cleanup();
            sendKeyComplete = true;
            sendThread.start();
        } catch (Exception e) {
        }
    }

    public void setClientType(Message msg) {
        try {
            msg.reader().readByte();//client_type
            zoomLevel = msg.reader().readByte();//zoom_level
            msg.reader().readBoolean();//is_gprs
            msg.reader().readInt();//width
            msg.reader().readInt();//height
            msg.reader().readBoolean();//is_qwerty
            msg.reader().readBoolean();//is_touch
            msg.reader().readUTF();//
            msg.cleanup();
        } catch (Exception e) {
        }

    }

}

package server.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Writer {

    private ByteArrayOutputStream os;

    private DataOutputStream dos;

    public Writer() {
        this.os = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.os);
    }

    public DataOutputStream writer() {
        return this.dos;
    }

    public byte[] getData() {
        return os.toByteArray();
    }
}

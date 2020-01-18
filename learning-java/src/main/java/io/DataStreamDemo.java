package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataStreamDemo {

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(baos));
        dos.writeUTF("hello");
        dos.writeInt(18);
        dos.writeBoolean(false);
        dos.writeChar('a');
        dos.flush();
        byte[] bs = baos.toByteArray();
        System.out.println(bs.length);
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(bs)));
        String s = dis.readUTF();
        int a = dis.readInt();
        boolean b = dis.readBoolean();
        char c = dis.readChar();
        System.out.println(s);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }

}

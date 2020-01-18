package io;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ByteArrayOutputStreamDemo {

    public static void main(String[] args) {
        byte[] bs = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            String msg = "pattern recognition and machine learning";
            byte[] data = msg.getBytes();
            baos.write(data, 0, data.length);
            baos.flush();
            bs = baos.toByteArray();
            System.out.println(new String(bs, 0, bs.length));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

package io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;

public class ByteArrayInputStreamDemo {

    public static void main(String[] args) {
        byte[] bs = "pattern recognition and machine learning".getBytes();
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(bs);
            byte[] buffer = new byte[6];
            int l = -1;
            while ((l = is.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, l));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

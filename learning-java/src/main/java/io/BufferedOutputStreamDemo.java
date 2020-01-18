package io;

import java.io.*;

public class BufferedOutputStreamDemo {

    public static void main(String[] args) {
        String path = BufferedOutputStreamDemo.class.getClassLoader().getResource(".").getPath();
        File f = new File(path + "/hello.md");
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(f));
            String s = "hello";
            byte[] bs = s.getBytes();
            os.write(bs, 0, bs.length);
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

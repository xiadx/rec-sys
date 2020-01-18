package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

public class FileToByteArrayDemo {

    public static void main(String[] args) {
        String path = FileToByteArrayDemo.class.getClassLoader().getResource("images/book/prml.png").getPath();
        System.out.println(fileToByteArray(path).length);
    }

    public static byte[] fileToByteArray(String path) {
        File src = new File(path);
        byte[] dest = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            is = new FileInputStream(src);
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int l = -1;
            while ((l = is.read(buffer)) != -1) {
                baos.write(buffer, 0, l);
            }
            baos.flush();
            dest = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        return dest;
    }

}

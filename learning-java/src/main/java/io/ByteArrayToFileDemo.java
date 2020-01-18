package io;

import java.io.*;

public class ByteArrayToFileDemo {

    public static void main(String[] args) {
        String path = ByteArrayToFileDemo.class.getClassLoader().getResource(".").getPath();
        byte[] bs = fileToByteArray(path + "/images/book/prml.png");
        byteArrayToFile(bs, path + "/prml.png");
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

    public static void byteArrayToFile(byte[] src, String path) {
        File dest = new File(path);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new ByteArrayInputStream(src);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int l = -1;
            while ((l = is.read(buffer)) != -1) {
                os.write(buffer, 0, l);
            }
            os.flush();
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

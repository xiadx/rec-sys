package io;

import java.io.*;
import java.net.URL;

public class FileUtil {

    public static void main(String[] args) {
        URL url = FileUtil.class.getClassLoader().getResource("images/book");
        String path = null;
        if (url != null) {
            path = url.getPath();
        }
        String srcPath = path + "/prml.png";
        String destPath = path + "/prml-copy.png";

        try {
            InputStream is = new FileInputStream(srcPath);
            OutputStream os = new FileOutputStream(destPath);
            copy(is, os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bs = null;
        try {
            InputStream is = new FileInputStream(destPath);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            copy(is, os);
            bs = os.toByteArray();
            System.out.println(bs.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream is = new ByteArrayInputStream(bs);
            OutputStream os = new FileOutputStream(path + "/prml-byte.png");
            copy(is, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy(InputStream is, OutputStream os) {
        try {
            byte[] buffer = new byte[1024];
            int l = -1;
            while ((l = is.read(buffer)) != -1) {
                os.write(buffer, 0, l);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(is, os);
        }
    }

//    public static void copyII(InputStream is, OutputStream os) {
//        try (is; os) {
//            byte[] buffer = new byte[1024];
//            int l = -1;
//            while ((l = is.read(buffer)) != -1) {
//                os.write(buffer, 0, l);
//            }
//            os.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void close(InputStream is, OutputStream os) {
        try {
            if (null != os) {
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (null != is) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(Closeable... ios) {
        for (Closeable io : ios) {
            try {
                if (null != io) {
                    io.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

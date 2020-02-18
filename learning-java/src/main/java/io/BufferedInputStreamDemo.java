package io;

import java.io.*;

public class BufferedInputStreamDemo {

    public static void main(String[] args) {
        File f = new File("README.md");
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(f));
            byte[] buffer = new byte[1024];
            int l = -1;
            while ((l = is.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, l));
            }
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

        is = null;
        BufferedInputStream bis = null;
        try {
            is = new FileInputStream(f);
            bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int l = -1;
            while ((l = is.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, l));
            }
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
            try {
                if (null != bis) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

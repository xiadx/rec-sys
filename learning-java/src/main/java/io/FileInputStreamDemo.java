package io;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileInputStreamDemo {

    public static void main(String[] args) {
        File f = new File("README.md");
        try {
            InputStream is = new FileInputStream(f);
            System.out.println((char)is.read());
            System.out.println((char)is.read());
            System.out.println((char)is.read());
            System.out.println((char)is.read());
            System.out.println((char)is.read());
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = null;
        try {
            is = new FileInputStream(f);
            int i;
            while ((i = is.read()) != -1) {
                System.out.println((char)i);
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
        try {
            is = new FileInputStream(f);
            byte[] buffer = new byte[6];
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
    }

}

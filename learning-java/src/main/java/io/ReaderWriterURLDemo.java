package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.URL;

public class ReaderWriterURLDemo {

    public static void main(String[] args) {
        String path = ReaderWriterURLDemo.class.getClassLoader().getResource(".").getPath();
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(
                            new InputStreamReader(
                                    new URL("http://www.baidu.com").openStream(), "UTF-8"));
            writer = new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(path + "/baidu.html"), "UTF-8"));
            String s = null;
            while ((s = reader.readLine()) != null) {
                writer.write(s);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {;
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        InputStreamReader is = null;
        try {
            is = new InputStreamReader(new URL("http://www.baidu.com").openStream(), "UTF-8");
            int c = -1;
            while ((c = is.read()) != -1) {
                System.out.print((char)c);
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

        InputStream ins = null;
        try {
            ins = new URL("http://www.baidu.com").openStream();
            int c = -1;
            while ((c = ins.read()) != -1) {
                System.out.print((char)c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ins) {
                    ins.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

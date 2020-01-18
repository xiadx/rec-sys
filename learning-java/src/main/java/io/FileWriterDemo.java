package io;

import java.io.*;

public class FileWriterDemo {

    public static void main(String[] args) {
        String path = FileWriterDemo.class.getClassLoader().getResource(".").getPath();
        File f = new File(path + "/hello.md");
        Writer writer = null;
        try {
            writer = new FileWriter(f);
            String s = "hello\n";
            char[] cs = s.toCharArray();
            writer.write(cs, 0, cs.length);
            writer.write("hello\n");
            writer.append("hello\n");
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

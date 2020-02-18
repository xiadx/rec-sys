package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class ReaderWriterDemo {

    public static void main(String[] args) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            writer = new BufferedWriter(new OutputStreamWriter(System.out));
            String s = "";
            while (!s.equals("exit")) {
                s = reader.readLine();
                writer.write(s);
                writer.newLine();
                writer.flush();
            }
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
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));) {
//            String s = "";
//            while (!s.equals("exit")) {
//                s = reader.readLine();
//                writer.write(s);
//                writer.newLine();
//                writer.flush();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}

package io;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CopyDemoIII {

    public static void main(String[] args) {
        String path = CopyDemoIII.class.getClassLoader().getResource(".").getPath();
        String srcPath = path + "/hello.md";
        String destPath = path + "/hello-copy.md";
        copy(srcPath, destPath);
    }

    public static void copy(String srcPath, String destPath) {
        File src = new File(srcPath);
        File dest = new File(destPath);
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(src));
            bw = new BufferedWriter(new FileWriter(dest));
            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bw) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

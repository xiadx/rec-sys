package io;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReaderDemo {

    public static void main(String[] args) {
        File f = new File("README.md");
        Reader reader = null;
        try {
            reader = new FileReader(f);
            char[] buffer = new char[6];
            int l = -1;
            while ((l = reader.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, l));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

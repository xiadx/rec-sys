package common;

import java.io.File;
import java.io.IOException;

public class FileDemo {

    public static void main(String[] args) throws IOException {
        File f = new File("abc.txt");
        System.out.println(f);
        System.out.println(System.getProperty("user.dir"));
        System.out.println(f.exists());
        System.out.println(f.isDirectory());
        System.out.println(f.isFile());
        System.out.println(f.lastModified());
        System.out.println(f.length());
        System.out.println(f.getName());
        System.out.println(f.getAbsolutePath());

        tree(new File(System.getProperty("user.dir")), 0);
    }

    public static void tree(File file, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("-");
        }
        System.out.println(file.getName());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                tree(f, level + 1);
            }
        }
    }

}

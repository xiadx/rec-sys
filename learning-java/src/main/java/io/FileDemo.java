package io;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileDemo {

    public static void main(String[] args) throws IOException {
        URL url = FileDemo.class.getClassLoader().getResource("images/book/prml.png");
        String path = null;
        if (url != null) {
            path = url.getPath();
        }
        System.out.println(path);

        File f = new File(path);
        System.out.println(f.length());

        URL u1 = FileDemo.class.getClassLoader().getResource("images");
        String p1 = null;
        if (u1 != null) {
            p1 = u1.getPath();
        }
        String p2 = "/book/prml.png";
        File f1 = new File(p1, p2);
        System.out.println(f1.length());

        String p3 = f.getParent();
        String p4 = f.getName();
        File f2 = new File(p3, p4);
        System.out.println(f2.length());

        File f3 = new File(new File(p3), p4);
        System.out.println(f3.length());

        System.out.println(f.getAbsolutePath());

        System.out.println(new File("prml.png").getAbsolutePath());
        System.out.println(new File("book/prml.png").getAbsolutePath());

        System.out.println(System.getProperty("user.dir"));

        System.out.println(f.getName());
        System.out.println(f.getPath());
        System.out.println(f.getAbsolutePath());
        System.out.println(f.getParent());
        System.out.println(f.getParentFile().getName());

        System.out.println(f.exists());
        System.out.println(f.isFile());
        System.out.println(f.isDirectory());

        System.out.println(new File("book").length());
        System.out.println(new File(p1, "book").length());

        File f4 = new File(p1, "book/mlapp.png");
        boolean flag = f4.createNewFile();
        System.out.println(flag);
        flag = f4.delete();
        System.out.println(flag);
    }

}

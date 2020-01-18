package io;

import java.io.File;

public class PathDemo {

    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        System.out.println(path);
        System.out.println(File.separatorChar);
    }

}

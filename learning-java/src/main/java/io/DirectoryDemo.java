package io;

import java.io.File;
import java.net.URL;

public class DirectoryDemo {

    private static long size = 0;

    public static void main(String[] args) {
        URL url = DirectoryDemo.class.getClassLoader().getResource("images");
        String path = null;
        if (url != null) {
            path = url.getPath();
        }
        File d1 = new File(path + "/book/ml");
        boolean f1 = d1.mkdir();
        System.out.println(f1);
        File d2 = new File(path + "/book/ml/dl");
        boolean f2 = d2.mkdirs();
        System.out.println(f2);
        boolean f3 = d1.delete();
        System.out.println(f3);
        boolean f4 = d2.delete();
        System.out.println(f4);

        File d3 = new File(path);
        for (String s : d3.list()) {
            System.out.println(s);
        }
        for (File f : d3.listFiles()) {
            System.out.println(f.getAbsolutePath());
        }
        for (File r : d3.listRoots()) {
            System.out.println(r.getAbsolutePath());
        }
        recursive(d3, 0);
        count(d3);
        System.out.println(size);

        DirectorySize ds = new DirectorySize(path);
        System.out.println(ds.getSize());
        System.out.println(ds.getFileSize());
        System.out.println(ds.getDirSize());
    }

    public static void recursive(File d, int deep) {
        for (int i = 0; i < deep; i++) {
            System.out.print("-");
        }
        System.out.println(d.getName());
        if (d.isDirectory()) {
            for (File f : d.listFiles()) {
                recursive(f, deep + 1);
            }
        }
    }

    public static void count(File d) {
        if (null != d && d.exists()) {
            if (d.isFile()) {
                size += d.length();
            } else {
                for (File f : d.listFiles()) {
                    count(f);
                }
            }
        }
    }

}

class DirectorySize {

    private long size;
    private String path;
    private int fileSize;
    private int dirSize;
    private File f;

    public DirectorySize(String path) {
        this.path = path;
        this.f = new File(path);
        count(this.f);
    }

    private void count(File d) {
        if (null != d && d.exists()) {
            if (d.isFile()) {
                this.size += d.length();
                this.fileSize++;
            } else {
                this.dirSize++;
                for (File f : d.listFiles()) {
                    count(f);
                }
            }
        }
    }

    public long getSize() {
        return size;
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getDirSize() {
        return dirSize;
    }

}

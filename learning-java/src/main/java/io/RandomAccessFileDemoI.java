package io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileDemoI {

    public static void main(String[] args) throws IOException {
        File f = new File("README.md");

//        RandomAccessFile raf = new RandomAccessFile(f, "r");
//        raf.seek(2);
//        byte[] buffer = new byte[6];
//        int l = -1;
//        while ((l = raf.read(buffer)) != -1) {
//            System.out.println(new String(buffer, 0, l));
//        }
//        raf.close();

//        RandomAccessFile raf = new RandomAccessFile(f, "r");
//        int pos = 2;
//        long size = f.length();
//        raf.seek(pos);
//        byte[] buffer = new byte[6];
//        int l = -1;
//        while ((l = raf.read(buffer)) != -1) {
//            if (size > l) {
//                System.out.println(new String(buffer, 0, l));
//                size -= l;
//            } else {
//                System.out.println(new String(buffer, 0, (int)size));
//                break;
//            }
//        }
//        raf.close();

        long l = f.length();
        int blockSize = 12;
        int blockNumber = (int)Math.ceil(l * 1.0 / blockSize);
        System.out.println(l);
        System.out.println(blockNumber);
        int pos = 0;
        int size = (int)(blockSize > l ? l : blockSize);
        for (int i = 0; i < blockNumber; i++) {
            pos = i * blockSize;
            if (i == blockNumber - 1) {
                size = (int)l;
            } else {
                size = blockSize;
                l -= size;
            }
            System.out.println(i + " --> " + pos + " --> " + size);
            split(i, pos, size);
        }
    }

    public static void split(int i, int pos, int size) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(new File("README.md"), "r");
        raf.seek(pos);
        byte[] buffer = new byte[6];
        int l = -1;
        while ((l = raf.read(buffer)) != -1) {
            if (size > l) {
                System.out.println(new String(buffer, 0, l));
                size -= l;
            } else {
                System.out.println(new String(buffer, 0, size));
                break;
            }
        }
        raf.close();
    }

}

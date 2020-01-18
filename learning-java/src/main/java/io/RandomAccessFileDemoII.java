package io;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.net.URL;

public class RandomAccessFileDemoII {

    public static void main(String[] args) throws IOException {
        URL url = RandomAccessFileDemoII.class.getClassLoader().getResource("images/book");
        String path = null;
        if (url != null) {
            path = url.getPath();
        }
        File f = new File(path + "/prml.png");
        long l = f.length();
        int blockSize = 100 * 1024;
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
        String path = RandomAccessFileDemoII.class.getClassLoader().getResource("images/book").getPath();
        RandomAccessFile rafr = new RandomAccessFile(new File(path + "/prml.png"), "r");
        File d = new File(path + "/prml");
        if (!d.exists()) {
            d.mkdir();
        }
        RandomAccessFile rafw = new RandomAccessFile(new File(path + "/prml/p-" + i + ".png"), "rw");
        rafr.seek(pos);
        byte[] buffer = new byte[1024];
        int l = -1;
        while ((l = rafr.read(buffer)) != -1) {
            if (size > l) {
                rafw.write(buffer, 0, l);
                size -= l;
            } else {
                rafw.write(buffer, 0, size);
                break;
            }
        }
        rafw.close();
        rafr.close();
    }

}

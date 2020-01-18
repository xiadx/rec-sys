package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FileSplitUtil {

    private File src;
    private String destDir;
    private List<String> destPaths;
    private int blockSize;
    private int blockNumber;

    public FileSplitUtil(String srcPath, String destDir) {
        this(srcPath, destDir, 1024);
    }

    public FileSplitUtil(String srcPath, String destDir, int blockSize) {
        this.src = new File(srcPath);
        this.destDir = destDir;
        this.blockSize = blockSize;
        this.destPaths = new ArrayList<String>();

        init();
    }

    private void init() {
        long l = this.src.length();
        this.blockNumber = (int)Math.ceil(l * 1.0 / blockSize);
        for (int i = 0; i < blockNumber; i++) {
            this.destPaths.add(this.destDir + "/" + i + "-" + this.src.getName());
        }
    }

    public void split() throws IOException {
        long l = this.src.length();
        int pos = 0;
        int size = (int)(blockSize > l ? l : blockSize);
        for (int i = 0; i < this.blockNumber; i++) {
            pos = i * blockSize;
            if (i == blockNumber - 1) {
                size = (int)l;
            } else {
                size = blockSize;
                l -= size;
            }
            splitDetail(i, pos, size);
        }
    }

    private void splitDetail(int i, int pos, int size) throws IOException {
        RandomAccessFile rafr = new RandomAccessFile(this.src, "r");
        RandomAccessFile rafw = new RandomAccessFile(this.destPaths.get(i), "rw");
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

    public void merge(String destPath) throws IOException {
        OutputStream os = new BufferedOutputStream(new FileOutputStream(destPath, true));
        Vector<InputStream> vi = new Vector<InputStream>();
        for (int i = 0; i < destPaths.size(); i++) {
            vi.add(new BufferedInputStream(new FileInputStream(destPaths.get(i))));
        }
        SequenceInputStream sis = new SequenceInputStream(vi.elements());
        byte[] buffer = new byte[1024];
        int l = -1;
        while ((l = sis.read(buffer)) != -1) {
            os.write(buffer, 0, l);
        }
        os.flush();
        sis.close();
        os.close();
    }

    public static void main(String[] args) throws IOException {
        String path = FileSplitUtil.class.getClassLoader().getResource("images/book").getPath();
        String srcPath = path + "/prml.png";
        String destDir = path + "/prml";
        FileSplitUtil fsu = new FileSplitUtil(srcPath, destDir, 100 * 1024);
        fsu.split();
        fsu.merge(path + "/prml-split.png");
    }

}

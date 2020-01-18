package io;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class FileUtilsDemo {

    public static void main(String[] args) throws IOException {
        long l = FileUtils.sizeOf(new File("README.md"));
        System.out.println(l);
        String path = FileUtilsDemo.class.getClassLoader().getResource("images/book").getPath();
        l = FileUtils.sizeOf(new File(path));
        System.out.println(l);

        Collection<File> fs = FileUtils.listFiles(new File(path), EmptyFileFilter.NOT_EMPTY, null);
        for (File f : fs) {
            System.out.println(f.getAbsolutePath());
        }
        System.out.println("--------------------");
        fs = FileUtils.listFiles(new File(path), EmptyFileFilter.NOT_EMPTY, DirectoryFileFilter.INSTANCE);
        for (File f : fs) {
            System.out.println(f.getAbsolutePath());
        }
        System.out.println("--------------------");
        fs = FileUtils.listFiles(new File(path), new SuffixFileFilter("jpg"), DirectoryFileFilter.INSTANCE);
        for (File f : fs) {
            System.out.println(f.getAbsolutePath());
        }
        System.out.println("--------------------");
        fs = FileUtils.listFiles(new File(path),
                FileFilterUtils.and(new SuffixFileFilter("png"),
                        EmptyFileFilter.NOT_EMPTY),
                DirectoryFileFilter.INSTANCE);
        for (File f : fs) {
            System.out.println(f.getAbsolutePath());
        }
        System.out.println("--------------------");

        String s = FileUtils.readFileToString(new File("README.md"), "UTF-8");
        System.out.println(s);
        byte[] bs = FileUtils.readFileToByteArray(new File("README.md"));
        System.out.println(bs.length);

        List<String> ls = FileUtils.readLines(new File("README.md"), "UTF-8");
        for (String line : ls) {
            System.out.println(line);
        }

        LineIterator it = FileUtils.lineIterator(new File("README.md"), "UTF-8");
        while (it.hasNext()) {
            System.out.println(it.nextLine());
        }

        path = FileUtilsDemo.class.getClassLoader().getResource(".").getPath();
        FileUtils.write(new File(path + "/hello.md"), "hello\nworld", "UTF-8");
        FileUtils.writeStringToFile(new File(path + "/hello.md"), "\nhello\nworld", "UTF-8", true);
        FileUtils.writeByteArrayToFile(new File(path + "/hello.md"), "\nhello\nworld\n".getBytes("UTF-8"), true);

        List<String> as = new ArrayList<String>();
        as.add("prml");
        as.add("mlapp");
        as.add("esl");
        FileUtils.writeLines(new File(path + "/hello.md"), as, true);

        FileUtils.copyFile(new File(path + "/hello.md"), new File(path + "hello-copy.md"));
        FileUtils.copyFileToDirectory(new File(path + "/hello.md"), new File(path + "/images/book"));
        FileUtils.copyDirectoryToDirectory(new File(path + "/images/book/prml"), new File(path + "/images/book/ml"));
        FileUtils.copyDirectory(new File(path + "/images/book/ml/prml"), new File(path + "/images/book/ml/prml-copy"));

        s = IOUtils.toString(new URL("http://www.baidu.com"), "UTF-8");
        System.out.println(s);
    }

}

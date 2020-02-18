package io;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class CopyDemoII {

    public static void main(String[] args) {
        URL url = CopyDemoII.class.getClassLoader().getResource("images/book");
        String path = null;
        if (url != null) {
            path = url.getPath();
        }
        String srcPath = path + "/prml.png";
        String destPath = path + "/prml-copy.png";
        copy(srcPath, destPath);
    }

    public static void copy(String srcPath, String destPath) {
        File src = new File(srcPath);
        File dest = new File(destPath);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(src));
            os = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[1024];
            int l = -1;
            while ((l = is.read(buffer)) != -1) {
                os.write(buffer, 0, l);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

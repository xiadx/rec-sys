package io;

import java.io.IOException;

public class EncodeDecodeDemo {

    public static void main(String[] args) throws IOException {
        String s = "模式识别机器学习";
        byte[] bs = s.getBytes();
        System.out.println(bs.length);
        bs = s.getBytes("GBK");
        System.out.println(bs.length);
        bs = s.getBytes("UTF-8");
        System.out.println(bs.length);

        bs = s.getBytes();
        s = new String(bs, 0, bs.length, "UTF-8");
        System.out.println(s);
        s = new String(bs, 0, bs.length, "GBK");
        System.out.println(s);
        s = new String(bs, 0, bs.length - 1, "UTF-8");
        System.out.println(s);
    }

}

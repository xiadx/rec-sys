package common;

import java.util.Date;

public class DateDemo {

    public static void main(String[] args) {
        Date d1 = new Date(2000);
        System.out.println(d1);
        System.out.println(d1.getTime());
        Date d2 = new Date();
        System.out.println(d2.getTime());
        System.out.println(d2.after(d1));
        Date d3 = new Date(2020 - 1900, 3, 10);
        System.out.println(d3);
        System.out.println(d3.after(d2));
    }

}

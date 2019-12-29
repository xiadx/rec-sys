package common;

import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DateFormatDemo {

    public static void main(String[] args) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(df.format(new Date(4000000)));
        Date d = df.parse("1970-01-01 09:06:40");
        System.out.println(d.getTime());
    }

}

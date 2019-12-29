package common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class CalendarDemo {

    public static void main(String[] args) throws ParseException {
        Calendar calendar = new GregorianCalendar(2999, 10, 9, 22, 10, 50);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(weekday);
        System.out.println(day);

        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.YEAR, 8012);
        System.out.println(c1);

        Calendar c3 = new GregorianCalendar();
        c3.add(Calendar.YEAR, -100);
        System.out.println(c3);

        Date d = c3.getTime();
        Calendar c4 = new GregorianCalendar();
        c4.setTime(d);
        System.out.println(c4);

        printCalendar(c4);

        String s = "2020-9-10";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(s);
        Calendar c = new GregorianCalendar();
        c.setTime(date);

        System.out.println("日\t一\t二\t三\t四\t五\t六");

        c.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < c.get(Calendar.DAY_OF_WEEK) - 1; i++) {
            System.out.print("\t");
        }

        int days = c.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= days; i++) {
            System.out.print(c.get(Calendar.DAY_OF_MONTH) + "\t");
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println();
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }

        System.out.println();
        System.out.println("请输入日期（格式：2020-9-10）");
        Scanner scanner = new Scanner(System.in);
        String s1 = scanner.nextLine();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = df1.parse(s1);
        Calendar c5 = new GregorianCalendar();
        c5.setTime(d1);
        int d5 = c5.get(Calendar.DAY_OF_MONTH);
        int dt = c5.getActualMaximum(Calendar.DATE);
        System.out.println("日\t一\t二\t三\t四\t五\t六");

        c5.set(Calendar.DAY_OF_MONTH, 1);

        for (int i = 0; i < c5.get(Calendar.DAY_OF_WEEK) - 1; i++) {
            System.out.print("\t");
        }

        for (int i = 1; i <= dt; i++) {
            if (d5 == c5.get(Calendar.DAY_OF_MONTH)) {
                System.out.print(c5.get(Calendar.DAY_OF_MONTH) + "*\t");
            } else {
                System.out.print(c5.get(Calendar.DAY_OF_MONTH) + "\t");
            }
            if (c5.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println();
            }
            c5.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public static void printCalendar(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DAY_OF_MONTH);
        int weekday = c.get(Calendar.DAY_OF_WEEK) - 1;
        String week = weekday == 0 ? "7" : weekday + "";
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        System.out.println(String.format("year->%d,month->%d,date->%d,week->%s,hour->%d,minute->%d,second->%d", year, month, date, week, hour, minute, second));
        System.out.printf("year->%d,month->%d,date->%d,week->%s,hour->%d,minute->%d,second->%d\n", year, month, date, week, hour, minute, second);
        System.out.println("year->" + year + ",month->" + month + ",date->" + date + ",week->" + week + ",hour->" + hour + ",minute->" + minute + ",second->" + second);
    }

}

package basic;

import java.math.BigDecimal;

public class BasicDemo {

    int number; // member variable
    static int size; // static variable

    public static void main(String[] args) {
        System.out.println("Comment");
        // single line comment
        /*
        multi line comment
        */
        /**
         * document comment
         */

        System.out.println("Identifier");
        int a123 = 1;
//        int 123a = 2;
        int $a = 3;
        int _abc = 4;
//        int #abc = 5;
        int 年龄 = 18;
//        int class = 2;

        System.out.println("Variable");
        int age;
        age = 18;
        int salary = 3000;
        System.out.println(age);
        System.out.println(salary);

        System.out.println(new BasicDemo().number);

        System.out.println(BasicDemo.size);

        System.out.println("Constant");
        final String name = "abc";
//        name = "def";
        System.out.println(name);
        final double PI = 3.14;
        double r = 4;
        double area = PI * r * r;
        double circle = 2 * PI * r;
        System.out.println("Area:" + area);
        System.out.println("Circle:" + circle);

        System.out.println("Primitive Data Type");
        int a = 15;
        int b = 015;
        int c = 0x15;
//        byte d = 300;
        byte d = 30;
        long e = 740000000000L;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);

        float f = 3.14F;
        double g = 6.28;
        double h = 628E-2;
        System.out.println(f);
        System.out.println(g);
        System.out.println(h);

        float f1 = 0.1f;
        double f2 = 1.0 / 10;
        System.out.println(f1 == f2);

        float d1 = 423432423f;
        float d2 = d1 + 1;
        System.out.println(d1 == d2);

        BigDecimal bd = BigDecimal.valueOf(1.0);
        bd = bd.subtract(BigDecimal.valueOf(0.1));
        bd = bd.subtract(BigDecimal.valueOf(0.1));
        bd = bd.subtract(BigDecimal.valueOf(0.1));
        bd = bd.subtract(BigDecimal.valueOf(0.1));
        bd = bd.subtract(BigDecimal.valueOf(0.1));
        System.out.println(bd);
        System.out.println(1.0 - 0.1 - 0.1 - 0.1 - 0.1 - 0.1);

        BigDecimal bd2 = BigDecimal.valueOf(0.1);
        BigDecimal bd3 = BigDecimal.valueOf(1.0 / 10);
        System.out.println(bd2.equals(bd3));

        char c1 = 'a';
        char c2 = '\u0061';
        System.out.println(c1);
        System.out.println(c2);
        System.out.println('a' + 'b');
        System.out.println("" + 'a' + 'b');
        System.out.println("" + 'a' + '\t' + 'b');

        boolean flag = true;
        if (flag) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        System.out.println("Operator");
        byte b1 = 1;
        int i1 = 2;
        long l1 = 3;
//        byte b2 = b1 + i1;
//        int i2 = i1 + l1;
//        float f3 = 3.14;
        float f4 = 3.14F;
        float f5 = i1 + l1;
        System.out.println(b1);
        System.out.println(f4);
        System.out.println(f5);
        System.out.println(9 % 5);

        int n = 3;
        int m = n++;
        System.out.println("n=" + n + "\t" + "m=" + m);
        n = 3;
        m = ++n;
        System.out.println("n=" + n + "\t" + "m=" + m);

        n = 3;
        m = 4;
        n += m;
        System.out.println("n=" + n + "\t" + "m=" + m);
        n = 3;
        m = 4;
        n *= m + 3;
        System.out.println("n=" + n + "\t" + "m=" + m);

        n = 3;
        System.out.println(n == 3);
        System.out.println(n != 3);
        System.out.println(a < 5);

        char c3 = 'a';
        char c4 = 'c';
        System.out.println(c3);
        System.out.println((int)c3);
        System.out.println(0 + c3);
        System.out.println(c3 < c4);

        boolean b2 = true;
        boolean b3 = false;
        System.out.println(b2 & b3);
        System.out.println(b2 | b3);
        System.out.println(b2 ^ b3);
        System.out.println(!b2);

//        System.out.println(1 > 2 & 2 < (3 / 0));
        System.out.println(1 > 2 && 2 < (3 / 0));

        System.out.println(3 & 4);
        System.out.println(3 | 4);
        System.out.println(3 ^ 4);
        System.out.println(~3);
        System.out.println(3 << 2);
        System.out.println(12 >> 2);

        System.out.println("3" + 4 + 5);
        System.out.println(4 + 5 + "3");
        System.out.println('a');
        System.out.println('a' + 4);

        System.out.println(true ? "true" : "false");
        System.out.println(false ? "true" : "false");

        System.out.println((int)3.14);

        int money = 1000000000;
        int years = 20;
        int total = money * years;
        long t1 = money * years;
        long t2 = money * (long)years;
        System.out.println(total);
        System.out.println(t1);
        System.out.println(t2);

        System.out.println(Math.random());
        System.out.println((int)(6 * Math.random()) + 1);

        int z = (int)(6 * Math.random()) + 1;
        System.out.println(z);
        if (z <= 3) {
            System.out.println("small");
        } else {
            System.out.println("big");
        }

        int y = (int)(100 * Math.random());
        System.out.println(y);
        if (y < 15) {
            System.out.println("y < 15");
        } else if (y < 25) {
            System.out.println("15 <= y < 25");
        } else if (y < 45) {
            System.out.println("25 <= y < 45");
        } else if (y < 65) {
            System.out.println("45 <= y < 65");
        } else if (y < 85) {
            System.out.println("65 <= y < 85");
        } else {
            System.out.println("85 <= y < 100");
        }

        int month = (int)(1 + (12 * Math.random()));
        System.out.println(month);
        switch (month) {
            case 1:
            case 2:
            case 3:
                System.out.println("spring");
                break;
            case 4:
            case 5:
            case 6:
                System.out.println("summer");
                break;
            case 7:
            case 8:
            case 9:
                System.out.println("fall");
                break;
            case 10:
            case 11:
            case 12:
                System.out.println("winter");
                break;
            default:
                System.out.println("error");
                break;
        }

        char x = 'a';
        char w = (char)(x + (int)(26 * Math.random()));
        System.out.println(w);
        switch (w) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                System.out.println("vowel");
                break;
            case 'y':
            case 'w':
                System.out.println("semi-vowel");
                break;
            default:
                System.out.println("consonant");
        }

        int i = 1;
        int s = 0;
        while (i <= 100) {
            s += i;
            i++;
        }
        System.out.println(s);

        i = 1;
        s = 0;
        do {
            s += i;
            i++;
        } while (i <= 100);
        System.out.println(i);
        System.out.println(s);

        s = 0;
        for (i = 1; i <= 100; i++) {
            s += i;
        }
        System.out.println(i);
        System.out.println(s);

        i = 1;
        for (i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + " = " + j * i + "\t");
            }
            System.out.println();
        }

        int even = 0;
        int odd = 0;
        for (i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                even += i;
            } else {
                odd += i;
            }
        }
        System.out.println("even:" + even);
        System.out.println("odd:" + odd);

        for (i = 1; i <= 100; i++) {
            if (i % 5 == 0) {
                System.out.print(i + "\t");
            }
            if (i % 25 == 0) {
                System.out.println();
            }
        }

        int j = 0;
        for (i = 1; i <= 100; i++) {
            if (i % 5 == 0) {
                System.out.print(i + "\t");
                j++;
            }
            if (j == 5) {
                System.out.println();
                j = 0;
            }
        }

        int t = 0;
        while (true) {
            t++;
            int q = (int)(100 * Math.random());
            if (q == 88) {
                break;
            }
        }
        System.out.println(t);

        j = 0;
        for (i = 100; i < 150; i++) {
            if (i % 3 == 0) {
                continue;
            }
            System.out.print(i + "\t");
            j++;
            if (j % 5 == 0) {
                System.out.println();
                j = 0;
            }
        }
        System.out.println();

        outer:
        for (i = 101; i < 150; i++) {
            for (j = 2; j < i / 2; j++) {
                if (i % j == 0) {
                    continue outer;
                }
            }
            System.out.print(i + "\t");
        }
        System.out.println();

        System.out.println("Overload");
        BasicDemo de = new BasicDemo();
        de.hello();
        System.out.println(de.add(1, 2));
        System.out.println(de.add(1.0, 2.0));

        System.out.println("Recursive");
        System.out.println(de.factorial(5));
    }

    void hello() {
        System.out.println("hello");
    }

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    long factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

}

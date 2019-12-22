package basic;

import java.util.Scanner;

public class ScannerDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("name:");
        String name = sc.nextLine();
        System.out.println("age:");
        int age = sc.nextInt();
        System.out.println("name:" + name + "\n" + "age:" + age);
    }

}

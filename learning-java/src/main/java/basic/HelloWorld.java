package basic;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");

        int[] arr = {1, 2, 3, 4, 5};
        int total = 0;
        int i = 0;
        total += arr[i++];
        System.out.println(total);
        System.out.println(i);
    }
}

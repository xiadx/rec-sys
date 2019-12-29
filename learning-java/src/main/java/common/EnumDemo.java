package common;

public class EnumDemo {

    public static void main(String[] args) {
        Season s = Season.WINTER;
        switch (s) {
            case SPRING:
                System.out.println("spring");
                break;
            case SUMMER:
                System.out.println("summer");
                break;
            case AUTUMN:
                System.out.println("autumn");
                break;
            case WINTER:
                System.out.println("winter");
                break;
        }
    }

}

enum Season {
    SPRING, SUMMER, AUTUMN, WINTER
}

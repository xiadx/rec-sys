package thread;

public class LambdaDemoII {

    public static void main(String[] args) {
        ILove love = (int a) -> {
            System.out.println("i love lambda --> " + a);
        };
        love.lambda(100);
        love = (a) -> {
            System.out.println("i love lambda --> " + a);
        };
        love.lambda(50);
        love = a -> {
            System.out.println("i love lambda --> " + a);
        };
        love.lambda(5);
        love = a -> System.out.println("i love lambda --> " + a);
        love.lambda(0);
    }

}

interface ILove {

    void lambda(int a);

}

class Love implements ILove {

    @Override
    public void lambda(int a) {
        System.out.println("i love lambda --> " + a);
    }

}

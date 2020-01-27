package thread;

public class LambdaDemoIII {

    public static void main(String[] args) {
        IFavorite favorite = (int a, int b) -> {
            System.out.println("i favorite lambda --> " + (a + b));
            return a + b;
        };
        favorite.lambda(100, 200);
        favorite = (a, b) -> {
            System.out.println("i favorite lambda --> " + (a + b));
            return a + b;
        };
        favorite.lambda(10, 20);
        favorite = (a, b) -> a + b;
        System.out.println(favorite.lambda(1, 2));
    }

}

interface IFavorite {

    int lambda(int a, int b);

}

class Favorite implements IFavorite {

    @Override
    public int lambda(int a, int b) {
        System.out.println("i favorite lambda --> " + (a + b));
        return a + b;
    }

}

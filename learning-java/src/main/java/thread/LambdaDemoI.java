package thread;

public class LambdaDemoI {

    static class SLike implements ILike {

        @Override
        public void lambda() {
            System.out.println("i like lambda SLike");
        }
    }

    public static void main(String[] args) {
        ILike like = new Like();
        like.lambda();
        like = new SLike();
        like.lambda();
        class LLike implements ILike {
            @Override
            public void lambda() {
                System.out.println("i like lambda LLike");
            }
        }
        like = new LLike();
        like.lambda();
        like = new ILike() {
            @Override
            public void lambda() {
                System.out.println("i like lambda ALike");
            }
        };
        like.lambda();
        like = () -> {
            System.out.println("i like lambda");
        };
        like.lambda();
    }

}

interface ILike {

    void lambda();

}

class Like implements ILike {

    @Override
    public void lambda() {
        System.out.println("i like lambda Like");
    }

}

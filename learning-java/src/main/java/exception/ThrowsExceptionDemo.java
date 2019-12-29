package exception;

import java.io.IOException;
import java.io.FileReader;

public class ThrowsExceptionDemo {

    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader("README.md");
        System.out.println((char)reader.read());
        reader.close();
    }

}

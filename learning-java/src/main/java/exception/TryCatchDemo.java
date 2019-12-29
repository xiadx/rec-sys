package exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

public class TryCatchDemo {

    public static void main(String[] args) {
        FileReader reader = null;
        try {
            reader = new FileReader("README.md");
            System.out.println((char)reader.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

package junit;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestJunit {

    String message = "Hello World";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test
    public void testPrintMessage() {
//        message = "New World";
        message = "Hello World";
        assertEquals(message,messageUtil.printMessage());
    }

}

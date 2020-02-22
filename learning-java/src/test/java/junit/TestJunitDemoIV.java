package junit;

import junit.framework.*;

public class TestJunitDemoIV {

    public static void main(String[] a) {
        // add the test's in the suite
        TestSuite suite = new TestSuite(TestJunitDemoI.class, TestJunitDemoII.class, TestJunitDemoIII.class);
        TestResult result = new TestResult();
        suite.run(result);
        System.out.println("Number of test cases = " + result.runCount());
    }

}

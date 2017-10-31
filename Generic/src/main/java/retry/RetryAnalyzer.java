package retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{
    int counter = 0;
    int attempt = 2;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (counter < attempt){
            counter++;
            return true;
        }
        return false;
    }
}

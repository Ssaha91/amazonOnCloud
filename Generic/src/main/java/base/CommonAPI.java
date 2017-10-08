package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import reporting.ExtentFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CommonAPI {

    public static WebDriver driver = null;

    public static ExtentReports reports;
    public static ExtentTest test;

    public String saucelab_username = "saha4311";
    public String saucelab_accessKey = "28329940-1f7a-4501-bf2b-4300161da8a8";


    @Parameters({"useCloudEnv", "cloudEnvName", "platform", "platformVersion", "browserName", "browserVersion", "url", "pathReports"})
    @BeforeMethod
    public void initializeTest(boolean useCloudEnv,String cloudEnvName, String platform,
                               String platformVersion, String browserName, String browserVersion, String url, String pathReports){
        if (useCloudEnv==true){
            if(cloudEnvName.equalsIgnoreCase("saucelabs")){
                getCloudDriver(cloudEnvName, saucelab_username, saucelab_accessKey, platform, platformVersion, browserName, browserVersion, pathReports);
            }
            }else{
            getLocalDriver(platform, browserName, pathReports);
        }
        driver.manage().window().maximize();
        test.log(LogStatus.INFO, "Maximizing Browser");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.navigate().to(url);
    }

    public WebDriver getLocalDriver(String platform, String browserName, String pathReports) {

        reports = ExtentFactory.getInstance(pathReports);
        test = reports.startTest("sampleTest");

        if (platform.contains("macOS")) {
            if (browserName.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", "../Generic/driver/geckodriver");
                driver = new FirefoxDriver();
            } else if (browserName.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", "../Generic/driver/chromedriver");
                driver = new ChromeDriver();
            }

        } else if (platform.contains("win")) {
            if (browserName.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", "../Generic/driver/geckodriver.exe");
                driver = new FirefoxDriver();
            } else if (browserName.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", "../Generic/driver/chromedriver.exe");
                driver = new ChromeDriver();
            }
        }
        return driver;
    }

    public WebDriver getCloudDriver(String cloudEnvName, String envUserName, String envAccessKey, String platform,
                               String platformVersion, String browserName, String browserVersion, String pathReports){

        reports = ExtentFactory.getInstance(pathReports);
        test = reports.startTest("sampleTest");

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("browserName", browserName);
        cap.setCapability("browserVersion", browserVersion);
        cap.setCapability("platform", platform);
        cap.setCapability("platformVersion", platformVersion);

        if (cloudEnvName.equalsIgnoreCase("saucelabs")){
            try {
                driver = new RemoteWebDriver(new URL("http://" + envUserName + ":" + envAccessKey + "@ondemand.saucelabs.com:80/wd/hub"), cap);
                test.log(LogStatus.INFO, "Running Saucelabs");
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
                System.err.println("Invalid path");
            }
        }
        return driver;
    }
    @Parameters({"directoryPath"})
    @AfterMethod
    public void tearDown(ITestResult testResult, @Optional String directoryPath)throws IOException{
        String path = null;
        String imagePath = null;

        if (testResult.getStatus()== ITestResult.FAILURE){
            path = takeScreenShot(driver, testResult.getName(), directoryPath);
            imagePath = test.addScreenCapture(path);
            test.log(LogStatus.FAIL, "failed test case", imagePath);
        }else if (testResult.getStatus()== ITestResult.SUCCESS) {
            path = takeScreenShot(driver, testResult.getName(), directoryPath);
            imagePath = test.addScreenCapture(path);
            test.log(LogStatus.PASS, "passed test case", imagePath);
        }
        reports.endTest(test);
        reports.flush();
        driver.quit();
    }

    public String takeScreenShot(WebDriver driver, String fileName, String Path) throws IOException {
        fileName = fileName + ".png";
        File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File (Path + fileName));
        String destination = Path + fileName;
        return destination;
    }
}

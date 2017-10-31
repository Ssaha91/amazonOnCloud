package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import jdk.nashorn.internal.ir.IfNode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonAPI {

    public static WebDriver driver = null;

    public static ExtentReports reports;
    public static ExtentTest test;

    public String saucelab_username = "saha4311";
    public String saucelab_accessKey = "28329940-1f7a-4501-bf2b-4300161da8a8";

    @BeforeSuite
    public void startsExtentReport() {
        reports = new ExtentReports(System.getProperty("user.dir") + "/Extent-Report/reports.html", true);
        reports.loadConfig(new File(System.getProperty("user.dir")+ "/report-config.xml"));
    }
    @Parameters({"useCloudEnv", "cloudEnvName", "platform", "platformVersion", "browserName", "browserVersion", "url"})
    @BeforeMethod
    public void initializeTest(boolean useCloudEnv, String cloudEnvName, String platform,
                               String platformVersion, String browserName, String browserVersion, String url, @Optional Method method){

        test = reports.startTest(method.getName());

        if (useCloudEnv==true){
            if(cloudEnvName.equalsIgnoreCase("saucelabs")){
                getCloudDriver(cloudEnvName, saucelab_username, saucelab_accessKey, platform, platformVersion, browserName, browserVersion);
            }
            }else{
            getLocalDriver(platform, browserName);
        }
        driver.manage().window().maximize();
        test.log(LogStatus.INFO, "Maximizing Browser");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.navigate().to(url);
    }

    public WebDriver getLocalDriver(String platform, String browserName) {

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
                               String platformVersion, String browserName, String browserVersion){

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
    @AfterMethod(alwaysRun = true)
    protected void tearDown(ITestResult testResult) {
        String path;
        String imagePath;

        if (testResult.getStatus() == ITestResult.FAILURE) {
            path = captureScreenshot(driver, testResult.getName());
            imagePath = test.addScreenCapture(path);
            test.log(LogStatus.FAIL, "Failed Test Case: " + imagePath);
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            path = captureScreenshot(driver, testResult.getName());
            imagePath = test.addScreenCapture(path);
            test.log(LogStatus.SKIP, "Skipped Test Case: " + imagePath);
        }
        driver.quit();
        reports.endTest(test);
    }

    @AfterSuite
    protected void endReports(){
        reports.flush();
        reports.close();
    }
    protected String captureScreenshot(WebDriver driver, String screenshotName) {
        DateFormat dateFormat = null;
        Date date = null;
        if (System.getProperty("os.name").contains("Win")){
            dateFormat = new SimpleDateFormat("(MM.dd.yyyy-HH;mma)");
            date = new Date();
            dateFormat.format(date);
        }
        else if (System.getProperty("os.name").contains("Mac")){
            dateFormat = new SimpleDateFormat("(MM.dd.yyyy-HH:mma)");
            date = new Date();
            dateFormat.format(date);
        }


        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(System.getProperty("user.dir") + "/screenshots/" + screenshotName + " " + dateFormat.format(date) + ".png"));
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
        return screenshotName;
    }
    public boolean isAlertPresent(){
        try {
            driver.switchTo().alert();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Alert not found or something");
        }
        return false;
    }

    public String randomUserNameGenerator() {
        String username = RandomStringUtils.randomAlphabetic(8);
        return username;
    }

    public String randomNumberseGenerator() {
        String username = RandomStringUtils.randomAlphanumeric(4) + RandomStringUtils.randomAlphabetic(4);
        return username;
    }
}



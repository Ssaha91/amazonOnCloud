package testPages;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pages.Homepage;
import retry.RetryAnalyzer;

public class TestHomepage extends CommonAPI {

    @Test(enabled = false)
    public void testWindowHandles() throws InterruptedException {
        Homepage homepage = PageFactory.initElements(driver, Homepage.class);
        homepage.test();
    }

    @Test(enabled = false)
    public void alertsDemo() throws InterruptedException {
        Homepage homepage = PageFactory.initElements(driver, Homepage.class);
        homepage.alertDemo("Saha");
    }

    @Test(enabled = false)
    public void alertsDemoTwo() throws InterruptedException {
        Homepage homepage = PageFactory.initElements(driver, Homepage.class);
        homepage.confirmDemo("Saha", "Ok");
    }

    @Test(enabled = false)
    public void alertsDemoThree() throws InterruptedException {
        Homepage homepage = PageFactory.initElements(driver, Homepage.class);
        homepage.confirmDemo("Saha", "Cancel");
    }

    @Test(enabled = false)
    public void testFrameDemo() throws InterruptedException {
        Homepage homepage = PageFactory.initElements(driver, Homepage.class);
        homepage.frameDemo("Python");
    }
    @Test(enabled = false)
    public void testMouseHoverOver() throws InterruptedException{
        Homepage homepage = PageFactory.initElements(driver, Homepage.class);
        homepage.checkMouseHoverOver();
    }
    @Test(enabled = false)
    public void testRadioBtn() throws InterruptedException{
        Homepage homepage = PageFactory.initElements(driver, Homepage.class);
        homepage.checkRadioBtn();
    }
    @Test(enabled = false)
    public void testCheckbox() throws InterruptedException{
        Homepage homepage = PageFactory.initElements(driver, Homepage.class);
        homepage.checkCheckBox();
    }
    @Test(enabled = false)
    public void teslistOfRadioBtn() throws InterruptedException{
        Homepage homepage = PageFactory.initElements(driver, Homepage.class);
        homepage.checkListOfRadioBtn();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testRandom() {
        Homepage homepage = PageFactory.initElements(driver, Homepage.class);
        homepage.randomGeneratorTest();
    }
}

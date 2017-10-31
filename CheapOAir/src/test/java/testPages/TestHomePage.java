package testPages;

import base.CommonAPI;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pages.HomePages;

import static base.CommonAPI.driver;

public class TestHomePage extends CommonAPI {


    @Test(enabled = false)
    public void alert()  {
        HomePages hp = PageFactory.initElements(driver, HomePages.class);
        hp.isAlertPresent();
        driver.switchTo().alert().dismiss();
    }

    @Test
    public void checkTest() {
        HomePages hp = PageFactory.initElements(driver, HomePages.class);
        hp.check();
    }
}


package testPages;

import base.CommonAPI;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pages.Homepage;

public class TestHomepage extends CommonAPI {

    @Test
    public void clickSearchBox() throws Exception {
        Homepage hp = PageFactory.initElements(driver, Homepage.class);
        hp.goSearchBox();
    }
}

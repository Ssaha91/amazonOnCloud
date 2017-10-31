package pages;

import base.CommonAPI;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Set;

public class HomePages extends CommonAPI {

    @FindBy(xpath = ".//*[@id='ember1064']/button")
    WebElement textField;

    @FindBy(xpath = ".//*[@id='ember1060']")
    WebElement emailField;

    public void check() {
        emailField.sendKeys("test123");
        textField.click();
    }
}

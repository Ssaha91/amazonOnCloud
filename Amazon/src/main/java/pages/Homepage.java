package pages;

import base.CommonAPI;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import reader.DataReader;

public class Homepage extends CommonAPI {

    @FindBy(id = "twotabsearchtextbox")
    WebElement searchBox;

    public void goSearchBox() throws Exception {
        String[] items = DataReader.getDataFromExcelFile("\\src\\test\\resources\\searchOnAmazon.xlsx", 0);
        for (int i = 1; i<items.length; i++){
            Thread.sleep(1500);
            searchBox.sendKeys(items[i]);
            Thread.sleep(1500);
            searchBox.clear();
            Thread.sleep(1500);
        }
    }
}

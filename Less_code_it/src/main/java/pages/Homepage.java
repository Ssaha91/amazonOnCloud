package pages;

import base.CommonAPI;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Homepage extends CommonAPI {

    Actions action = new Actions(driver);

    @FindBy(id = "openwindow")
    WebElement openWindowBtn;

    @FindBy(id = "search-courses")
    WebElement searchCoursesField;

    @FindBy(id = "name")
    WebElement nameField;

    @FindBy(id = "alertbtn")
    WebElement alertBtn;

    @FindBy(id = "confirmbtn")
    WebElement confirmBtn;

    @FindBy(id = "search-courses")
    WebElement secondFrame;

    @FindBy(id = "mousehover")
    WebElement mousehoverBtn;

    @FindBy(id = "bmwradio")
    WebElement bmwRadioButton;

    @FindBy(id = "benzradio")
    WebElement benzRadioButton;

    @FindBy(id = "hondaradio")
    WebElement hondaRadioButton;

    @FindBy (xpath = ".//input [@id='bmwcheck']")
    WebElement bmwCheckbox;

    @FindBy (xpath = ".//input[@id='benzcheck']")
    WebElement benzCheckbox;

    @FindBy (xpath = ".//input[@id='honjjjdacheck']")
    WebElement hondaCheckbox;

    @FindBy (xpath = ".//input[@type='radio']")
    List<WebElement> listOfRadioButton;

    public void randomGeneratorTest() {
        hondaCheckbox.sendKeys("blbhh");
//        try {
//            nameField.sendKeys(randomNumberseGenerator());
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void checkListOfRadioBtn() throws InterruptedException {
        boolean isChacked = false;
        int size = listOfRadioButton.size();
        System.out.println("size of the radio button " + size);
        for (int i=0; i<size; i++){
                isChacked = listOfRadioButton.get(i).isSelected();

                if (!isChacked){
                    listOfRadioButton.get(i).click();
                    Thread.sleep(3000);
                }
        }
    }
    public void checkCheckBox() throws InterruptedException {
       bmwCheckbox.click();
       Thread.sleep(2000);
       benzCheckbox.click();
       Thread.sleep(2000);
       hondaCheckbox.click();
        Thread.sleep(2000);
    }

    public void checkRadioBtn() throws InterruptedException {
        bmwRadioButton.click();
        Thread.sleep(2000);
        benzRadioButton.click();
        Thread.sleep(2000);
        hondaRadioButton.click();
        Thread.sleep(2000);
        System.out.println("bmw radio button is selected? " + bmwRadioButton.isSelected());
        System.out.println("bmw radio button is selected? " + benzRadioButton.isSelected());
        System.out.println("bmw radio button is selected? " + hondaRadioButton.isSelected());
    }

    public void checkMouseHoverOver() throws InterruptedException {
        action.moveToElement(mousehoverBtn).build().perform();
        Thread.sleep(2000);
    }
    public void frameDemo(String input) throws InterruptedException {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0, -400)");

        String handle = driver.getWindowHandle();
        Set<String> windowHandle = driver.getWindowHandles();

        driver.switchTo().frame("courses-iframe");

        Thread.sleep(2000);
        secondFrame.sendKeys(input);
        Thread.sleep(2000);

        driver.switchTo().defaultContent();
        Thread.sleep(2000);
    }

    public void alertDemo(String input) throws InterruptedException {
        nameField.sendKeys(input);
        alertBtn.click();
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();
        Thread.sleep(2000);
        alert.accept();
        Thread.sleep(2000);

    }

    public void confirmDemo(String name, String input) throws InterruptedException {
        nameField.sendKeys(name);

        confirmBtn.click();
        if(input.equalsIgnoreCase("ok")) {
            Thread.sleep(2000);
            Alert alert = driver.switchTo().alert();
            Thread.sleep(2000);
            alert.accept();
            Thread.sleep(2000);
        } else if (input.equalsIgnoreCase("cancel")) {
            Thread.sleep(2000);
            Alert alert = driver.switchTo().alert();
            Thread.sleep(2000);
            alert.dismiss();
            Thread.sleep(2000);
        } else {
            System.out.println("Enter Valid Input");
        }
    }

    public void test() throws InterruptedException {
        openWindowBtn.click();
        Thread.sleep(2000);
        String parent = driver.getWindowHandle();
        Set<String> s1 = driver.getWindowHandles();

        Iterator<String> I1 = s1.iterator();
        while(I1.hasNext()) {
            String childWindow = I1.next();
            if(!parent.equals(childWindow)) {
                driver.switchTo().window(childWindow);
                Thread.sleep(1000);
                searchCoursesField.sendKeys("Java");
                Thread.sleep(1000);
                searchCoursesField.clear();
                Thread.sleep(1000);
                searchCoursesField.sendKeys("Python");
                Thread.sleep(1000);
                driver.close();
            }
        }
        Thread.sleep(1000);
        driver.switchTo().window(parent);
        Thread.sleep(2000);
    }

}

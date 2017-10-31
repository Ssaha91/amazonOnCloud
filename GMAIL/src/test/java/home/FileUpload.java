package home;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

public class FileUpload extends CommonAPI {

    @Test
    public void testfileUpload() throws Exception {

        driver.findElement(By.id("identifierId")).sendKeys("saha4311@gmail.com");
        driver.findElement(By.id("identifierNext")).click();
        driver.findElement(By.name("password")).sendKeys("No171717");
        driver.findElement(By.id("passwordNext")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[text()='COMPOSE']")).click();
        driver.findElement(By.xpath("//textarea[@aria-label='To']")).sendKeys("ibrahimkhan0894@gmail.com");
        driver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys("Test File Upload");

        Thread.sleep(2000);

        WebElement fileInput = driver.findElement(By.xpath("//div[@aria-label='Attach files']"));
        fileInput.click();

        Thread.sleep(2000);

        StringSelection ss = new StringSelection("C:\\Users\\saha4\\Desktop\\Selenium");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[contains(text(), 'Send')]")).click();
    }
}

package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MultiTest {
    static WebDriver driver;
    public static void clickOnElement(By by) {
        driver.findElement(by).click();
    }

    public static void typeText(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    public static String getTextFromElement(By by) {
        return driver.findElement(by).getText();
    }

    public static String currentTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        return sdf.format(date);
    }

    public static void waitForClickable(By by, int timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver,timeInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static void waitForVisible(By by, int timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver,timeInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @BeforeMethod
    public void openBrowser() {
        System.out.println(currentTimeStamp());
        System.setProperty("webdriver.chrome.driver","src/test/Drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/");
    }

@Test
    public void verifyUserShouldBeAbleToRegisterSuccessfully() {
    clickOnElement(By.xpath("//a[@class='ico-register']"));//Click on the Register button.
    typeText(By.name("FirstName"), "Shital");//Type name in the firstname textbox.
    typeText(By.name("LastName"), "Mehta");//Type surname in the lastname textbox.
    Select selectDay = new Select(driver.findElement(By.name("DateOfBirthDay")));//select date
    selectDay.selectByVisibleText("27");
    Select selectMonth = new Select(driver.findElement(By.name("DateOfBirthMonth")));//select month
    selectMonth.selectByValue("5");
    Select selectYear = new Select(driver.findElement(By.name("DateOfBirthYear")));//select year
    selectYear.selectByValue("1989");
    String email = "shitalm7+" + currentTimeStamp() + "@gmail.com";//type email address to get registered.
    System.out.println(email);
    typeText(By.id("Email"),(email));
    waitForVisible(By.id("Newsletter"), 20);
    clickOnElement(By.id("Newsletter"));
    typeText(By.id("Password"), "shital.123");//type password in the text box
    typeText(By.id("ConfirmPassword"), "shital.123");//type confirm password in the textbox
    waitForClickable(By.name("register-button"), 10);//waiting for the button to get clicked
    clickOnElement(By.name("register-button"));//click the button
    //getting the text
    String actualRegisterSuccessMessage = getTextFromElement(By.xpath("//div[@class='result']"));
    String expectedRegisterSuccessMessage = "Your registration completed";//This text is Expected
    //Verifying the actual result with the Expected result.
    Assert.assertEquals (actualRegisterSuccessMessage, expectedRegisterSuccessMessage);
}
@Test
     public void verifyUserIsAbleToNavigateToDesktopPage()
{
        clickOnElement(By.xpath("//div[@class='header-menu']/ul[1]/li[1]/a"));//Click on Computer Menu
        clickOnElement(By.xpath("//div[@class='listbox']/ul[1]/li[1]/ul[1]/li[1]/a"));//Click on Desktops
        waitForVisible(By.xpath("//div[@class='page-title']/h1"), 40);// Asked the browser to wait for secs.
        //Getting the text
        String actualPageTitle = getTextFromElement(By.xpath("//div[@class='page-title']/h1"));
        String expectedPageTitle = "Desktops"; //This text is Expected
        //Verifying the Actual result with the Expected result
        Assert.assertEquals(actualPageTitle , expectedPageTitle);
}
@Test
    public void verifyUserCommentIsSuccessfullyAdded()
{
        clickOnElement(By.xpath("//div[@class='news-items']/div[2]/div[3]/a"));//Click on Details button.
        typeText(By.id("AddNewComment_CommentTitle"),"Test Cases");//Typing in the title text-box
        typeText(By.id("AddNewComment_CommentText"), "This is automation testing");//typing in the Comment text-box
        clickOnElement(By.xpath("//div[@class='buttons']/button[1]"));//Clicks on the Comment button
        //Getting the Text
        String actualSuccessfulMessage = getTextFromElement(By.xpath("//div[@class='result']"));
        String expectedSuccessfulMessage = "News comment is successfully added.";//This text is Expected
        //Verifying the Actual result with the Expected result
        Assert.assertEquals(actualSuccessfulMessage , expectedSuccessfulMessage);
}
@Test
     public void verifyRegisteredUserShouldBeAbleToReferAProductToAFriend()
{
        verifyUserShouldBeAbleToRegisterSuccessfully();//Register a user successfully
        verifyUserIsAbleToNavigateToDesktopPage();//user navigates to desktop page
        //click on build your own computer
        clickOnElement(By.xpath("//div[@class='item-grid']/div[1]/div[1]/div[2]/h2/a"));
        //click on EmailAFriend button
        clickOnElement(By.xpath("//div[@class='email-a-friend']/button[1]"));
        //Type Friend's email
        typeText(By.id("FriendEmail"),"automation1@gmail.com");
        //type the Personal Message
        typeText(By.id("PersonalMessage"),"Hello I recommend this product to you");
        //Clicks the send Email Button
        clickOnElement(By.xpath("//div[@class='buttons']/button"));
        //Getting the text
        String actualEmailSentMessageConfirmation = getTextFromElement(By.xpath("//div[@class='result']"));
        String expectedEmailSentMessageConfirmation = "Your message has been sent.";//This text is expected.
        //Verifying the actual result = expected result
        Assert.assertEquals(actualEmailSentMessageConfirmation, expectedEmailSentMessageConfirmation);
}
    @AfterMethod
    public void closeBrowser()
    {
        driver.close();//closes the Browser
    }
}
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ReadEmails {

    static By loginField = By.xpath("//input[@name='ulogin']");
    static By passwordField = By.xpath("//input[@name='pass']");
    static By loginButton = By.xpath("//input[@class='nobrd']");
    static By lettersBlock = By.xpath("//a[contains(text(),'Письма')]");
    static By tasksBlock = By.xpath("//a[contains(text(),'Задания')]");
    static By startTaskButton = By.xpath("//body//input[4]");
    static By confirmTaskButton = By.xpath("//form[1]//input[3]");
    static By confirmField = By.xpath("//input[@id='zdtext']");
    static By sendButton = By.xpath("//tr[@class='line']//td//input");

    static By firstLetter = By.xpath("/html[1]/body[1]/div[2]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/table[4]/tbody[1]/tr[1]/td[2]/table[1]/tbody[1]/tr[2]/td[2]/a[1]");
    static By blueLink = By.xpath("//tr[4]//td[1]/a[1]");
    //static By blueLink = By.xpath("/html/body/div[2]/div/table/tbody/tr/td/table[4]/tbody/tr[1]/td[2]/table/tbody/tr[3]/td/a");
    static By selectFrame = By.cssSelector("html > frameset:nth-child(2) > frame:nth-child(1)");
    static By Button1x = By.xpath("/html/body/center/table/tbody/tr[1]/td[1]/table/tbody/tr[1]/td[1]/a[1]");
    static By Button2x = By.xpath("/html[1]/body[1]/center[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/a[2]");
    static By Button3x = By.xpath("/html[1]/body[1]/center[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/a[3]");
    static By Button4x = By.xpath("/html[1]/body[1]/center[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/a[4]");
    static By Button5x = By.xpath("/html[1]/body[1]/center[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/a[5]");
    static By successMoneyMessage = By.xpath("//strong[contains(text(),'Деньги зачислены')]");

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "/Users/ivan/Projects/Drivers/geckodriver");
        System.setProperty("webdriver.chrome.driver", "/Users/ivan/Projects/Drivers/chromedriver");
        //WebDriver driver = new FirefoxDriver();
        ChromeOptions ChromeOptions = new ChromeOptions();
        ChromeOptions.addArguments("" +
                "--headless",
                "window-size=1400,900", "--no-sandbox");
        WebDriver driver = new ChromeDriver(ChromeOptions);
        //WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://wmmail.ru/");

        //driver.findElement(loginField).sendKeys("visitor81");// visitor81 iwansch
        driver.findElement(loginField).sendKeys("iwansch");// visitor81 iwansch
        //driver.findElement(passwordField).sendKeys("visitorrotisiv"); // 42677797351 visitorrotisiv
        driver.findElement(passwordField).sendKeys("42677797351"); // 42677797351 visitorrotisiv
        driver.findElement(loginButton).click();
        driver.findElement(lettersBlock).click();

        boolean stop;
        boolean success;
        try {driver.findElement(firstLetter); stop = false; } catch (Exception e) {stop = true;}

        while (!stop){
            driver.findElement(firstLetter).click();
            //JavascriptExecutor js = ((JavascriptExecutor) driver);
            //js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            driver.findElement(blueLink).click();
            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

            for (String tab : driver.getWindowHandles()) {driver.switchTo().window(tab);}//Переключение на активный таб в браузере
            driver.manage().timeouts().implicitlyWait(65, TimeUnit.SECONDS);// Ждем отсчета l сек
            driver.switchTo().frame(driver.findElement(selectFrame));//Переключение на фрейм с цифрами

            try {driver.findElement(successMoneyMessage); success = false; } catch (Exception e) {success = true;}

            if (!success) {
                driver.close();
                for (String tab : driver.getWindowHandles()) {driver.switchTo().window(tab);}
                driver.findElement(lettersBlock).click();
            }
            else {
                driver.findElement(Button1x).click();
                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                driver.close();
                for (String tab : driver.getWindowHandles()) {driver.switchTo().window(tab);}
                driver.findElement(lettersBlock).click();
            }
            System.out.println("A letter was read");
            try {driver.findElement(firstLetter); stop = false;} catch (Exception e) {stop = true;}
        }
        System.out.println("All the letters are read");
        driver.findElement(tasksBlock).click();
        driver.get("http://wmmail.ru/index.php?cf=uzd-readtask&zdid=1601953");
        try {
            driver.findElement(startTaskButton).click();
            driver.findElement(confirmTaskButton).click();
            driver.findElement(confirmField).sendKeys("Спасибо");
            driver.findElement(sendButton).click();
        } catch (Exception e) {
            System.out.println("The task is not available");
        }
        driver.quit();
    }
}

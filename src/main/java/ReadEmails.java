import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class ReadEmails {

    static By loginField = By.xpath("//input[@name='ulogin']");
    static By passwordField = By.xpath("//input[@name='pass']");
    static By loginButton = By.xpath("//input[@class='nobrd']");
    static By lettersBlock = By.xpath("//a[contains(text(),'Письма')]");
    static By firstLetter = By.xpath("/html[1]/body[1]/div[2]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/table[4]/tbody[1]/tr[1]/td[2]/table[1]/tbody[1]/tr[2]/td[2]/a[1]");
    static By blueLink = By.xpath("//tr[4]//td[1]/a[1]");
    static By selectFrame = By.cssSelector("html > frameset:nth-child(2) > frame:nth-child(1)");
    static By Button1x = By.xpath("/html/body/center/table/tbody/tr[1]/td[1]/table/tbody/tr[1]/td[1]/a[1]");
    static By Button2x = By.xpath("/html[1]/body[1]/center[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/a[2]");
    static By Button3x = By.xpath("/html[1]/body[1]/center[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/a[3]");
    static By Button4x = By.xpath("/html[1]/body[1]/center[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/a[4]");
    static By Button5x = By.xpath("/html[1]/body[1]/center[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/a[5]");
    static By successMoneyMessage = By.xpath("//strong[contains(text(),'Деньги зачислены')]");

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "/Users/ivan/Downloads/task/testUA/Drivers/geckodriver");
        System.setProperty("webdriver.chrome.driver", "/Users/ivan/Downloads/task/testUA/Drivers/chromedriver");
        //WebDriver driver = new FirefoxDriver();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://wmmail.ru/");

        driver.findElement(loginField).sendKeys("iwansch");
        driver.findElement(passwordField).sendKeys("42677797351");
        driver.findElement(loginButton).click();
        driver.findElement(lettersBlock).click();

        boolean stop;
        boolean success;
        try {driver.findElement(firstLetter); stop = false; } catch (Exception e) {stop = true;}

        while (!stop){
            driver.findElement(firstLetter).click();
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
            try {driver.findElement(firstLetter); stop = false;} catch (Exception e) {stop = true;}
        }
        System.out.println("All the letters are read");
        driver.quit();
    }
}

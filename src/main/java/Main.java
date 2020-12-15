import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static final ArrayList<String> windows = new ArrayList<>();
    private static final WebDriver driver = new ChromeDriver();
    private static final JSONParser parser = new JSONParser();
    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
//        Set chromedriver location and access website
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        driver.get("http://www.tinder.com/");

//        Call login function
        login(driver);

//        Find like button
        WebElement like = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div/main/div[1]/div/div/div[1]/div/div[2]/div[4]/button"));

//        Auto swipe function
        auto_swipe(driver, like);

    }

    public static void login(WebDriver driver) throws InterruptedException, IOException, ParseException {
//        Find and select login buttons and choose facebook login
        WebElement login = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div/main/div[1]/div/div/div/div/header/div/div[2]/div[2]/button/span"));
        login.click();

        Thread.sleep(500);

        WebElement fb_log = driver.findElement(By.xpath("//*[@id=\"modal-manager\"]/div/div/div[1]/div/div[3]/span/div[2]/button"));
        fb_log.click();

//        Retrieve authentication credentials from credentials.json
        JSONObject credentials = (JSONObject) parser.parse(new FileReader("src/Main/java/credentials.json"));

//        Store all windows in the arraylist and switch the the facebook login
        for (String currentWindow : driver.getWindowHandles()) {
            driver.switchTo().window(currentWindow);
            windows.add(currentWindow);
        }

        Thread.sleep(500);

//        find input fields and send credentials
        WebElement user_in = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/form/div/div[1]/div/input"));
        user_in.sendKeys((String) credentials.get("Username"));
        WebElement pass_in = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/form/div/div[2]/div/input"));
        pass_in.sendKeys((String) credentials.get("Password"));

        WebElement submit_log = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/form/div/div[3]/label[2]/input"));
        submit_log.click();

//        Switch back to base window
        driver.switchTo().window(windows.get(0));
        Thread.sleep(10000);

//        Get passed misc popups (location, cookies, notifications etc)
        WebElement allow_location = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[3]/button[1]"));
        allow_location.click();
        Thread.sleep(500);

        WebElement notifications = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[3]/button[1]"));
        notifications.click();

        WebElement cookies_accept = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div[1]/button"));
        cookies_accept.click();

//        Wait to load main page
        Thread.sleep(5000);

    }

    public static void auto_swipe(WebDriver driver, WebElement like) throws InterruptedException {
        while (true) {
            try {
                like.click();
            } catch (Exception e) {
                try {
                    driver.findElement(By.xpath("/html/body/div[2]/div/div/button[2]")).click();
                } catch (Exception ioe) {
                    try{
                        driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/button[2]")).click();
                    } catch (Exception match){
                        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/main/div[2]/div/div/div[1]/div/div[4]/button"));
                    }
                }
            }
            Thread.sleep(250);
        }
    }
}

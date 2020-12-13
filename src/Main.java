import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//       Create arraylist to store windows (facebook login etc)
        ArrayList<String> windows = new ArrayList<>();

//        Set up chrome driver
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.tinder.com/");

//        Select login buttons and choose facebook login
        WebElement login = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div/main/div[1]/div/div/div/div/header/div/div[2]/div[2]/button/span"));
        login.click();
        Thread.sleep(500);
        WebElement fb_log = driver.findElement(By.xpath("//*[@id=\"modal-manager\"]/div/div/div[1]/div/div[3]/span/div[2]/button"));
        fb_log.click();

//        Store authentication credentials in a string
        String user = "";
        String pass = "";

//        Store all windows in the arraylist and switch the the facebook login
        for(String currentWindow : driver.getWindowHandles()){
            driver.switchTo().window(currentWindow);
            windows.add(currentWindow);
        }
        Thread.sleep(500);

//        find input fields and send credentials
        WebElement user_in = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/form/div/div[1]/div/input"));
        user_in.sendKeys(user);
        WebElement pass_in = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/form/div/div[2]/div/input"));
        pass_in.sendKeys(pass);

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

//        Wait to load page
        Thread.sleep(5000);

//        Find like button
        WebElement like = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div/main/div[1]/div/div/div[1]/div/div[2]/div[4]/button"));


//        Infinite swiping, catching exceptions such as tinder popups including Super like, Adding tinder to home page and out of matches
        while(true){
            try{
                like.click();
            } catch (Exception e){
                try{
                    WebElement supa = driver.findElement(By.xpath("/html/body/div[2]/div/div/button[2]"));
                    supa.click();
                } catch (Exception ioe){
                    driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/button[2]")).click();
                    try{
                        like.click();
                    }
                    catch (Exception out_of_matches){
                        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/main/div/div[1]/div/div[1]/div[3]/button")).click();
                    }
                }

            }
            Thread.sleep(250);
        }

    }
}

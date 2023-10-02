package search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class SetUp {


    protected WebDriver driver;


    @BeforeAll
//    public static void SetProperty() {
//        System.setProperty("webdriver.Chrome.driver",
//                "C:\\Users\\Public\\webDriver\\chromedriver-win64\\chromedriver.exe");
//    }

    //из системной переменной
    public static void SetProperty() {
 System.setProperty("webdriver.Chrome.driver",
         System.getenv("CHROMEDRIVER"));

}

    @BeforeEach
    public void setDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
    }
        @AfterEach
        public void closeTest() {
            driver.quit();
        }
   }


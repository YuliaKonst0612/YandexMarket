package searchTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ConfigReader;

import java.util.concurrent.TimeUnit;

public class SetUp {


        protected WebDriver driver;


        @BeforeAll
        public static void SetProperty() {
            System.setProperty("webdriver.Chrome.driver",
                    "C:\\Users\\Public\\webDriver\\chromedriver-win64\\chromedriver.exe");
        }

        @BeforeEach
        public void setDriver() {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(40, TimeUnit.SECONDS);
        }

        @AfterEach
        public void closeTest() {
            driver.quit();
        }
   }


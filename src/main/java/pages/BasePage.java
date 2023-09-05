package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {
    private WebDriver driver;


    public BasePage(WebDriver driver) {
        this.driver = driver;

    }

    @Step("Клик на каталог")
    /**
     * Метод кликает на кнопку "Каталог" на главной странице Яндекс Маркет
     * Автор: [Юлия Константинова]
     */
    public void clickCatalogLink() {
        WebElement katalog = driver.findElement(By.xpath("//button[.//span[text()='Каталог']]"));
        katalog.click();
    }

}
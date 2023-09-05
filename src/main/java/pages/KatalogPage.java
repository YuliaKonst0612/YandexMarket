package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class KatalogPage {
    private WebDriver driver;
    private WebElement cathegory;

    public KatalogPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    /**
     * Метод имитирует наведение мыши на раздел "Ноутбуки и компьютеры в каталоге
     * Автор: [Юлия Константинова]
     */
    public void chooseCategory() {
        Actions actions = new Actions(driver);
        cathegory = driver.findElement(By.xpath("//a/span[text()='Ноутбуки и компьютеры']"));
        //WebElement cathegory = driver.findElement(By.xpath("//a/span[text()='Ноутбуки и компьютеры']"));
        actions.moveToElement(cathegory).perform();
    }

    @Step
    /**
     * Метод кликает на подкатегорию "Ноутбуки" в разделе "Ноутбуки и компьютеры" и проверяет, что переход осуществлен
     * Автор: [Юлия Константинова]
     */
    public void moveToCathegory() {
        WebElement subcathegory = driver.findElement(By.xpath("//a[text()='Ноутбуки']"));
        subcathegory.click();
        WebElement notebookSectionTitle = driver.findElement(By.xpath("//h1[text()='Ноутбуки']"));
        Assertions.assertTrue(notebookSectionTitle.isDisplayed(), "Не удалось перейти в раздел 'Ноутбуки'");
    }

}

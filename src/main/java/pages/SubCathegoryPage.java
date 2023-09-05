package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SubCathegoryPage {

    private WebDriver driver;
    private int minPrice;
    private int maxPrice;
    private String manufacturer1;
    private String manufacturer2;

    public SubCathegoryPage(WebDriver driver, int minPrice, int maxPrice) {
        this.driver = driver;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.manufacturer1 = "Lenovo";
        this.manufacturer2 = "HP";

    }

    private String noteBookName;

    public SubCathegoryPage(String noteBookName) {
        this.noteBookName = noteBookName;
    }

    @Step("Установка фильтра по цене")
    /**
     * Метод устанавливает фильтры по цене
     * Автор: [Юлия Константинова]
     */
    public void setPriceFilter() {
        WebElement minPriceInput = driver.findElement(By.xpath("(//div[@data-grabber='SearchFilters']" +
                "//child::input[contains(@id,'min')])[1]"));
        WebElement maxPriceInput = driver.findElement(By.xpath("(//div[@data-grabber='SearchFilters']" +
                "//child::input[contains(@id,'max')])[1]"));

        minPriceInput.sendKeys(String.valueOf(minPrice));
        maxPriceInput.sendKeys(String.valueOf(maxPrice));
    }

    @Step("Выбор производителя 1")
    /**
     * Метод устанавливает производителя 1
     * Автор: [Юлия Константинова]
     */
    public void selectManufacturer1(String manufacturer) {
        WebElement manufacturerElement = driver.findElement(By.xpath("//span[text()='" + manufacturer + "']"));
        manufacturerElement.click();
    }

    @Step("Выбор производителя 2")
    /**
     * Метод устанавливает производителя 2
     * Автор: [Юлия Константинова]
     */
    public void selectManufacturer2(String manufacturer) {
        WebElement manufacturerElement = driver.findElement(By.xpath("//span[text()='" + manufacturer + "']"));
        manufacturerElement.click();
    }

    @Step("Проверка на количество элементов в результатах поиска")
    /**
     * Метод проверяет, что в списке результатов поиска более 12 товаров 
     * Автор: [Юлия Константинова]
     */
    public void assertMoreThan12ItemsDisplayed() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-test-id='virtuoso-item-list']" +
                "//descendant::*[@data-index]")));

        System.out.println(items.size());

        boolean result = items.size() > 12;
        Assertions.assertTrue(result, "Менее 12 элементов представлены на странице");
    }

    @Step("Проверка на соответствие результатов поиска фильтрам ")
/**
 * Метод проверяет, что результаты поиска соответствуют фильтрам
 * Автор: [Юлия Константинова]
 */
    public void testFilterOnAllPages() {
        List<WebElement> resultSearch = driver.findElements(By.xpath("//div[@data-test-id='virtuoso-item-list']"));
        boolean allMatch = true;

        for (WebElement element : resultSearch) {
            String elementText = element.getText();
            if (elementText.contains(manufacturer1) && elementText.contains(manufacturer2)) {
                if (!isPriceInRange(element, minPrice, maxPrice)) {
                    allMatch = false;
                    break; // Прекратить проверку при первом несоответствии
                }
            }
        }

        Assertions.assertTrue(allMatch, "Товары не соответствуют фильтрам");
    }
    /**
     * Метод проверяет, что результаты поиска соответствуют фильтру по цене
     * Автор: [Юлия Константинова]
     */
    private boolean isPriceInRange(WebElement element, double minPrice, double maxPrice) {
        String priceText = element.findElement(By.xpath("//div[@data-baobab-name='price']"))
                .getText();
        double price = Double.parseDouble(priceText.replaceAll("[^0-9.]+", ""));
        return price >= minPrice && price <= maxPrice;
    }


    @Step("Переход на следующую страницу")
    /**
     * Метод переходит на следующую страницу
     * Автор: [Юлия Константинова]
     */
    public boolean goToNextPage() {
        WebElement nextPageButton = driver.findElement(By.xpath("//div[@data-baobab-name='next']"));

        if (nextPageButton.isEnabled()) {
            nextPageButton.click();
            return true; // Переход выполнен успешно
        } else {
            return false; // Нет больше страниц
        }
    }

    @Step("Переход на первую страницу")
    /**
    * Метод переходит на первую страницу
    * Автор: [Юлия Константинова]
    */
    public void goToFirstPage() {
        WebElement firstPageButton = driver.findElement(By.xpath("//div[@data-auto='pagination-page' and text()='1']"));
        firstPageButton.click();
    }

    @Step("Запомнить и ввести в поисковую строку название первого элемента в поиске")
    /**
     * Метод берет первый элемент из списка товаров
     * и вводит его в поисковую строку
     * Автор: [Юлия Константинова] 
     */
    public String getFirstname(WebDriver driver) {
        List<WebElement> items = driver.findElements(By.xpath("//div[@data-test-id='virtuoso-item-list']//descendant::*[@data-index]"));
        WebElement firstItem = items.get(1);
        String noteBookName = firstItem.findElement(By.xpath("//*[@data-auto='snippet-title-header']")).getText();
        System.out.println("Название ноутбука: " + noteBookName);

        WebElement searchInput = driver.findElement(By.xpath("//input[@name='text']"));
        searchInput.sendKeys(noteBookName);
        return noteBookName;

    }

    @Step("Нажать кнопку 'найти'")
    /**
     * Метод нажимает на кнопку "Найти"
     * Автор: [Юлия Константинова] 
     */
    public void findButtonClick() {
        WebElement findButton = driver.findElement(By.xpath("//button[@data-auto='search-button']"));
        findButton.click();

    }

    @Step("Проверка наличия искомого товара в результатах поиска")
    /**
     * Метод проверяет, что в списке результатов поиска присутствует товар, который был взят в методе getFirstname
     * Автор: [Юлия Константинова] 
     */
    public void verifySearch(WebDriver driver, String notebookName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-test-id='virtuoso-item-list']")));
        boolean itemFound = items.stream()
                .anyMatch(result -> result.getText().contains(notebookName));

        Assertions.assertTrue(itemFound, "Искомый товар не найден в результатах поиска.");
    }
}
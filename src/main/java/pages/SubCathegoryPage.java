package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import parameters.TestParameters;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SubCathegoryPage {

    private WebDriver driver;

    public SubCathegoryPage(WebDriver driver) {
        this.driver = driver;
    }

     private String itemName;

    public SubCathegoryPage(String itemName) {
        this.itemName = itemName;
    }

    @Step("Установка фильтра по цене")
    /**
     * Метод устанавливает фильтры по цене
     * Автор: [Юлия Константинова]
     */
    public void setPriceFilter(int minPrice, int maxPrice) {
        WebElement minPriceInput = driver.findElement(By.xpath("(//div[@data-grabber='SearchFilters']" +
                "//child::input[contains(@id,'min')])[1]"));
        WebElement maxPriceInput = driver.findElement(By.xpath("(//div[@data-grabber='SearchFilters']" +
                "//child::input[contains(@id,'max')])[1]"));

        minPriceInput.sendKeys(String.valueOf(minPrice));
        maxPriceInput.sendKeys(String.valueOf(maxPrice));
    }


    @Step("Выбор производителей '{manufacturers}'")
    public void selectManufacturers(List<String> manufacturers) {
        for (String manufacturer : manufacturers) {
            WebElement manufacturerElement = driver.findElement(By.xpath("//span[text()='" + manufacturer + "']"));
            manufacturerElement.click();
        }
    }
    @Step("Проверка на количество элементов в результатах поиска более '{expectedItemCount}'")
    /**
     * Метод проверяет количество элементов в результатах поиска
     * Автор: [Юлия Константинова]
     */
    public void assertNumberOfItemsDisplayed(int expectedItemCount) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-test-id='virtuoso-item-list']" +
                "//descendant::*[@data-index]")));

        System.out.println(items.size());

        boolean result = items.size() > expectedItemCount;
        Assertions.assertTrue(result, "Менее" + expectedItemCount+ "элементов представлены на странице");
    }

    @Step("Проверка на соответствие результатов поиска фильтрам '{manufacturers}','{minPrice}', '{maxPrice}' ")
/**
 * Метод проверяет, что результаты поиска соответствуют фильтрам по цене и производителю
 * Автор: [Юлия Константинова]
 */

    public boolean verifyProductsMatchingFiltersOnAllPages(int minPrice, int maxPrice, List<String> manufacturers) {
        boolean allProductsMatchFilters = true; // Изначально предполагаем, что все товары соответствуют фильтрам

        while (goToNextPage()) {
            List<TestParameters> productsOnCurrentPage = getProductsOnCurrentPage(); // Получить товары на текущей странице

            // Проверить, что все товары на текущей странице соответствуют фильтру по производителю
            boolean productsByManufacturerMatchFilters = productsOnCurrentPage.stream().allMatch(product ->
                    manufacturers.contains(product.getManufacturer())
            );

            // Проверить, что все товары на текущей странице соответствуют фильтру по цене
            boolean productsByPriceMatchFilters = productsOnCurrentPage.stream().allMatch(product ->
                    product.getPrice() >= minPrice && product.getPrice() <= maxPrice
            );

            // Если хотя бы один товар на странице не соответствует фильтру по производителю, устанавливаем флаг в false и выдаем ошибку
            if (!productsByManufacturerMatchFilters) {
                allProductsMatchFilters = false;
                Assertions.fail("На текущей странице найден товар, не соответствующий фильтру по производителям" + manufacturers);
                break; // Прервать проверку на следующих страницах
            }

            // Если хотя бы один товар на странице не соответствует фильтру по цене, устанавливаем флаг в false и выдаем ошибку
            if (!productsByPriceMatchFilters) {
                allProductsMatchFilters = false;
                Assertions.fail("На текущей странице найден товар, не соответствующий фильтру по цене");
                break; // Прервать проверку на следующих страницах
            }

            // Переход на следующую страницу (если есть)
            goToNextPage();
        }

         return true;
    }
    /**
     * Метод Получает список товаров на текущей странице и возвращает их в виде списка объектов TestParameters.
     * Содержит информацию о производителе и цене.
     * Автор: [Юлия Константинова]
     */
    private List<TestParameters> getProductsOnCurrentPage() {
        List<TestParameters> products = new ArrayList<>();
        List<WebElement> items = driver.findElements(By.xpath("//div[@data-test-id='virtuoso-item-list']//descendant::*[@data-index]"));
        for (WebElement productElement : items) {

            String productManufacturer = productElement.findElement(By.xpath("//h3[ @data-auto='snippet-title-header']")).getText();
          String priceText = productElement.findElement(By.xpath("//h3[ @data-auto='price-block']"))
                    .getText();
            double price = Double.parseDouble(priceText.replaceAll("[^0-9.]+", ""));
            TestParameters productInfo = new TestParameters(productManufacturer,price) ;
            products.add(productInfo);
        }

        return products;
    }


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
        goToNextPage();
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
        String itemName = firstItem.findElement(By.xpath("//*[@data-auto='snippet-title-header']")).getText();
        System.out.println("Название ноутбука: " + itemName);

        WebElement searchInput = driver.findElement(By.xpath("//input[@name='text']"));
        searchInput.sendKeys(itemName);
        return itemName;

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

    @Step("Проверка наличия искомого товара '{itemName}' в результатах поиска")
    /**
     * Метод проверяет, что в списке результатов поиска присутствует товар, который был взят в методе getFirstname
     * Автор: [Юлия Константинова] 
     */
    public void verifySearch(WebDriver driver, String itemName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-test-id='virtuoso-item-list']")));
        boolean itemFound = items.stream()
                .anyMatch(result -> result.getText().contains(itemName));

        Assertions.assertTrue(itemFound, "Товар" + itemName+ "не найден в результатах поиска.");
    }
}
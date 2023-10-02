package search;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.MethodSource;
import pages.BasePage;
import config.ConfigReader;
import pages.KatalogPage;
import pages.SubCathegoryPage;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.List;

public class NoteBookSearchTest extends SetUp {


    @ParameterizedTest
    @MethodSource("search.tests.data.provider.DataProvider#provideTestData")
    @Feature("Выбор товаров Yandex market")
    @DisplayName("Проверка выбора товаров")
    /**
     * Метод Запускает тестовые шаги
     * Автор: [Юлия Константинова]
     */
    public void runTest(int minPrice, int maxPrice, List<String> manufacturers, int expectedItemCount) {

        BasePage basePage = new BasePage(driver);
        KatalogPage katalogPage = new KatalogPage(driver);
        SubCathegoryPage subCathegoryPage = new SubCathegoryPage(driver);
        String baseUrl = ConfigReader.getProperty("baseUrl");

        driver.get(baseUrl);
        basePage.clickCatalogLink();
        katalogPage.chooseCategory();
        katalogPage.moveToCathegory();
        subCathegoryPage.setPriceFilter(minPrice, maxPrice);
        subCathegoryPage.selectManufacturers(manufacturers);
        subCathegoryPage.assertNumberOfItemsDisplayed(expectedItemCount);
        subCathegoryPage.verifyProductsMatchingFiltersOnAllPages(minPrice, maxPrice, manufacturers);
        subCathegoryPage.goToFirstPage();
        String itemName = subCathegoryPage.getFirstname(driver);
        subCathegoryPage.findButtonClick();
        subCathegoryPage.verifySearch(driver, itemName);
    }

}
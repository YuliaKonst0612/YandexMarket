package searchTests;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import pages.BasePage;
import pages.ConfigReader;
import pages.KatalogPage;
import pages.SubCathegoryPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class NoteBookSearchTest extends SetUp {


    @ParameterizedTest
    @CsvSource({"Lenovo, HP"})
    @Feature("Выбор товаров Yandex market")
    @DisplayName("Проверка выбора товаров")
    /**
     * Метод Запускает тестовые шаги
     * Автор: [Юлия Константинова]
     */
    public void runTest(String manufacturer1, String manufacturer2) {

        BasePage basePage = new BasePage(driver);
        KatalogPage katalogPage = new KatalogPage(driver);
        SubCathegoryPage subCathegoryPage = new SubCathegoryPage(driver, 10000, 90000);
        String baseUrl = ConfigReader.getProperty("baseUrl");

        driver.get(baseUrl);
        basePage.clickCatalogLink();
        katalogPage.chooseCategory();
        katalogPage.moveToCathegory();
        subCathegoryPage.setPriceFilter();
        subCathegoryPage.selectManufacturer1(manufacturer1);
        subCathegoryPage.selectManufacturer2(manufacturer2);
        subCathegoryPage.assertMoreThan12ItemsDisplayed();
        subCathegoryPage.testFilterOnAllPages();
        subCathegoryPage.goToNextPage();
        subCathegoryPage.goToFirstPage();
        String notebookName = subCathegoryPage.getFirstname(driver);
        subCathegoryPage.findButtonClick();
        subCathegoryPage.verifySearch(driver, notebookName);
    }

}
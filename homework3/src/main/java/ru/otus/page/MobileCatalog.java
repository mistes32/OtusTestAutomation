package ru.otus.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MobileCatalog extends Page {

    static final String PRICE_SORTER = ".n-filter-sorter[data-bem*=price]";
    private static final String SHOW_ALL_BUTTON = "footer > button";
    private static final String SEARCHBOX = "[data-autotest-id='7893318']";
    private static final String SEARCH_TEXT_INPUT = SEARCHBOX + " input[type=text]";
    private static final String ALL_FILTERS_BUTTON = "[data-zone-name=all-filters-button] [href]";
    private static final String POPUP_TITLE = ".popup-informer__title";
    private static final By COMPARE_POPUP = By.cssSelector(".popup-informer__controls a");


    private static final String CELL_COMPARE = ".n-user-lists_type_compare";
    private static final By PRELOADER = By.cssSelector("preloadable__preloader");

    public MobileCatalog(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getUrl() {
        return "https://market.yandex.ru/catalog--mobilnye-telefony/54726/list?local-offers-first=0&onstock=1";
    }


    public MobileCatalog expandAllBrands() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(SEARCHBOX + " " + SHOW_ALL_BUTTON))).click();
        return this;
    }

    public MobileCatalog searchForBrand(String brandName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SEARCH_TEXT_INPUT))).click();
        driver.findElement(By.cssSelector(SEARCH_TEXT_INPUT)).sendKeys(brandName);
        return this;
    }

    public MobileCatalog selectBrandCheckbox(String brandName) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(SEARCHBOX + String.format(" input[name*='%s']+*", brandName)))).click();
        return this;
    }

    public Filter<MobileCatalog> clickAllFilters() {
        driver.findElement(By.cssSelector(ALL_FILTERS_BUTTON)).click();
        return new Filter<>(this);
    }

    @Override
    public MobileCatalog open() {
        return (MobileCatalog) super.open();
    }

    public void sortByPrice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRICE_SORTER))).click();
    }

    public Cell getCellByIndex(int i) {
        return new Cell(i);
    }

    public void waitPreloaderDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(PRELOADER));
    }

    public String getPopupTitleText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(POPUP_TITLE))).getText();
    }

    public Compare clickCompareInPopup() {
        wait.until(ExpectedConditions.elementToBeClickable(COMPARE_POPUP)).click();
        return new Compare(this.driver);
    }

    public class Cell {
        private final By CELL = By.cssSelector(".n-snippet-cell2");
        private final By CELL_IMAGE = By.cssSelector(".n-snippet-cell2__image");
        private final By CELL_HEADER = By.cssSelector(".n-snippet-cell2__header");
        private final By CELL_TITLE = By.cssSelector(".n-snippet-cell2__title");
        private int index;

        Cell(int i) {
            this.index = i;
        }

        public void addCompare() {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(CELL))
                    .get(index)
                    .findElement(By.cssSelector(CELL_COMPARE))
                    .click();
        }

        public String getTitleText() {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(CELL))
                    .get(index)
                    .findElement(CELL_TITLE).findElement(By.cssSelector("a")).getAttribute("title");
        }


    }
}
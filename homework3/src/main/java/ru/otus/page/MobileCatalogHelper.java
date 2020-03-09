package ru.otus.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MobileCatalogHelper {
    public static void assertPriceAscending(Page mobileCatalog) {
        mobileCatalog.wait.until(ExpectedConditions
                .attributeContains(By.cssSelector(MobileCatalog.PRICE_SORTER), "class", "n-filter-sorter_state_select"));
    }
}

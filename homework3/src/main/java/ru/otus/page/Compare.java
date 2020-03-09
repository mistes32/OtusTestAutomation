package ru.otus.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Compare extends Page {
    String COMPARE_HEADER = ".n-compare-cell";
    String COMPARE_CELL_DRAGG = ".n-compare-cell-draggable";
    String COMPARE_SHOW_ALL = ".n-compare-show-controls__all";
    String COMPARE_ROW_NAME = ".n-compare-row-name";
    String COMPARE_DIFF = ".n-compare-show-controls__diff";
    private String ROW_TEXT = ".//div[contains(@class,'n-compare-row-name')][.//text()[contains(.,'%s')]]";
    private String SPINNER = ".n-compare-table__spinner";

    public Compare(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getUrl() {
        throw new IllegalStateException();
    }

    public int getSize() {
        return driver.findElements(By.cssSelector(COMPARE_CELL_DRAGG)).size();
    }

    public void clickShowAllFeatures() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(COMPARE_SHOW_ALL))).click();
    }

    public boolean rowTextContans(String text) {
        waitSpinnerDissappear();
        return driver.findElements(By.xpath(String.format(ROW_TEXT, text))).stream().anyMatch(WebElement::isDisplayed);
    }

    //n-compare-table__spinner
    public void showDifferentFeature() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(COMPARE_DIFF))).click();
    }

    public void waitSpinnerDissappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(SPINNER)));
    }
}

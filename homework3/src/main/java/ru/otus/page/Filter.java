package ru.otus.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;

public class Filter<T extends Page> extends Page {
    String show = ".button_action_show-filtered";
    private T previousPage;

    public Filter(WebDriver driver) {
        super(driver);
    }

    public Filter(T main) {
        super(main.driver);
        previousPage = main;
    }

    public T show() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(show))).click();
        return previousPage;
    }

    @Override
    protected String getUrl() {
        throw new IllegalStateException();
    }

    public Filter<T> selectProductLineByName(String... productLine) {
        Arrays.asList(productLine).forEach(s -> wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format("//label[.//text()[.='%s']]/ancestor::span[1]", s)))).click());
        return this;
    }
}

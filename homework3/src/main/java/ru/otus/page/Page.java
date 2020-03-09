package ru.otus.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public abstract class Page {
    public static long DEFAULT_TIMEOUT = 100L;
    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());
    WebDriver driver;
    WebDriverWait wait;
    private String url;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    protected abstract String getUrl();

    public Page open() {
        driver.get(getUrl());
        LOG.debug("Page opened:" + getUrl());
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

        return this;
    }

}

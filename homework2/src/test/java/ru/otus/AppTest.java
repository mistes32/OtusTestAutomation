package ru.otus;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;

import static ru.otus.Browser.CHROME;


public class AppTest {

    public static final String URL = "https://otus.ru/";
    public final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());
    public WebDriver driver;
    public Browser browser;

    @Before
    public void init() {
        browser = Browser.valueOf(System.getProperty("browser", CHROME.name()).toUpperCase());

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
        }


    }

    @Test
    public void firstTest() {
        LOG.debug("HELLO WORLD!");
        Capabilities caps = new FirefoxOptions();
        driver = WebDriverFactory.createNewDriver(browser, caps);
        driver.get(URL);
        LOG.debug("Page Opened");
        driver.quit();
    }

    @After
    public void close() {
        driver.quit();
    }
}

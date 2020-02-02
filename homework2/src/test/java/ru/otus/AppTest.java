package ru.otus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;

import static ru.otus.Browser.CHROME;


public class AppTest {

    private static final String URL = "https://otus.ru/";
    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private WebDriver driver;

    @Before
    public void init() {
        Browser browser = Browser.valueOf(System.getProperty("browser", CHROME.name()).toUpperCase());
        // Странная реализация  здесь получилась. Насколько мне ясно предполагалось внести подобную реализацию в фабрику.
        // Реализация capabilities легко прячется в enum, но фабрика обязывает сделать нечто подобное
        Capabilities caps;
        switch (browser) {
            case FIREFOX:
                caps = new FirefoxOptions();
                break;
            case CHROME:
                caps = new ChromeOptions();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + browser);
        }
        driver = WebDriverFactory.createNewDriver(browser, caps);

    }

    @Test
    public void firstTest() {
        LOG.debug("HELLO WORLD!");
        driver.get(URL);
        LOG.debug("Page Opened");
        driver.quit();
    }

    @After
    public void close() {
        driver.quit();
    }
}

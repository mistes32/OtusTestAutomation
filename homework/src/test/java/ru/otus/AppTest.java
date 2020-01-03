package ru.otus;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;


public class AppTest {

    public static final String URL = "https://otus.ru/";
    public final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Before
    public void init() {
        WebDriverManager.chromedriver().setup();

    }

    @Test
    public void firstTest() {
        LOG.debug("HELLO WORLD!");
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        LOG.debug("Page Opened");
        driver.quit();
    }
}

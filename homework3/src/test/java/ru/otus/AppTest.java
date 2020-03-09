package ru.otus;


import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.page.Filter;
import ru.otus.page.Main;
import ru.otus.page.MobileCatalog;
import ru.otus.page.MobileCatalogHelper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static ru.otus.Browser.CHROME;


public class AppTest {

    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());
    BrowserMobProxy proxy = new BrowserMobProxyServer();
    private WebDriver driver;

    @BeforeMethod
    public void init() {
        Browser browser = Browser.valueOf(System.getProperty("browser", CHROME.name()).toUpperCase());
        ChromeOptions opt = new ChromeOptions();
        proxy.start(8081);
        //3G ~ 40 kb/s
        proxy.setReadBandwidthLimit(80000);
        proxy.setWriteBandwidthLimit(80000);
        //opt.setProxy(ClientUtil.createSeleniumProxy(proxy));
        opt.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        opt.setExperimentalOption("useAutomationExtension", false);
        opt.setAcceptInsecureCerts(true);
        driver = WebDriverFactory.createNewDriver(browser, opt);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS);

    }

    @Test()
    public void test() {
        Main main = new Main(driver);
        main.open();
        Cookie cookie = new Cookie("spravka", Settings.SUT.spravka(), ".yandex.ru", "/", java.sql.Date.valueOf(LocalDate.now().plusDays(1)));
        driver.manage().deleteCookieNamed("spravka");
        driver.manage().addCookie(cookie);
        MobileCatalog mobileCatalog = new MobileCatalog(driver);
        mobileCatalog.open();
        Filter<MobileCatalog> filter = mobileCatalog
                .expandAllBrands()
                .searchForBrand("Xiaomi")
                .selectBrandCheckbox("Xiaomi")
                .clickAllFilters();
        filter
                .selectProductLineByName("Redmi")
                .show().sortByPrice();
        MobileCatalogHelper.assertPriceAscending(mobileCatalog);
        mobileCatalog.waitPreloaderDisappear();
        String productTitleText;
        MobileCatalog.Cell productCell = mobileCatalog.getCellByIndex(0);
        productTitleText = productCell.getTitleText();
        productCell.addCompare();
        Assert.assertThat(mobileCatalog.getPopupTitleText(), StringContains.containsString(String.format("Товар %s добавлен к сравнению", productTitleText)));
        mobileCatalog.clickAllFilters().selectProductLineByName("Redmi")
                .selectProductLineByName("Mi", "Mi Mix", "Mi Max", "Mi Note")
                .show();

        productCell = mobileCatalog.getCellByIndex(0);
        productTitleText = productCell.getTitleText();
        productCell.addCompare();
        Assert.assertThat(mobileCatalog.getPopupTitleText(), StringContains.containsString(String.format("Товар %s добавлен к сравнению", productTitleText)));
        mobileCatalog.clickCompare();

    }

    @AfterMethod(alwaysRun = true)
    public void close() {
        driver.quit();
        driver.manage().deleteAllCookies();
    }
}

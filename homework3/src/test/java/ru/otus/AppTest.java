package ru.otus;


import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.page.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
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
/*        proxy.start(8081);
        //3G ~ 40 kb/s не отрабатывают JS скрипты
        proxy.setReadBandwidthLimit(200000);
        proxy.setWriteBandwidthLimit(200000);
        opt.setProxy(ClientUtil.createSeleniumProxy(proxy));*/
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
        LOG.debug(productTitleText);
        productCell.addCompare();
        Assert.assertThat(mobileCatalog.getPopupTitleText(), StringContains.containsString(String.format("Товар %s добавлен к сравнению", productTitleText)));
        mobileCatalog.clickAllFilters().selectProductLineByName("Redmi")
                .selectProductLineByName("Mi", "Mi Mix", "Mi Max", "Mi Note")
                .show();
        productCell = mobileCatalog.getCellByIndex(0);
        productTitleText = productCell.getTitleText();
        LOG.debug(productTitleText);
        productCell.addCompare();
        Assert.assertThat(mobileCatalog.getPopupTitleText(), StringContains.containsString(String.format("Товар %s добавлен к сравнению", productTitleText)));
        Compare compare = mobileCatalog.clickCompareInPopup();
        Assert.assertEquals(2, compare.getSize());
        compare.clickShowAllFeatures();
        Assert.assertTrue(compare.rowTextContans("Операционная система"));
        compare.showDifferentFeature();
        Assert.assertFalse(compare.rowTextContans("Операционная система"));
    }

    @AfterClass()
    public void close() {
        Optional.of(driver).ifPresent(WebDriver::quit);
    }
}

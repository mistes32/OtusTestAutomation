package ru.otus;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public class WebDriverFactory {


    public static WebDriver createNewDriver(Browser browser) {
        return browser.driver().get();

    }

    public static WebDriver createNewDriver(Browser browser, Capabilities caps) {
        return browser.driverWithCaps().apply(caps);

    }

}

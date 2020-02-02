package ru.otus;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.function.Function;
import java.util.function.Supplier;


public enum Browser {

    FIREFOX() {
        @Override
        public Supplier<WebDriver> driver() {
            return FirefoxDriver::new;
        }

        @Override
        public Function<Capabilities, WebDriver> driverWithCaps() {
            return FirefoxDriver::new;
        }

        @Override
        public Browser setup() {
            WebDriverManager.firefoxdriver().setup();
            return this;
        }
    },

    CHROME() {
        @Override
        public Supplier<WebDriver> driver() {
            return ChromeDriver::new;
        }

        @Override
        public Function<Capabilities, WebDriver> driverWithCaps() {
            return ChromeDriver::new;
        }

        @Override
        public Browser setup() {
            WebDriverManager.chromedriver().setup();
            return this;
        }
    };

    public abstract Supplier<WebDriver> driver();

    public abstract Function<Capabilities, WebDriver> driverWithCaps();


    public abstract Browser setup();
}

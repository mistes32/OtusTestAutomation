package ru.otus;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.function.Function;
import java.util.function.Supplier;


public enum Browser {

    FIREFOX() {
        @Override
        public Supplier<WebDriver> driver() {
            return FirefoxDriver::new;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Function<FirefoxOptions, WebDriver> driverWithCaps() {
            return FirefoxDriver::new;
        }
    },

    CHROME() {
        @Override
        public Supplier<WebDriver> driver() {
            return ChromeDriver::new;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Function<ChromeOptions, WebDriver> driverWithCaps() {
            return ChromeDriver::new;
        }
    };

    public abstract Supplier<WebDriver> driver();

    public abstract <T extends Capabilities> Function<T, WebDriver> driverWithCaps();


}

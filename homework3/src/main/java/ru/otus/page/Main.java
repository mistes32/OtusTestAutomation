package ru.otus.page;

import org.openqa.selenium.WebDriver;
import ru.otus.Settings;
import ru.otus.configuration.SutConfigurationException;

import java.util.Optional;

public class Main extends Page {

    public Main(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getUrl() {
        return Optional.ofNullable(Settings.SUT.baseUrl()).orElseThrow(() -> new SutConfigurationException("Base url is not present"));
    }

}

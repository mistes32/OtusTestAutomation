package ru.otus.page;

import org.openqa.selenium.WebDriver;
import ru.otus.Settings;

public class Main extends Page {

    public Main(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getUrl() {
        return Settings.SUT.baseUrl();
    }

}

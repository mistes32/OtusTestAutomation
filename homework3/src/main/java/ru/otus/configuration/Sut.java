package ru.otus.configuration;

import org.aeonbits.owner.Config;

public interface Sut extends Config {
    public String baseUrl();

    public String spravka();
}

package ru.otus;

import org.aeonbits.owner.ConfigFactory;
import ru.otus.configuration.Sut;

public class Settings {

    public final static Sut SUT = ConfigFactory.create(Sut.class);


}

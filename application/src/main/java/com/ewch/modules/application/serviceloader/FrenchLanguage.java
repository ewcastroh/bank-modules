package com.ewch.modules.application.serviceloader;

import java.util.Locale;

public class FrenchLanguage implements Internationalization {
    @Override
    public Locale getLocale() {
        return new Locale("fr", "FR");
    }

    @Override
    public void greeting(String name) {
        System.out.printf("¡Bonjour %s!\n", name);
    }
}

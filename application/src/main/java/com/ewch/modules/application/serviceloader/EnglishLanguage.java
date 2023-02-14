package com.ewch.modules.application.serviceloader;

import java.util.Locale;

public class EnglishLanguage implements Internationalization {
    @Override
    public Locale getLocale() {
        return new Locale("en", "US");
    }

    @Override
    public void greeting(String name) {
        System.out.printf("¡Hello %s!\n", name);
    }
}

package com.ewch.modules.application.serviceloader;

import java.util.Locale;

public class SpanishLanguage implements Internationalization {
    @Override
    public Locale getLocale() {
        return new Locale("es", "ES");
    }

    @Override
    public void greeting(String name) {
        System.out.printf("¡Hola %s!\n", name);
    }
}

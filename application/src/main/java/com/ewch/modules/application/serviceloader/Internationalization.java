package com.ewch.modules.application.serviceloader;

import java.util.Locale;

public interface Internationalization {

    Locale getLocale();

    void greeting(String name);
}

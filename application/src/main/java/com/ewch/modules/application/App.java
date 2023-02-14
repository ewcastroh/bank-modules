package com.ewch.modules.application;

import com.ewch.modules.application.serviceloader.Internationalization;

import java.util.List;
import java.util.Locale;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class App {

    public static void main( String[] args ) {
        ServiceLoader<Internationalization> loader = ServiceLoader.load(Internationalization.class);
        Iterable<Internationalization> iterable = () -> loader.iterator();
        Stream<Internationalization> stream = StreamSupport.stream(iterable.spliterator(), false);
        List<Locale> locales = stream.map(Internationalization::getLocale).collect(Collectors.toList());
        System.out.printf("Supported Languages: %s\n", locales);
    }
}

package com.agir.maidai.config.converters;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateToStringConverter implements Converter<LocalDate, String> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String convert(LocalDate source) {
        if(source == null) {
            return null;
        }
        return source.format(formatter);
    }
}

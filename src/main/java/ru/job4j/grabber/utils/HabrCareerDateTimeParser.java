package ru.job4j.grabber.utils;

import java.time.*;

/**
 * @author artem.polschak@gmail.com on 25.07.2022.
 * @project job4j_grabber
 */
public class HabrCareerDateTimeParser implements DateTimeParser {

    @Override
    public LocalDateTime parse(String parse) {
        String[] tmp = parse.split("\\+");
        return LocalDateTime.parse(tmp[0]);
    }
}

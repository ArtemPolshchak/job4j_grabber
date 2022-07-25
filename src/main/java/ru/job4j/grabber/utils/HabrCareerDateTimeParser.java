package ru.job4j.grabber.utils;

import java.time.LocalDateTime;

/**
 * @author artem.polschak@gmail.com on 25.07.2022.
 * @project job4j_grabber
 */
public class HabrCareerDateTimeParser implements DateTimeParser {

    @Override
    public LocalDateTime parse(String parse) {
        String[] parseInfoFromSite = parse.split("\\+");
        String[] parseDateAndTime = parseInfoFromSite[0].split("T");
        String[] parseDate = parseDateAndTime[0].split("-");
        String[] parseTime = parseDateAndTime[1].split(":");

        return LocalDateTime.of(Integer.parseInt(parseDate[0]),
                Integer.parseInt(parseDate[1]),
                Integer.parseInt(parseDate[2]), Integer.parseInt(parseTime[0]),
                Integer.parseInt(parseTime[1]),
                Integer.parseInt(parseTime[2]));
    }
}

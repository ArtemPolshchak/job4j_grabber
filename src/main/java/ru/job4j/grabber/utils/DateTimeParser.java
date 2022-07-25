package ru.job4j.grabber.utils;

import java.time.LocalDateTime;

/**
 * @author artem.polschak@gmail.com on 25.07.2022.
 * @project job4j_grabber
 */

public interface DateTimeParser {
    LocalDateTime parse(String parse);
}

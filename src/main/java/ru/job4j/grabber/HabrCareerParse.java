package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author artem.polschak@gmail.com on 25.07.2022.
 * @project job4j_grabber
 */
public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);

    public static void main(String[] args) throws IOException {
        Connection connection = Jsoup.connect(PAGE_LINK);
        Document document = connection.get();
        Elements rows = document.select(".vacancy-card__inner");
        rows.forEach(row -> {
            Element titleElement = row.select(".vacancy-card__title").first();
            Element linkElement = titleElement.child(0);
            String vacancyName = titleElement.text();

            Element dateElement = row.select(".vacancy-card__date").first();
            Element dateEl = dateElement.child(0);
            String dateText = dateElement.text();

            String link = String.format("%s%s  дата - %s", SOURCE_LINK,
                    linkElement.attr("href"), dateEl.attr("datetime"));
            System.out.printf("%s %s %s%n", vacancyName, dateText, link);
        });
    }
}

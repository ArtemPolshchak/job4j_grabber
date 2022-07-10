package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/**
 * @author artem.polschak@gmail.com on 11.07.2022.
 * @project job4j_grabber
 */

public class AlertRabbit {

    public static void main(String[] args) throws Exception {

        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String sql = String.format(
                        "create table if not exists rabbit(%s, %s);",
                        "id serial primary key",
                        "created_date timestamp"
                );
                statement.execute(sql);
            }

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(intervalSeconds("rabbit.properties"))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        }
    }

    private static Connection getConnection() throws Exception {
        Connection connection;
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            Class.forName(properties.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password")
            );
        }
        return connection;
    }

    public static int intervalSeconds(String properties) {
        Properties config = new Properties();
        try (InputStream io = AlertRabbit.class.getClassLoader().getResourceAsStream(properties)) {
            config.load(io);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(config.getProperty("rabbit.interval"));
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement statement =
                         connection.prepareStatement("insert into rabbit (created_date) values(?)")) {
                LocalDateTime created = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(created);
                statement.setTimestamp(1, timestamp);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


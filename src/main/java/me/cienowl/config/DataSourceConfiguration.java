package me.cienowl.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.cienowl.context.DataSourceWriterReadOnlyRouting;
import me.cienowl.context.DatabaseEnvironment;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.writer")
    public HikariConfig hikariConfigWriter() {
        return new HikariConfig();
    }

    public DataSource writerDataSource() {
        return new HikariDataSource(hikariConfigWriter());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.readonly")
    public HikariConfig hikariConfigReadOnly() {
        return new HikariConfig();
    }

    public DataSource readOnlyDataSource() {
        return new HikariDataSource(hikariConfigReadOnly());
    }

    @Bean
    public DataSource dataSource() {
        DataSourceWriterReadOnlyRouting dataSourceWriterReadOnlyRouting = new DataSourceWriterReadOnlyRouting();
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DatabaseEnvironment.WRITER, writerDataSource());
        targetDataSource.put(DatabaseEnvironment.READONLY, readOnlyDataSource());
        dataSourceWriterReadOnlyRouting.setTargetDataSources(targetDataSource);

        dataSourceWriterReadOnlyRouting.setDefaultTargetDataSource(writerDataSource());
        return dataSourceWriterReadOnlyRouting;
    }
}

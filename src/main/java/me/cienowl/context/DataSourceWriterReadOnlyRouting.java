package me.cienowl.context;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceWriterReadOnlyRouting extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getEnvironment();
    }
}

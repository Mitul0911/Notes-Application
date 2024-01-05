package com.notes.setup;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EbeanDatabaseSetup {

    @Value("${ebean.db.host}")
    String dbHost;

    @Value("${ebean.db.port}")
    String dbPort;

    @Value("${ebean.db.name}")
    String dbName;

    @Value("${ebean.db.username}")
    String dbUsername;

    @Value("${ebean.db.password}")
    String dbPassword;

    @Value("${ebean.ddl.generate}")
    boolean ddlGenerate;

    @Value("${ebean.ddl.run}")
    boolean ddlRun;

    @Value("${ebean.run.migration}")
    boolean runMigration;

    @EventListener(ApplicationReadyEvent.class)
    @Order(100)
    public void createEbeanDataBase(){
        String dbURL = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername(dbUsername);
        dataSourceConfig.setPassword(dbPassword);
        dataSourceConfig.setUrl(dbURL);
        dataSourceConfig.setSchema(dbUsername);

        DatabaseConfig config = new DatabaseConfig();
        config.setDataSourceConfig(dataSourceConfig);
        config.setDefaultServer(true);
        config.setDdlGenerate(ddlGenerate);
        config.setDdlRun(ddlRun);
        config.setRunMigration(runMigration);

        Database database = DatabaseFactory.create(config);

    }
}

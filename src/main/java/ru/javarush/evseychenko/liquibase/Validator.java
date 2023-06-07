package ru.javarush.evseychenko.liquibase;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import ru.javarush.evseychenko.dataSource.ConnectionData;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


public class Validator {

    @SneakyThrows
    public void start(){
        Map<String, Object> config = new HashMap<>();
        Connection connection = ConnectionData.getConnection();

        try (connection) {
            Scope.child(config, () -> {

                Database database = DatabaseFactory
                        .getInstance()
                        .findCorrectDatabaseImplementation(new JdbcConnection(connection));

                Liquibase liquibase = new liquibase
                        .Liquibase("liquibase/changelog.xml", new ClassLoaderResourceAccessor(), database);

                liquibase.update(new Contexts(), new LabelExpression());
            });
        }
    }
}

package me.rootdeibis.orewards.common.database.mysql;

import me.rootdeibis.orewards.common.function.Functions;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLFileDatabase extends ISQLDatabase {


    private final File file_sql;
    private Connection connection;

    public SQLFileDatabase(File file_sql) {
        this.file_sql = file_sql;
    }

    @Override
    public void connect(Functions.EFunction onConnect) {
        try {
            if (!this.file_sql.exists()) file_sql.createNewFile();

            Class.forName("org.sqlite.JDBC");

            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.file_sql);

            onConnect.apply();

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return this.getConnection();
    }

    @Override
    public void disconnect() {

    }
}

package me.rootdeibis.orewards.common.database.mysql;


import me.rootdeibis.orewards.common.function.Functions;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySQLDatabase extends ISQLDatabase {

    private Connection connection;

    private final String host;
    private final String dbName;
    private final String username;
    private final String password;

    private Thread connectionThread;

    public MySQLDatabase(String host, String dbName, String username, String password) {
        this.host = host;
        this.dbName = dbName;
        this.username = username;
        this.password = password;

    }



    @Override
    public void connect(Functions.EFunction callback) {
        try {

            if (this.connection != null && !this.connection.isClosed()) this.disconnect();

            Class.forName("com.mysql.jdbc.Driver");

            final String connectionURL = "jdbc:mysql://" +  this.host +":3306/" + this.dbName;

            this.connectionThread = new Thread(() -> {
               try {
                   this.connection = DriverManager.getConnection(connectionURL, this.username, this.password);

                   if (callback != null) callback.apply();

                    this.connectionThread.interrupt();
               }catch (Exception e) {
                   e.printStackTrace();
                   this.connectionThread.interrupt();
               }
             });


            this.connectionThread.start();



        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void disconnect() {
        try {
            if (this.connection != null && !this.connection.isClosed()) this.connection.close();

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}

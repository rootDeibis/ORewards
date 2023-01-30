package me.rootdeibis.orewards.common.database.mysql;


import me.rootdeibis.orewards.common.function.Functions;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySQLDatabase implements ISQLDatabase {

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
    public void connect() {
        this.connect(() -> {});
    }

    @Override
    public void disconnect() {
        try {
            if (this.connection != null && !this.connection.isClosed()) this.connection.close();

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean has(String query) {
        return this.get(query) != null;
    }

    @Override
    public HashMap<String, Object> get(String query, String... keys) {
        HashMap<String, Object> result = new HashMap<>();

        try {
            Statement statement = this.connection.createStatement();

           ResultSet resultSet = statement.executeQuery(query);

           if (resultSet.next()) {

               for (String key : keys) {
                   Object value = resultSet.getObject(key);


                   if (!(value == null)) value = "undefined";

                   result.put(key, value);

               }
               
           }

           return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void all(String query, Functions.Function<List<HashMap<String, Object>>> then) {
        Thread thread = new AllThreadQuery(this.connection, query, then);

        thread.start();
    }


    private class AllThreadQuery extends Thread {


        private final Connection connection;
        private final String query;
        private final Functions.Function<List<HashMap<String, Object>>> then;
        public AllThreadQuery(Connection connection , String query, Functions.Function<List<HashMap<String, Object>>> then) {
            this.connection = connection;
            this.query = query;
            this.then = then;
        }

        @Override
        public void run() {
            try {

                List<HashMap<String, Object>> ResultList = new ArrayList<>();

                Statement statement = this.connection.createStatement();

                ResultSet resultSet = statement.executeQuery(this.query);

                while (resultSet.next()) {

                    HashMap<String, Object> result = new HashMap<>();
                        
                    ResultSetMetaData metaData = resultSet.getMetaData();

                    for (int i = 0; i < metaData.getColumnCount(); i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultSet.getObject(columnName);

                        result.put(columnName, value);

                    }

                    ResultList.add(result);
                    
                }

                this.then.apply(ResultList);

                this.interrupt();



            }catch (Exception e) {
                this.interrupt();
                throw new RuntimeException(e);
            }

        }
    }


}

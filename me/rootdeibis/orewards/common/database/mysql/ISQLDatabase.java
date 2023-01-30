package me.rootdeibis.orewards.common.database.mysql;


import me.rootdeibis.orewards.common.function.Functions;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ISQLDatabase {

    abstract public void connect(Functions.EFunction onConnect);


    abstract public Connection getConnection();

    abstract public void disconnect();

    public boolean exec(String query) {
       try {

           PreparedStatement statement = this.getConnection().prepareStatement(query);

           return statement.execute();

       }catch (Exception e) {
           e.printStackTrace();
       }
       return false;
    }
    public boolean has(String query) {
        return this.get(query) != null;
    }

    public HashMap<String, Object> get(String query, String... keys) {
        HashMap<String, Object> result = new HashMap<>();

        try {
            Statement statement = this.getConnection().createStatement();

            statement.setFetchSize(1);

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

    public void all(String query, Functions.Function<List<HashMap<String, Object>>> then) {
        Thread thread = new AllThreadQuery(this.getConnection(), query, then);

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

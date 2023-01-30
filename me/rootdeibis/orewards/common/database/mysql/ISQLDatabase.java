package me.rootdeibis.orewards.common.database.mysql;


import me.rootdeibis.orewards.common.function.Functions;

import java.util.HashMap;
import java.util.List;

public interface ISQLDatabase {

    void connect(Functions.EFunction onConnect);

    void connect();

    void disconnect();



    boolean has(String query);

    HashMap<String, Object> get(String query, String... keys);

    void all(String query, Functions.Function<List<HashMap<String, Object>>> then);


}

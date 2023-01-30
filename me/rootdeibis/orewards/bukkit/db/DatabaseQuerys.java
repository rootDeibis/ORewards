package me.rootdeibis.orewards.bukkit.db;

public enum DatabaseQuerys {

    CREATE_TABLE("CREATE TABLE IF NOT EXISTS %s (uuid varchar(20), until varchar(10))"),
    SELECT_USER("SELECT * FROM %s WHERE uuid='%s'"),
    CREATE_USER("INSERT INTO %s VALUES('%s','%s')"),

    UPDATE_USER("UPDATE %s SET until='%s' WHERE uuid='%s'"),

    DELETE_USER("DELETE FROM %s WHERE uuid='%s'");

    private final String query;

    DatabaseQuerys(String query) {
        this.query = query;
    }


    public String parse() {
        return this.query;
    }

}

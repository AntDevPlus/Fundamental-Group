package plus.antdev.fundamentalgroup.database;

import plus.antdev.fundamentalgroup.Main;

import java.sql.*;

public class DatabaseManager {

    private String databaseName;
    private String databaseHost;
    private String databasePort;
    private String databaseUser;
    private String databasePass;
    Connection conn = null;
    Main plugin;

    public DatabaseManager(String databaseName, String databaseHost, String databasePort, String databaseUser,String databasePass, Main main) {
        this.databaseName = databaseName;
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.databaseUser = databaseUser;
        this.databasePass = databasePass;
        this.plugin = main;
    }

    public void connection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + databaseHost +"/" + databaseName +"?" +
                            "user=" + databaseUser + "&password=" + databasePass);
            if(isConnected()){
                if(! tableExist()){
                    createTableGroup();
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().info("Can't connect to database");
            plugin.getLogger().warning("SQLException: " + ex.getMessage());
            plugin.getLogger().warning("SQLState: " + ex.getSQLState());
            plugin.getLogger().warning("VendorError: " + ex.getErrorCode());
        }
    }
    public boolean isConnected(){
        return conn != null;
    }
    public boolean tableExist() throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ?"
                + "LIMIT 1;");
        preparedStatement.setString(1, "group");

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }
    public void createTableGroup() throws SQLException {
        Statement statement = conn.createStatement();

        String sql = "CREATE TABLE `group` (" +
                "`id` SMALLINT NOT NULL AUTO_INCREMENT," +
	            "`leader` TEXT NOT NULL," +
	            "`players` TEXT NOT NULL," +
                "PRIMARY KEY (`id`))"+
                "COLLATE='utf8mb4_0900_ai_ci';";
        statement.executeUpdate(sql);
        plugin.getLogger().info("Genering Fundamental-Group table in database...");
    }
}

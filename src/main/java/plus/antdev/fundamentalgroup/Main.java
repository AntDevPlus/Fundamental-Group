package plus.antdev.fundamentalgroup;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import plus.antdev.fundamentalgroup.database.DatabaseManager;
import plus.antdev.fundamentalgroup.events.FundamentalGroupListener;

public class Main extends JavaPlugin {

    private DatabaseManager databaseManager;
    private boolean useDatabase;

    @Override
    public void onEnable() {
        createConfig(); loadConfig();
        if (useDatabase) { databaseManager.connection(); }
        getLogger().info("is loaded");
        getServer().getPluginManager().registerEvents(new FundamentalGroupListener(this),this);
    }

    @Override
    public void onDisable() {
        getLogger().info("is unloaded");
    }

    private void createConfig() {
        this.saveDefaultConfig();
    }
    private void loadConfig() {
        FileConfiguration config = this.getConfig();
        boolean useDatabase = config.getBoolean("use-database");
        String databaseName = config.getString("database.name");
        String databaseHost = config.getString("database.host");
        String databasePort = config.getString("database.port");
        String databaseUser = config.getString("database.user");
        String databasePass = config.getString("database.pass");

        this.useDatabase = useDatabase;

        if (useDatabase) {
            databaseManager = new DatabaseManager(databaseName,databaseHost,databasePort,databaseUser,databasePass,this);
        }

    }
}
